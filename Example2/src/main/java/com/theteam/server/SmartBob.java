package com.theteam.server;

import java.util.HashMap;

public class SmartBob implements IQuestionAnswerSystem {

	private static final long TIME_TO_LIVE = 60 * 60000;
	private static final String DEFAULT_ANS = "Sorry currently we cannot provide a answer to this question.";
	private IQuestionAnswerSystem watson = new Watson();
	
	private HashMap<String, AnsPair> cheatSheet;
	public void Init() {
		watson.Init();
		cheatSheet = new HashMap<String, AnsPair>();
	}

	public String GetAnswer(String question) {
		String answer = null;
		long curTime = System.currentTimeMillis();
		if(cheatSheet.containsKey(question))
		{
			System.out.println("Find the ans to \""+ question + "\" in cheatsheet");
			AnsPair cheatAnsPair = cheatSheet.get(question);
			if(cheatAnsPair.GetExpiration() >= curTime)
			{
				answer = cheatAnsPair.GetAns();
			}
			else
			{
				System.out.println("Ans expired. Expiration is " + cheatAnsPair.GetExpiration() 
																 + " but now is "+curTime );
			}
		}
		
		if(answer == null)
		{
			try
			{
				answer = watson.GetAnswer(question);
				AnsPair newCheatAnsPair = new AnsPair(answer, curTime+TIME_TO_LIVE);
				cheatSheet.put(question, newCheatAnsPair);
				System.out.println("Store ans to question "+ question + "expire at " + (curTime+TIME_TO_LIVE));
			}
			catch (Exception e)
			{
				System.out.println("At time " + curTime + " trying to access Watson but got a exception");
			}
		}
		
		if(answer == null)
		{
			answer = DEFAULT_ANS;
		}
		return answer;
	}
	
	private class AnsPair
	{
		private String ans;
		private long expiration;
		public AnsPair(String ans, long expiration)
		{
			this.ans = ans;
			this.expiration = expiration;
		}
		
		String GetAns()	{ return this.ans; }
		long GetExpiration()	{	return this.expiration;	}
	}

}
