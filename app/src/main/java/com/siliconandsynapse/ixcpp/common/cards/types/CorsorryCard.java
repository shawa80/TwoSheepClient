package com.siliconandsynapse.ixcpp.common.cards.types;

import com.siliconandsynapse.ixcpp.common.cards.Card;

public class CorsorryCard extends Card {


	public static final int ONE     = 1;
	public static final int TWO     = 2;
	public static final int THREE   = 3;
	public static final int FOUR    = 4;
	public static final int FIVE    = 5;
	public static final int SIX     = 6;
	public static final int SEVEN   = 7;
	public static final int EIGHT   = 8;
	public static final int NINE    = 9;
	public static final int TEN     = 10;
	public static final int ELEVEN  = 11;
	
	public static final int PINK    = 0;
	public static final int RED     = 1;
	public static final int ORANGE  = 2;
	public static final int YELLOW  = 3;
	public static final int GREEN   = 4;
	public static final int LT_BLUE = 5;
	public static final int DR_BLUE = 6;
	public static final int PURPLE  = 7;
	public static final int BROWN   = 8;
	public static final int GREY    = 9;


	
	CorsorryCard(int secureCode, int s, int v) throws IndexOutOfBoundsException {
		super(secureCode, s, v);
	}

	CorsorryCard(int secureCode, String s, String v) throws IndexOutOfBoundsException {
		super(secureCode, s, v);
	}
	
	@Override
	public String getHint() {
		return "CorsorryCard";
	}
	
	public String getSuit()
	{

		
		if (suit == PINK)
			return "Pink";
		if (suit == RED)
			return "Red";
		if (suit == ORANGE)
			return "Orange";
		if (suit == YELLOW)
			return "Yellow";
		if (suit == GREEN)
			return "Green";
		if (suit == LT_BLUE)
			return "Lt Blue";
		if (suit == DR_BLUE)
			return "Dr Blue";
		if (suit == PURPLE)
			return "Purple";
		if (suit == BROWN)
			return "Brown";
		if (suit == GREY)
			return "Grey";
		
		return "";
	}
	public String getType()
	{
		if (value == ONE)
			return "One";
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
		if (value == ELEVEN)
			return "Eleven";


		return "";
	}

	
	protected int getTypeValue(String value)
	{
		if ("One".equals(value))
			return ONE;
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
		if ("Eleven".equals(value))
			return ELEVEN;

		return -1;
	}

	protected int getSuitValue(String value)
	{

		if ("Pink".equals(value))
			return PINK;
		if ("Red".equals(value))
			return RED;
		if ("Orange".equals(value))
			return ORANGE;
		if ("Yellow".equals(value))
			return YELLOW;
		if ("Green".equals(value))
			return GREEN;
		if ("Lt Blue".equals(value))
			return LT_BLUE;
		if ("Dr Blue".equals(value))
			return DR_BLUE;
		if ("Purple".equals(value))
			return PURPLE;
		if ("Brown".equals(value))
			return BROWN;
		if ("Grey".equals(value))
			return GREY;		
		
		return -1;
	}
	
}
