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
			conn.getOutputStream().write(postDataBytes);

			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String result = "";
			int length = 0;
			for ( int c = in.read(); c != -1; c = in.read() )
			{
				result += (char)c;
			}
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
		return ParseJsonAndReturnFirst.parse(requestWatsonForAns(question));
	}
}
