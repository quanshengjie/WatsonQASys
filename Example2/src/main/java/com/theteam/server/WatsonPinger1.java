package com.theteam.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class WatsonPinger1 implements IWatsonPinger {

	private final String studentUsername = "osu2_student7";
	private final String studentPassword = "PL7kdFZI";
	private final String uploaderUsername = "osu2_uploader";
	private final String uploaderPassword = "UspeKDrB";
	private final String question = "TheTeamHandShake";
	
	public int lightPing()
	{
		try {
			
			
			URL url = new URL("https://dal09-gateway.watsonplatform.net/instance/579/deepqa/v1/question");
			
			String userPassword = studentUsername + ":" + studentPassword;
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

			return conn.getResponseCode();
		}

		catch (MalformedURLException e) { 
			e.printStackTrace();
		} 
		catch (IOException e) {   
			e.printStackTrace();
		}
		return -1;
	}

	public int heavyPing() {
		int status1 = -1, status2 = -2, status3 = -3, status4 = -4;
		try {
			
			// first ping, normal question service
			URL url = new URL("https://dal09-gateway.watsonplatform.net/instance/579/deepqa/v1/question");
			
			String userPassword = studentUsername + ":" + studentPassword;
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
			status1 = conn.getResponseCode();
			
			// second ping, document service
			url = new URL("https://dal09-gateway.watsonplatform.net/instance/579/deepqa/v1/question/document/T_A29BAE8BF50A1FD5B5914426D2D35E9D/0/-1");
			userPassword = uploaderUsername + ":" + uploaderPassword;
			encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", "Basic " + encoding);
			conn.setDoOutput(true);
			status2 = conn.getResponseCode();
			
			// third ping, normal question service
			url = new URL("https://dal09-gateway.watsonplatform.net/instance/579/deepqa/v1/question");
			userPassword = studentUsername + ":" + studentPassword;
			encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());
			params = new HashMap<String,Object>();
			postData = 
					new StringBuilder("{\"question\":{\"questionText\":\"" + question + "\"}}");
			postDataBytes = postData.toString().getBytes("UTF-8");
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Basic " + encoding);
			conn.setRequestProperty("content-type", "application/json");
			conn.setRequestProperty("accept", "application/json");
			conn.setRequestProperty("X-SyncTimeout", "30");
			conn.setDoOutput(true);
			conn.getOutputStream().write(postDataBytes);
			status3 = conn.getResponseCode();
			
			// forth ping, document service
			url = new URL("https://dal09-gateway.watsonplatform.net/instance/579/deepqa/v1/question/document/T_A29BAE8BF50A1FD5B5914426D2D35E9D/0/-1");
			userPassword = uploaderUsername + ":" + uploaderPassword;
			encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", "Basic " + encoding);
			conn.setDoOutput(true);
			status4 = conn.getResponseCode();
		}

		catch (MalformedURLException e) { 
			e.printStackTrace();
		} 
		catch (IOException e) {   
			e.printStackTrace();
		}
		
		if(status1 == status2 && status2 == status3 && status3 == status4)
		{
			return status1;
		}
		else
		{
			return -1;
		}
	}

}
