package com.siliconandsynapse.ixcpp.common.cards.types;

import com.siliconandsynapse.ixcpp.common.cards.Card;

public class PokerCard extends Card {

	

	
	public static final int ACE   = 1;
	public static final int TWO   = 2;
	public static final int THREE = 3;
	public static final int FOUR  = 4;
	public static final int FIVE  = 5;
	public static final int SIX   = 6;
	public static final int SEVEN = 7;
	public static final int EIGHT = 8;
	public static final int NINE  = 9;
	public static final int TEN   = 10;
	public static final int JACK  = 11;
	public static final int QUEEN = 12;
	public static final int KING  = 13;
	public static final int JOKER = 14;

	public static final int CLUB   = 0;
	public static final int SPADE  = 1;
	public static final int HEART  = 2;
	public static final int DIAMOND = 3;

	
	PokerCard(int secureCode, int s, int v) throws IndexOutOfBoundsException {
		super(secureCode, s, v);
	}

	PokerCard(int secureCode, String s, String v) throws IndexOutOfBoundsException {
		super(secureCode, s, v);
	}
	
	public String getSuit()
	{

		if (suit == CLUB)
			return "Clubs";
		if (suit == SPADE)
			return "Spades";
		if (suit == HEART)
			return "Hearts";
		if (suit == DIAMOND)
			return "Diamonds";

		return "";
	}
	public String getType()
	{
		if (value == ACE)
			return "ACE";
		if (value == TWO)
			return "Two";
		if (value == THREE)
			return "Three";
		if (value == FOUR)
			return "Four";
		if (value == FIVE)
			return "Five";
		if (value == SIX)
			return "Six";
		if (value == SEVEN)
			return "Seven";
		if (value == EIGHT)
			return "Eight";
		if (value == NINE)
			return "Nine";
		if (value == TEN)
			return "Ten";
		if (value == JACK)
			return "Jack";
		if (value == QUEEN)
			return "Queen";
		if (value == KING)
			return "King";


		return "";
	}

	
	protected int getTypeValue(String value)
	{
		if ("ACE".equals(value))
			return ACE;
		if ("Two".equals(value))
			return TWO;
		if ("Three".equals(value))
			return THREE;
		if ("Four".equals(value))
			return FOUR;
		if ("Five".equals(value))
			return FIVE;
		if ("Six".equals(value))
			return SIX;
		if ("Seven".equals(value))
			return SEVEN;
		if ("Eight".equals(value))
			return EIGHT;
		if ("Nine".equals(value))
			return NINE;
		if ("Ten".equals(value))
			return TEN;
		if ("Jack".equals(value))
			return JACK;
		if ("Queen".equals(value))
			return QUEEN;
		if ("King".equals(value))
			return KING;

		return -1;
	}

	protected int getSuitValue(String value)
	{

		if ("Clubs".equals(value))
			return CLUB;
		if ("Spades".equals(value))
			return SPADE;
		if ("Hearts".equals(value))
			return HEART;
		if ("Diamonds".equals(value))
			return DIAMOND;

		return -1;
	}

	@Override
	public String getHint() {
		
		return "poker";
	}
	
}
