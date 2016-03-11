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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shengjie
 *
 */
public class WatsonGetAnswerId implements IAnswerIdManager
{
	private String username = "b87698a6-9f6b-4b05-aa1c-c8da2077a155";
	private String password = "R6KzUnQg2HVO";
	
	private String requestWatsonForId(String ans)
	{
		try {
			
			String urlStr = "https://gateway.watsonplatform.net/natural-language-classifier/api/v1/classifiers/668877x36-nlc-21/classify";
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
		List<String> ids = new ArrayList<String>();
		List<Double> confs = new ArrayList<Double>();
		ParseWatsonNLClassifierJson.parse(rawJson, ids, confs);
		String id = "-1";
		if(confs.size() > 1 && confs.get(0) >= 0.707)
		{
			id = ids.get(0);
		}
		return id;
	}
}
