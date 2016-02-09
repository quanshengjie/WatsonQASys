package com.theteam.server;

import java.util.HashMap;
import java.util.Map;

public class Bob implements QuestionAnswerSystem
{
	private HashMap<String, String> cheatSheet = new HashMap<Integer, String>();
	
	public void Init()
	{
		cheatSheet.put("", value)
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
