package com.siliconandsynapse.aclient.game;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import android.app.Activity;
import android.util.Log;

//import com.siliconandsynapse.aclient.NetworkService;
import com.siliconandsynapse.aclient.NetworkService;
import com.siliconandsynapse.aclient.gameModels.TableModel;
import com.siliconandsynapse.ixcpp.common.ChoiceResponse;
import com.siliconandsynapse.ixcpp.common.Discard;
import com.siliconandsynapse.ixcpp.common.cards.Card;
import com.siliconandsynapse.ixcpp.common.cards.CardFactory;
import com.siliconandsynapse.ixcpp.common.cards.types.PokerCard;
import com.siliconandsynapse.ixcpp.gameInteraction.GameController;
import com.siliconandsynapse.ixcpp.protocol.Pause;
import com.siliconandsynapse.ixcpp.protocol.game.GameMessage;
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
import com.siliconandsynapse.net.ixtunnel.ParseError;
import com.siliconandsynapse.net.ixtunnel.RecieverExists;
//import com.siliconandsynapse.ixcpp.protocol.Pause;
//import com.siliconandsynapse.ixcpp.protocol.game.GameChat;
//import com.siliconandsynapse.ixcpp.protocol.game.GameMessage;
//import com.siliconandsynapse.ixcpp.protocol.game.GameStart;
//import com.siliconandsynapse.ixcpp.protocol.game.PlayerChoice;
//import com.siliconandsynapse.ixcpp.protocol.game.PlayerDiscard;
//import com.siliconandsynapse.ixcpp.protocol.game.PlayerChoice;
//import com.siliconandsynapse.ixcpp.protocol.game.PlayerDiscard;
//import com.siliconandsynapse.ixcpp.protocol.game.PlayerId;
//import com.siliconandsynapse.ixcpp.protocol.game.PlayerInfo;
//import com.siliconandsynapse.ixcpp.protocol.game.PlayerPickACard;
//import com.siliconandsynapse.ixcpp.protocol.game.QuitGame;
//import com.siliconandsynapse.ixcpp.protocol.game.TableChange;
import com.siliconandsynapse.ixcpp.protocol.game.TrickChange;
//import com.siliconandsynapse.ixcpp.protocol.game.TurnChange;
//import com.siliconandsynapse.ixcpp.protocol.lobby.JoinGameCmd;
//import com.siliconandsynapse.ixcpp.util.Mutex;
//import com.siliconandsynapse.net.ixtunnel.IxAddress;
//import com.siliconandsynapse.net.ixtunnel.IxManager;
//import com.siliconandsynapse.net.ixtunnel.ParseError;
//import com.siliconandsynapse.net.ixtunnel.RecieverExists;

public class GameService implements Runnable {

//	private GameStart gameStart;
	private PlayerInfo playerInfo;
	private PlayerId playerId;
	private PlayerPickACard playerPickACard;
	private GameMessage gameMessage;
//	private GameChat gameChat;
	private TableChange tableChange;
	private TrickChange trickChange;
	private TurnChange turnChange;
	private Pause pause;
//	private PlayerChoice playerChoice;
//	private PlayerDiscard playerDiscard;
//
	private Thread t;

	private IxManager home;
//
//	private IxAddress baseAddr;
	private int gameId;
	private IxAddress addr;

	private NetworkService service;
	//private FakeServer service;
//
//	private CardFactory cache;
	private TableModel table;

	private GameServiceCard serviceCard;
	private Mutex cardServerBlock;
	private Mutex cardUserBlock;
	private Card cardToPlay;

//	private GameServiceChoice serviceChoice;
	//private Mutex choiceServerBlock;
	//private Mutex choiceUserBlock;
//	private ChoiceResponse resp;
//
//
//	private GameServiceDiscard serviceDiscard;
//	private Mutex discardServerBlock;
//	private Mutex discardUserBlock;
//	private Discard dis;


	private static Hashtable<Integer, GameService> services = new Hashtable<>();

	private Activity act;

	public static GameService getService(Activity act, int gameId) {

		if (!services.containsKey(gameId)) {
			services.put(gameId, new GameService(act, gameId));
		}

		return services.get(gameId);
	}





