package com.theteam.server;

import java.util.HashMap;
import java.util.Map;

public class Bob implements IQuestionAnswerSystem
{
	private HashMap<String, String> cheatSheet = new HashMap<String, String>();
	
	public void Init()
	{
		cheatSheet.put("When is the deadline for CSE master program?", "January 31st");
		cheatSheet.put("What is the address of CSE Department?", "395 Dreese Laboratories 2015 Neil Avenue Columbus, OH 43210-1277");
		cheatSheet.put("Based on my profile, what master program fits me best?", "The Computer Science & Engineering requires a GPA of 3.0. Each candidate is required to pursue a program of study in courses approved by his/her academic advisor. Students in the thesis track need to complete 20 graded cr-hrs. Students in the non-thesis track need to complete 30 graded cr-hrs, or 24 graded cr-hrs and a masters project.");
	}
	
	public String GetAnswer(String question)
	{
		String result = "I don't know.";
		if (cheatSheet.containsKey(question))
		{
			result = cheatSheet.get(question);
		}
		return result;
	}
}
