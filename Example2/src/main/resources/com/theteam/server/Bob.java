package com.theteam.server;

public class Bob implements QuestionAnswerSystem
{
	public void Init()
	{
		
	}
	
	public String GetAnswer(String question)
	{
		String result = "I don't know.";
		if (question.equals("Hello! How are you?"))
		{
			result = "I am fine. Thanks.";
		}
		return result;
	}
}
