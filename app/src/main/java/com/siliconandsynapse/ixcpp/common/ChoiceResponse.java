package com.siliconandsynapse.ixcpp.common;

public class ChoiceResponse {

	
	private Answer answer;
	
	public ChoiceResponse(Answer answer) {
		this.answer = answer;
	}
	
	
	/// deal with THE answer ////////////////////

	public boolean hasAnswer()
	{
		if (answer == null)
			return false;

		return true;
	}
	public Answer getAnswer()
	{
		return answer;
	}
	
}
