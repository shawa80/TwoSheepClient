package com.siliconandsynapse.ixcpp.ui;

import com.siliconandsynapse.ixcpp.common.Choice;
import com.siliconandsynapse.ixcpp.common.ChoiceResponse;
import com.siliconandsynapse.ixcpp.common.Discard;
import com.siliconandsynapse.ixcpp.common.cards.Card;
import com.siliconandsynapse.ixcpp.common.cards.CardFactory;
//import com.siliconandsynapse.ixcpp.protocol.game.GameChat;
import com.siliconandsynapse.ixcpp.util.Mutex;

public interface ITableDisplay {

	public void updateScore(int playerId, int score);
	public void updatePlayerName(int playerId, String name);
	public void updateWealth(int playerId, int wealth);
	public void updateDescription(int playerId, String desc);

	public void writeNotice(String notice);
	public void indicatePlayer(int playerId);
	public void setMyPlayerId(int id);

	//Player Control
	public Card getPlay();
	public void voidPlay();

	//Choice control
	public void setChoice(Choice c);
	public void displayChoice();
	public ChoiceResponse getChoice();

	//discard control
	public void setDiscard(Discard d);
	public void displayDiscard();
	public Discard getDiscard();

	//Game events
	public void setCardNotice(Mutex n);
	public void setDiscardNotice(Mutex n);
	public void setChoiceNotice(Mutex n);
	//public void setGameChat(GameChat gameChat);

	public CardFactory getCardFactory();

}
