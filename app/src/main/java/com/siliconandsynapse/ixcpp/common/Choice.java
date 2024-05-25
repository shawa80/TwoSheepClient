package com.siliconandsynapse.ixcpp.common;

import java.util.*;

public class Choice
{
	private String id;
	private String question;
	private Vector<Answer> answers;

	public Choice(String id, String question)
	{
		this.id = id;
		this.question = question;

		answers = new Vector<Answer>();
	}

	/// deal with question ///////////////////////
	public String getId()
	{
		return id;
	}
	public String getQuestion()
	{
		return question;
	}

	/// deal with POSSIBLE answers ///////////////////////
	public void add(Answer a)
	{
		answers.add(a);
	}

	public int getCount()
	{
		return answers.size();
	}
	public Answer getAnswerAt(int i)
	{
		return answers.elementAt(i);
	}

	public boolean containsAnswer(Answer a)
	{
		return answers.contains(a);
	}



}
