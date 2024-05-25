package com.siliconandsynapse.ixcpp.common.cards.types;

import java.util.ArrayList;
import java.util.List;

import com.siliconandsynapse.ixcpp.common.cards.Card;
import com.siliconandsynapse.ixcpp.common.cards.CardFactory;

public class CorsorryCardFactory extends CardFactory {

	@Override
	public Card createCard(int secureCode, String suite, String type) {
		Card card;

		card = new CorsorryCard(secureCode, suite, type);
	
		addCard(secureCode, card);

		return card;
	}
	
	private Card createCard(int suit, int type) {
		
		int secureCode;
		Card card;

		secureCode = generateCode();
		card = new CorsorryCard(secureCode, suit, type);
	
		addCard(secureCode, card);

		return card;
	}
	
	//TODO not correct deck
	@Override
	public
	List<Card> getCards() {

		ArrayList<Card> cards = new ArrayList<Card>();
		
		cards.add(createCard(PokerCard.CLUB, PokerCard.NINE));
		cards.add(createCard(PokerCard.CLUB, PokerCard.TEN));
		cards.add(createCard(PokerCard.CLUB, PokerCard.ACE));
		cards.add(createCard(PokerCard.CLUB, PokerCard.KING));
		cards.add(createCard(PokerCard.CLUB, PokerCard.JACK));
		cards.add(createCard(PokerCard.CLUB, PokerCard.QUEEN));

		cards.add(createCard(PokerCard.SPADE, PokerCard.NINE));
		cards.add(createCard(PokerCard.SPADE, PokerCard.TEN));
		cards.add(createCard(PokerCard.SPADE, PokerCard.ACE));
		cards.add(createCard(PokerCard.SPADE, PokerCard.KING));
		cards.add(createCard(PokerCard.SPADE, PokerCard.JACK));
		cards.add(createCard(PokerCard.SPADE, PokerCard.QUEEN));

		cards.add(createCard(PokerCard.HEART, PokerCard.NINE));
		cards.add(createCard(PokerCard.HEART, PokerCard.TEN));
		cards.add(createCard(PokerCard.HEART, PokerCard.ACE));
		cards.add(createCard(PokerCard.HEART, PokerCard.KING));
		cards.add(createCard(PokerCard.HEART, PokerCard.JACK));
		cards.add(createCard(PokerCard.HEART, PokerCard.QUEEN));

		cards.add(createCard(PokerCard.DIAMOND, PokerCard.NINE));
		cards.add(createCard(PokerCard.DIAMOND, PokerCard.TEN));
		cards.add(createCard(PokerCard.DIAMOND, PokerCard.ACE));
		cards.add(createCard(PokerCard.DIAMOND, PokerCard.KING));
		cards.add(createCard(PokerCard.DIAMOND, PokerCard.JACK));
		cards.add(createCard(PokerCard.DIAMOND, PokerCard.QUEEN));

		return cards;
	}




}
