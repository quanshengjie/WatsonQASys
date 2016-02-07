package com.theteam.server;
import com.google.gson.*;

public class ParseJsonAndReturnFirst
{
	public static String parse(String jsonLine)
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
	    String result = jobject.get("text").getAsString();
	    return result;
	}
}
