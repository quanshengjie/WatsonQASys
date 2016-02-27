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
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Watson implements IQuestionAnswerSystem
{
	private String studentUsername = "osu2_student7";
	private String studentPassword = "PL7kdFZI";
	private String uploaderUsername = "osu2_uploader";
	private String uploaderPassword = "UspeKDrB";
	private IAnswerIdManager ansToId = new WatsonGetAnswerId();
	private IAnswerURLManager idToURL = new AnswerURLManagerLocalFile();
	private final int NORMAL_ANS_HTML_CRITICAL_LENGTH = 1024;
	
	public Watson()
	{
		idToURL.Init();
	}
	
	public void Init()
	{
		
	}
	
	private String requestWatsonForAns(String question)
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

			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
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
	
	private String requestWatsonForDocumentFragment(String docId)
	{
		try {
			
			
			URL url = new URL("https://dal09-gateway.watsonplatform.net/instance/579/deepqa/v1/question/document/"+docId);
			
			String userPassword = uploaderUsername + ":" + uploaderPassword;
			String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());

			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", "Basic " + encoding);
			conn.setDoOutput(true);

			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
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
	
	private String WrapAnsToHTML(String ans)
	{
		String result = "";
		String id = ansToId.GetID(ans);
		String url = idToURL.GetURL(id);
		result += "<p class=\"p1\">";
		result += "<span class=\"s1\">";
		result +=  StringEscapeUtils.escapeHtml4(ans);
		result += "</span>";
		if(url != null)
		{
			result += "<a href=\"" + url + "\" style=\"padding-left:20px\" target=\"_blank\">Read More</a>";
		}
		result += "</p>\n";
		return result;
	}
	
	private String WrapeDocumentFragmentToHTML(String docHTML, String docId)
	{
		String url = idToURL.GetURL(docId);
		String result = "";
		if(url != null)
		{
			result += "<a href=\"" + url + "\" style=\"padding-left:20px\" target=\"_blank\">Read More</a>";
		}
		
		result += "<p class=\"p1\">\n";
		result += docHTML;
		result += "</p>\n";
		if(url != null)
		{
			result += "<a href=\"" + url + "\" style=\"padding-left:20px\" target=\"_blank\">Read More</a>\n";
		}
		return result;
	}
	
	private boolean isHTMLBetterThanAnswer(String html)
	{
		boolean isBetter = false;
		Document doc = Jsoup.parse(html);
		Elements links = doc.select("a[href]");
		Elements table = doc.select("table");
		Elements tr = doc.select("tr");
		Elements td = doc.select("td");
		Elements ul = doc.select("ul");
		Elements li = doc.select("li");
		if(links.size() > 0 || table.size() > 0 || tr.size() > 0 
		   || td.size() > 0 || ul.size() > 0 || li.size() > 0)
		{
			isBetter = true;
		}
		return isBetter;
	}
	
	public String GetAnswer(String question)
	{
		String rawAns = requestWatsonForAns(question);
		List<String> parseResult = ParseWatsonQAJsonAndReturnFirst.parse(rawAns);
		String ans = parseResult.get(0);
		String html = requestWatsonForDocumentFragment(parseResult.get(1));
		if(!isHTMLBetterThanAnswer(html) && ans.length() <= NORMAL_ANS_HTML_CRITICAL_LENGTH)
		{
			ans = WrapAnsToHTML(ans);
			System.out.println("Return text of ans: " + parseResult.get(0));
		}
		else
		{		
			ans = WrapeDocumentFragmentToHTML(html, parseResult.get(1));
			System.out.println("Return document fragment of ans: " + parseResult.get(0));
		}
		return ans;
	}
}
