package com.siliconandsynapse.ixcpp.common;


public class Answer
{
	private int id;
	private String value;

	public Answer(int id, String value)
	{
		this.id = id;
		this.value = value;

	}
	public int getId()
	{
		return id;
	}
	public String getValue()
	{
		return value;
	}

	public boolean equals(Object e)
	{
		Answer a;

		if (!(e instanceof Answer))
			return false;

		a = (Answer)e;

		if (a.id == id)
			return true;

		return false;

	}

	public String toString()
	{
		return value + " " + id;
	}
}
