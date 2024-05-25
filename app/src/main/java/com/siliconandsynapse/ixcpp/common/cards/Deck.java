package com.siliconandsynapse.ixcpp.common.cards;

import java.util.*;



public class Deck
{

	private Vector<Card> hold;
	private CardFactory factory;


	/**
	*	Creates an empty deck.
	*/
	public Deck(CardFactory factory)
	{
		this.factory = factory;
		hold = new Vector<Card>();

		hold.addAll(factory.getCards());
	}

	/**
	*	Randomizes the cards in the deck
	*/
	public void shuffle()
	{
		Card o;
		Random r = new Random();

		for (int i = 0; i < hold.size()*3; i++)
		{
			o = hold.remove(r.nextInt(hold.size()-1));
			hold.add(o);
		}
	}

	/**
	*	Gets and removes the card at the top of the deck
	*	@return The card at the top of the deck
	*	@throws OutOfCardsException Thrown if the deck is empty
	*/
	public Card getTopCard() throws OutOfCardsException
	{
		if (hold.size() == 0)
			throw new OutOfCardsException();

		return hold.remove(0);
	}

	/**
	*	Gets the number of cards in the deck.
	*	@return The count of cards in the deck.
	*/
	public int cardCount()
	{
		return hold.size();
	}

	public Card getCardById(int secureCode) {
		return factory.getCard(secureCode);
	}

	public CardFactory getFactory() {
		return factory;
	}
	
}
