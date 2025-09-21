package com.siliconandsynapse.aclient.gameModels;

import com.siliconandsynapse.aclient.gameModels.models.ChoiceRequest;
import com.siliconandsynapse.aclient.gameModels.models.DiscardRequest;
import com.siliconandsynapse.aclient.gameModels.models.UpdateCards;
import com.siliconandsynapse.aclient.gameModels.models.UpdateGame;
import com.siliconandsynapse.ixcpp.common.Choice;
import com.siliconandsynapse.ixcpp.common.ChoiceResponse;
import com.siliconandsynapse.ixcpp.common.Discard;
import com.siliconandsynapse.ixcpp.common.cards.Card;
import com.siliconandsynapse.ixcpp.common.cards.CardFactory;
import com.siliconandsynapse.ixcpp.gameInteraction.TableCardEventHandler;
import com.siliconandsynapse.ixcpp.ui.ITableDisplay;
import com.siliconandsynapse.ixcpp.util.Mutex;
import com.siliconandsynapse.observerPool.ObserverPool;

public class TableModel implements ITableDisplay {


	private final PlayerCollection players = new PlayerCollection();
	private final PlayerCollection trick = new PlayerCollection();

	private final TableModelTranslator handTranslator;
	private final TableModelTranslator trickTranslator;


	private ObserverPool<ChoiceRequest> choiceRequest;
	public void addChoiceListener(ChoiceRequest listener) {
		choiceRequest.add(listener);
	}
	public void removeChoiceListener(ChoiceRequest listener) {
		choiceRequest.remove(listener);
	}


	private ObserverPool<DiscardRequest> discardRequest;
	public void addDiscardListener(DiscardRequest listener) {
		discardRequest.add(listener);
	}
	public void removeDiscardListener(DiscardRequest listener) {
		discardRequest.remove(listener);
	}



	private ObserverPool<UpdateCards> cardUpdate;
	public void addListener(UpdateCards listener) {
		cardUpdate.add(listener);
	}
	public void removeListener(UpdateCards listener) {
		cardUpdate.remove(listener);
	}




	private ObserverPool<UpdateGame> gameUpdate;
	public void addListener(UpdateGame listener) {
		gameUpdate.add(listener);
	}
	public void removeListener(UpdateGame listener) {
		gameUpdate.add(listener);
	}


	public TableModel() {


		cardUpdate = new ObserverPool<UpdateCards>(UpdateCards.class);
		gameUpdate = new ObserverPool<UpdateGame>(UpdateGame.class);

		choiceRequest = new ObserverPool<ChoiceRequest>(ChoiceRequest.class);
		discardRequest = new ObserverPool<DiscardRequest>(DiscardRequest.class);

		handTranslator = new TableModelTranslator(players, cardUpdate, false);
		trickTranslator = new TableModelTranslator(trick, cardUpdate, true);

	}


	public TableCardEventHandler getHandTranslator() {

		return handTranslator;
	}

	public TableCardEventHandler getTrickTranslator() {

		return trickTranslator;
	}




	@Override
	public void displayDiscard() {


		// TODO Auto-generated method stub

	}

	@Override
	public CardFactory getCardFactory() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Discard getDiscard() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Card getPlay() {
		// TODO not used
		return null;
	}

	@Override
	public void indicatePlayer(int playerId) {

		players.getPlayerFromServer(playerId).setTurn();
	}



	@Override
	public void setCardNotice(Mutex arg0) {
		// TODO Auto-generated method stub

	}



	@Override
	public void displayChoice() {


	}

	@Override
	public ChoiceResponse getChoice() {
		return null;
	}


	@Override
	public void setChoice(Choice c) {

		this.choiceRequest.getDispatcher().displayChoiceDialog(c);
		//notify UI here, none blocking...
	}

	@Override
	public void setChoiceNotice(Mutex cBlock) {

	}




	@Override
	public void setDiscard(Discard d) {

		this.discardRequest.getDispatcher().displayDiscardDialog(d);

	}

	@Override
	public void setDiscardNotice(Mutex arg0) {
		// TODO Auto-generated method stub

	}

//	@Override
//	public void setGameChat(GameChat arg0) {
//		// TODO Auto-generated method stub
//
//	}

	@Override
	public void setMyPlayerId(int playerId) {

		players.setMyPlayerId(playerId);
		trick.setMyPlayerId(playerId);

	}

	@Override
	public void updateDescription(int playerId, String description) {

		players.getPlayerFromServer(playerId).setDescription(description);
	}

	@Override
	public void updatePlayerName(int player, String name) {

		players.getPlayerFromServer(player).setName(name);
	}

	@Override
	public void updateScore(int player, int score) {

		players.getPlayerFromServer(player).setScore(score);

	}

	@Override
	public void updateWealth(int player, int wealth) {
		players.getPlayerFromServer(player).setWealth(wealth);

	}



	public PlayerModel getPlayer(int playerId) {

		trick.getPlayerFromGui(playerId); //creates a trick along with a player, so they stay in sync

		return players.getPlayerFromGui(playerId);
	}


	@Override
	public void voidPlay() {
		// TODO unused

	}

	@Override
	public void writeNotice(String message) {

		gameUpdate.getDispatcher().writeNotice(message);

	}


}
