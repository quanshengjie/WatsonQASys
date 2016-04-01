package com.theteam.server;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;


public class SmartBob implements IQuestionAnswerSystem {

	private static SmartBob instance = new SmartBob();
	private static boolean inited = false;
	
	private static final long TIME_TO_LIVE = 60; // in unit of minutes
	private static final String DEFAULT_ANS = "Sorry currently we cannot provide a answer to this question.";
	private static final int CACHE_CAPACITY = 10000;
	private IQuestionAnswerSystem watson = new Watson();
	
	private static LoadingCache<String, String> cheatSheet;
	
	public static SmartBob GetInstance()
	{
		return instance;
	}
	
	private SmartBob()	{	}
	
	public void Init() {
		if(!inited)
		{
			watson.Init();
			
			// build cache
			int numProcessores = Runtime.getRuntime().availableProcessors();
			CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder().concurrencyLevel(numProcessores * 2);
		    if (CACHE_CAPACITY > 0) {
		        cacheBuilder.maximumSize(CACHE_CAPACITY);
		    }
		    if(TIME_TO_LIVE > 0)
		    {
		    	cacheBuilder.expireAfterWrite(TIME_TO_LIVE, TimeUnit.MINUTES);
		    }
		    CacheLoader<String, String> cheatSheetCacheLoader = new CacheLoader<String, String>() {
		        @Override
		        public String load(String question) {
		        	String answer = null;
		        	try
					{
						answer = watson.GetAnswer(question);
						System.out.println("Store ans to question "+ question);
					}
					catch (Exception e)
					{
						System.out.println("Watson fail to give answer");
					}
		        	if(answer == null)
		    		{
		    			answer = DEFAULT_ANS;
		    		}
		        	return answer;
		        }
		    };
		    cheatSheet = cacheBuilder.build(cheatSheetCacheLoader);
			inited = true;
		}
	}

	public String GetAnswer(String question) {
		String answer = null;
		try {
			answer = cheatSheet.get(question);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		if(answer == null)
		{
			answer = DEFAULT_ANS;
		}
		return answer;
	}

}
