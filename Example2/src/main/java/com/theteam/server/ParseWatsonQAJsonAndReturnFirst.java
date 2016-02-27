package com.theteam.server;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;

public class ParseWatsonQAJsonAndReturnFirst
{
	/**
	 * @param jsonLine the json in String returned by Watson QA
	 * @return
	 * 		The first List with index 0 contain the first in String, 
	 * 		index 1 contain its document ID in Watson
	 * 		If parse failed, the index 0 is empty string, index 1 is "-1"
	 */
	public static List<String> parse(String jsonLine)
	{
	    JsonElement jelement = new JsonParser().parse(jsonLine);
	    JsonObject  jobject = jelement.getAsJsonObject();
	    jobject = jobject.getAsJsonObject("question");
	    JsonArray jarray = jobject.getAsJsonArray("evidencelist");
	    int i = 0;
	    jobject = jarray.get(i).getAsJsonObject();
	    while(jobject.get("text") == null)
	    {
	    	i++;
	    	jobject = jarray.get(i).getAsJsonObject();
	    }
	    List<String> result = new ArrayList<String>();
	    String ans = jobject.get("text").getAsString();
	    result.add(0, ans);
	    String document = jobject.get("document").getAsString();
	    String[] documentUrlSplited = document.split("/question/document/", 2);
	    result.add(1, documentUrlSplited[documentUrlSplited.length-1]);
	    return result;
	}
}
