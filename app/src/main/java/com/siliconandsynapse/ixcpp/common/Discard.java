package com.siliconandsynapse.ixcpp.common;

import java.util.*;

import com.siliconandsynapse.ixcpp.common.cards.Card;

public class Discard
{
	private int min;
	private int max;
	private String message;

	private Vector<Card> cards;

	public Discard()
	{
		min = 0;
		max = 0;
		message = "";

		cards = new Vector<Card>();
	}

	public String getMessage()
	{
		return message;
	}
	public void setMessage(String message)
	{
		this.message = message;
	}

	public void setMin(int min)
	{
		this.min = min;
	}
	public int getMin()
	{
		return min;
	}

	public void setMax(int max)
	{
		this.max = max;
	}
	public int getMax()
	{
		return max;
	}

	/////////////////////////////////////
	//deal with cards
	/////////////////////////////////////
	public void addCard(Card card)
	{
		cards.add(card);
	}

	public Card getCardAt(int at)
	{
		return cards.elementAt(at);
	}

	public int getCount()
	{
		return cards.size();
	}

}	
