package com.siliconandsynapse.aclient.game;

import java.util.Hashtable;

import android.app.Activity;

import com.siliconandsynapse.aclient.NetworkService;
import com.siliconandsynapse.aclient.gameModels.TableModel;
import com.siliconandsynapse.ixcpp.common.ChoiceResponse;
import com.siliconandsynapse.ixcpp.common.Discard;
import com.siliconandsynapse.ixcpp.common.cards.Card;
import com.siliconandsynapse.ixcpp.common.cards.CardFactory;
import com.siliconandsynapse.ixcpp.protocol.Pause;
import com.siliconandsynapse.ixcpp.protocol.game.GameMessage;
import com.siliconandsynapse.ixcpp.protocol.game.PlayerChoice;
import com.siliconandsynapse.ixcpp.protocol.game.PlayerDiscard;
import com.siliconandsynapse.ixcpp.protocol.game.PlayerId;
import com.siliconandsynapse.ixcpp.protocol.game.PlayerInfo;
import com.siliconandsynapse.ixcpp.protocol.game.PlayerPickACard;
import com.siliconandsynapse.ixcpp.protocol.game.QuitGame;
import com.siliconandsynapse.ixcpp.protocol.game.TableChange;
import com.siliconandsynapse.ixcpp.protocol.game.TurnChange;
import com.siliconandsynapse.ixcpp.protocol.lobby.JoinGameCmd;
import com.siliconandsynapse.ixcpp.util.Mutex;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.ixcpp.protocol.game.TrickChange;

public class GameService implements Runnable {

	private PlayerInfo playerInfo;
	private PlayerId playerId;
	private PlayerPickACard playerPickACard;
	private GameMessage gameMessage;
	private TableChange tableChange;
	private TrickChange trickChange;
	private TurnChange turnChange;
	private Pause pause;
	private PlayerChoice playerChoice;
	private PlayerDiscard playerDiscard;

	private Thread t;

	private IxManager home;
	private final int gameId;
	private final String subGameId;
	private IxAddress addr;

	private NetworkService service;

	private CardFactory cache;
	private final TableModel table;

	private GameServiceCard serviceCard;
	private final Mutex cardServerBlock;
	private final Mutex cardUserBlock;
	private Card cardToPlay;

	private GameServiceChoice serviceChoice;
	private final Mutex choiceServerBlock;
	private final Mutex choiceUserBlock;
	private ChoiceResponse resp;


	private GameServiceDiscard serviceDiscard;
	private final Mutex discardServerBlock;
	private final Mutex discardUserBlock;
	private Discard dis;


	private static final Hashtable<String, GameService> services = new Hashtable<>();

	private Activity act;

	public static GameService getService(Activity act, int gameId) {
		return getService(act, gameId, null);
	}
	public static GameService getService(Activity act, int gameId, String subGameId) {

		if (!services.containsKey(gameId+subGameId)) {
			services.put(gameId+subGameId, new GameService(act, gameId, subGameId));
		}

		return services.get(gameId+subGameId);
	}

	private GameService(Activity act, int gameId) {
		this(act, gameId, null);
	}

	private GameService(Activity act, int gameId, String subGameId) {

		this.subGameId = subGameId;
		this.gameId = gameId;

		act = this.act;
		cardServerBlock = new Mutex();
		cardUserBlock = new Mutex();
		cardToPlay = null;


		choiceUserBlock = new Mutex();
		choiceServerBlock = new Mutex();
		resp = null;

		discardServerBlock = new Mutex();
		discardUserBlock = new Mutex();

		table = new TableModel();

	}


	public void start() {

		t = new Thread(this);
		t.setName("Game Service Thread");
		t.start();
	}

	public Mutex getCardBlock() {

		return cardUserBlock;
	}

	public void setCard(Card card) {

		cardToPlay = card;
	}

	public Card getCard() {
		return cardToPlay;
	}




	public Mutex getChoiceBlock() {
		return choiceUserBlock;
	}


	public void setChoiceResponse(ChoiceResponse resp) {
		this.resp = resp;
	}

	public ChoiceResponse getChoiceResponse() {
		return resp;
	}




	public Mutex getDiscardBlock() {
		return discardUserBlock;
	}

	public void setDiscardResponse(Discard dis) {
		this.dis = dis;
	}

	public Discard getDiscardResponse() {
		return dis;
	}


	public TableModel getModel() {
		return table;
	}

	public void run() {

		var f = CardFactory.getInstance(CardFactory.SHEEPSHEAD);

		addr = IxAddress.createRoot("ixcpp.games." + gameId);

		if (subGameId != null)
			addr = addr.append(subGameId);

		service = NetworkService.getService();

		home = service.getTunnel();

		playerInfo = new PlayerInfo(addr, table);
		home.registerReceiver(playerInfo);

		playerId = new PlayerId(addr, table);
		home.registerReceiver(playerId);

		playerPickACard = new PlayerPickACard(addr, cardServerBlock);
		home.registerReceiver(playerPickACard);

		gameMessage = new GameMessage(addr, table);
		home.registerReceiver(gameMessage);

		tableChange = new TableChange(addr, table.getHandTranslator(), f);
		home.registerReceiver(tableChange);

		trickChange = new TrickChange(addr, table.getTrickTranslator(), f);
		home.registerReceiver(trickChange);

		turnChange = new TurnChange(addr, table);
		home.registerReceiver(turnChange);

		pause = new Pause(addr);
		home.registerReceiver(pause);

		playerChoice = new PlayerChoice(addr, table, choiceServerBlock);
		home.registerReceiver(playerChoice);


		playerDiscard = new PlayerDiscard(addr, table, f, discardServerBlock);
		home.registerReceiver(playerDiscard);


		var jg = new JoinGameCmd(gameId);
		var gameKey = IxAddress.createRoot("ixcpp.lobby");
		jg.execute(gameKey, home);

		serviceCard = new GameServiceCard(
				cardServerBlock, cardUserBlock, this,
				playerPickACard, home);

		serviceChoice = new GameServiceChoice(
				choiceServerBlock, choiceUserBlock, this,
				playerChoice, home);

		serviceDiscard = new GameServiceDiscard(
				discardServerBlock, discardUserBlock, this,
				playerDiscard, home);
	}

	public void stop() {

		services.remove(gameId+subGameId);

		serviceCard.stop();
		serviceChoice.stop();
		serviceDiscard.stop();

		t.interrupt();

		try {
			t.join();
		} catch (InterruptedException e) {

		}

		var quit = new QuitGame(addr);

		quit.sendQuitGame(home, gameId);

		home.unregisterReciever(playerInfo);
		home.unregisterReciever(playerId);
		home.unregisterReciever(playerPickACard);
		home.unregisterReciever(gameMessage);
		home.unregisterReciever(tableChange);
		home.unregisterReciever(trickChange);
		home.unregisterReciever(turnChange);
		home.unregisterReciever(pause);
		home.unregisterReciever(playerChoice);
		home.unregisterReciever(playerDiscard);
	}




}
