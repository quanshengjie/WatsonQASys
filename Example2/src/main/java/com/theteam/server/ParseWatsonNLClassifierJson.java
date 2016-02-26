package com.theteam.server;
import java.util.List;

import com.google.gson.*;

public class ParseWatsonNLClassifierJson
{
	public static String parse(String jsonLine)
	{
	    JsonElement jelement = new JsonParser().parse(jsonLine);
	    JsonObject  jobject = jelement.getAsJsonObject();
	    JsonArray jarray = jobject.getAsJsonArray("classes");
	    int i = 0;
	    jobject = jarray.get(i).getAsJsonObject();
	    while(jobject.get("class_name") == null)
	    {
	    	i++;
	    	jobject = jarray.get(i).getAsJsonObject();
	    }
	    String result = jobject.get("class_name").getAsString();
	    return result;
	}

	public static void parse(String jsonLine, List<String> classes, List<Double> confidence)
	{
		classes.clear();
		confidence.clear();
	    JsonElement jelement = new JsonParser().parse(jsonLine);
	    JsonObject  jobject = jelement.getAsJsonObject();
	    JsonArray jarray = jobject.getAsJsonArray("classes");
	    int i = 0;
	    while(i < jarray.size())
	    {
	    	jobject = jarray.get(i).getAsJsonObject();
	    	String name = jobject.get("class_name").getAsString();
	    	String confidenceStr = jobject.get("confidence").getAsString();
	    	if(name != null && confidenceStr != null)
	    	{
	    		classes.add(name.trim());
	    		Double con = Double.parseDouble(confidenceStr.trim());
	    		confidence.add(con);
	    	}
	    	i++;
	    }
	}
}
