package com.theteam.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Watson implements QuestionAnswerSystem
{
	private String username = "osu2_student7";
	private String password = "PL7kdFZI";
	public Watson()
	{
	
	}
	
	public void Init()
	{
		
	}
	
	private String requestWatsonForAns(String question)
	{
		try {
			
			
			URL url = new URL("https://dal09-gateway.watsonplatform.net/instance/579/deepqa/v1/question");
			
			String userPassword = username + ":" + password;
			String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());

			Map<String,Object> params = new HashMap<String,Object>();

			StringBuilder postData = 
					new StringBuilder("{\"question\":{\"questionText\":\"" + question + "\"}}");
			byte[] postDataBytes = postData.toString().getBytes("UTF-8");

			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Basic " + encoding);
			conn.setRequestProperty("content-type", "application/json");
			conn.setRequestProperty("accept", "application/json");
			conn.setRequestProperty("X-SyncTimeout", "30");
			conn.setDoOutput(true);
			System.out.println("Packing finished at: " + System.nanoTime());
			conn.getOutputStream().write(postDataBytes);
			System.out.println("Retrive raw ans at: " + System.nanoTime());

			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			System.out.println("Converted to type Reader at: " + System.nanoTime());
			String result = "";
			StringBuilder builder = new StringBuilder();
			int charsRead = -1;
			char[] chars = new char[5000];
			do{
			    charsRead = in.read(chars,0,chars.length);
			    //if we have valid chars, append them to end of string.
			    if(charsRead>0)
			        builder.append(chars,0,charsRead);
			}while(charsRead>0);
			result = builder.toString();
			System.out.println("Finishing extracting raw ans at: " + System.nanoTime());
			return result;
		}

		catch (MalformedURLException e) { 
			e.printStackTrace();
		} 
		catch (IOException e) {   
			e.printStackTrace();
		}
		return "I don't know!";
	}
	
	public String GetAnswer(String question)
	{
		System.out.println("Send to Watson at: " + System.nanoTime());
		String rawAns = requestWatsonForAns(question);
		System.out.println("Hear from Watson at: " + System.nanoTime());
		String ans = ParseWatsonQAJsonAndReturnFirst.parse(rawAns);
		System.out.println("Parse finished at: " + System.nanoTime());
		return ans;
	}
}
