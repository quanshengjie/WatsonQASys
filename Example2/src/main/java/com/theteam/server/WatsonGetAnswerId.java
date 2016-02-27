/**
 * 
 */
package com.theteam.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shengjie
 *
 */
public class WatsonGetAnswerId implements IAnswerIdManager
{
	private String username = "5e088ca7-28e1-45e0-8e55-9929c7a71438";
	private String password = "ESWUguQIskzH";
	
	private String requestWatsonForId(String ans)
	{
		try {
			
			String urlStr = "https://gateway.watsonplatform.net/natural-language-classifier/api/v1/classifiers/c7e487x21-nlc-10734/classify";
			urlStr += "?text=" + URLEncoder.encode(ans, "UTF-8");
			URL url = new URL(urlStr);
			
			String userPassword = username + ":" + password;
			String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());

			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", "Basic " + encoding);
			conn.setRequestProperty("content-type", "application/json");
			conn.setRequestProperty("accept", "application/json");
			conn.setRequestProperty("X-SyncTimeout", "30");
			conn.setDoOutput(true);

			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String result = "";
			StringBuilder builder = new StringBuilder();
			int charsRead = -1;
			char[] chars = new char[3];
			do{
			    charsRead = in.read(chars,0,chars.length);
			    //if we have valid chars, append them to end of string.
			    if(charsRead>0)
			        builder.append(chars,0,charsRead);
			}while(charsRead>0);
			result = builder.toString();
			return result;
		}

		catch (MalformedURLException e) { 
			e.printStackTrace();
		} 
		catch (IOException e) {   
			e.printStackTrace();
		}
		return "-1";
	}
	
	public String GetID(String ans)
	{
		String rawJson = requestWatsonForId(ans);
		String id = ParseWatsonNLClassifierJson.parse(rawJson);
		return id;
	}
}
