package com.siliconandsynapse.ixcpp.common.cards;

public abstract class Card
{
	public static final int UP = 1;
	public static final int DOWN = 2;


	protected int suit;
	protected int value;
	private int facing;
	private int secureCode;


	protected Card(int secureCode, int s, int v) throws IndexOutOfBoundsException
	{
		suit = s;
		value = v;
		facing = DOWN;
		this.secureCode = secureCode;
		
	}

	protected Card(int id, String csuit, String type)
	{

		secureCode = id;
		value = getTypeValue(type);
		suit = getSuitValue(csuit);

		if (value == -1 || suit == -1)
			facing = DOWN;
		else
			facing = UP;
	}

	public abstract String getHint();
	
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}
		
	public void setFace(int f)
	{
		facing = f;
	}
	public int getFace()
	{
		return facing;
	}
	
	public boolean valuesEquals(Card target) 
	{
		if (target.secureCode == secureCode
				&& target.suit == suit
				&& target.value == value)
			return true;
		
		return false;
		
	}

	public boolean equals(Object c)
	{
		Card target;

		if (!(c instanceof Card))
			return false;

		target = (Card)c;

		if (target.secureCode == secureCode)
			return true;

		return false;

	}
	
	public int hashCode() {
		return (secureCode + "").hashCode();
		
	}

	public int getSuitType()
	{
		return suit;
	}
	public int getValueType()
	{
		return value;
	}


	public abstract String getSuit();
	public abstract String getType();
	

	public char getSuitAbr()
	{
		return ' ';
	}
	public char getTypeAbr()
	{
		return ' ';
	}

	public boolean equal(Object o)
	{
		return false;
	}

	public String toString()
	{

		return getType() + " of " + getSuit() + " sc: " + secureCode;
	}

	public int getCode()
	{
		return secureCode;
	}


	protected abstract int getTypeValue(String value);
	protected abstract int getSuitValue(String value);



}