	private GameService(Activity act, int gameId) {

		this.gameId = gameId;
//
		act = this.act;
		cardServerBlock = new Mutex();
		cardUserBlock = new Mutex();
		cardToPlay = null;
//
//
//		choiceUserBlock = new Mutex();
//		choiceServerBlock = new Mutex();
//		resp = null;
//
//		discardServerBlock = new Mutex();
//		discardUserBlock = new Mutex();
//
		//cache = CardFactory.getInstance(CardFactory.POKER);

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




//	public Mutex getChoiceBlock() {
//		return choiceUserBlock;
//	}
//
//
//	public void setChoiceResponse(ChoiceResponse resp) {
//		this.resp = resp;
//	}
//
//	public ChoiceResponse getChoiceResponse() {
//		return resp;
//	}




//	public Mutex getDiscardBlock() {
//		return discardUserBlock;
//	}
//
//	public void setDiscardResponse(Discard dis) {
//		this.dis = dis;
//	}
//
//	public Discard getDiscardResponse() {
//		return dis;
//	}


	public TableModel getModel() {
		return table;
	}

	public void run() {

		CardFactory f = CardFactory.getInstance(CardFactory.SHEEPSHEAD);


//		table.setMyPlayerId(0);
//		table.getHandTranslator().validateCard(0, "public",
//				0, 0, x.get(0));
//		table.getHandTranslator().validateCard(0, "trick",
//				0, 1, x.get(1));
//		table.getHandTranslator().validateCard(0, "public",
//				1, 0, x.get(2));
//		table.getHandTranslator().validateCard(0, "public",
//				1, 1, x.get(3));
//		x.get(3).setFace(Card.UP);
//		table.getHandTranslator().removeNonvalidatedCards();

		addr = IxAddress.createRoot("ixcpp.games." + gameId);


		service = NetworkService.getService();

//		baseAddr = service.getGameAddr();
//
//		try {
//			addr = baseAddr.append(gameName);
//		} catch (ParseError e) {
//			e.printStackTrace();
//		}

			home = service.getTunnel();

//			gameStart = new GameStart(addr, cache);
//			home.registerReceiver(gameStart);
//
			playerInfo = new PlayerInfo(addr, table);
			home.registerReceiver(playerInfo);

			playerId = new PlayerId(addr, table);
			home.registerReceiver(playerId);

			playerPickACard = new PlayerPickACard(addr, cardServerBlock);
			home.registerReceiver(playerPickACard);

			gameMessage = new GameMessage(addr, table);
			home.registerReceiver(gameMessage);
//
//			gameChat = new GameChat(addr, home);
//			gameChat.registerTable(table);
//			home.registerReceiver(gameChat);
//
			tableChange = new TableChange(addr, table.getHandTranslator(), f);
			home.registerReceiver(tableChange);

			trickChange = new TrickChange(addr, table.getTrickTranslator(), f);
			home.registerReceiver(trickChange);

			turnChange = new TurnChange(addr, table);
			home.registerReceiver(turnChange);

			pause = new Pause(addr);
			home.registerReceiver(pause);

//			playerChoice = new PlayerChoice(addr, table, choiceServerBlock);
//			home.registerReceiver(playerChoice);
//
//			//add UNREGISTER when adding this receiver!!!!
//			playerDiscard = new PlayerDiscard(addr, table, cache, discardServerBlock);
//			home.registerReceiver(playerDiscard);
//

		service.start();

		var jg = new JoinGameCmd(gameId);
		var gameKey = IxAddress.createRoot("ixcpp.lobby");
		jg.execute(gameKey, home);


//		service.executeLobby(new JoinGameCmd(gameName));
//
		serviceCard = new GameServiceCard(
				cardServerBlock, cardUserBlock, this,
				playerPickACard, home);
//
//		serviceChoice = new GameServiceChoice(
//				choiceServerBlock, choiceUserBlock, this,
//				playerChoice, home);
//
//		serviceDiscard = new GameServiceDiscard(
//				discardServerBlock, discardUserBlock, this,
//				playerDiscard, home);
//
//		serviceCard.join();
//		serviceChoice.join();
//		serviceDiscard.join();
	}

	public void stop() {

		services.remove(gameId);

		serviceCard.stop();
		//serviceChoice.stop();
		//serviceDiscard.stop();

		t.interrupt();

		try {
			t.join();
		} catch (InterruptedException e) {

		}

		var quit = new QuitGame(addr);

		quit.sendQuitGame(home, gameId);


		//home.unregisterReciever(gameStart);
		home.unregisterReciever(playerInfo);
		home.unregisterReciever(playerId);
		home.unregisterReciever(playerPickACard);
		home.unregisterReciever(gameMessage);
		//home.unregisterReciever(gameChat);
		home.unregisterReciever(tableChange);
		home.unregisterReciever(trickChange);
		home.unregisterReciever(turnChange);
		home.unregisterReciever(pause);
		//home.unregisterReciever(playerChoice);
		//home.unregisterReciever(playerDiscard);
	}




}
