package com.theteam.server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.*;


public class Example2 {

	public static void main(String[] args) {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase("question-record");
		
//		db.getCollection("missing-questions").insertOne(
//				new Document("question", new Document()
//						.append("question", "Where can I find the best pizza in OSU")
//						.append("confidence", "0.037562")));
		
		FindIterable<Document> iterable = db.getCollection("missing-questions").find();
		iterable.forEach(new Block<Document>() {
		    public void apply(final Document document) {
		    	if(document.containsKey("question"))
		    	{
		    		Document questionItem = (Document)document.get("question");
		    		System.out.println(questionItem.getString("question"));
		    		System.out.println(questionItem.getString("confidence"));
		    		System.out.println();
		    	}
		    }
		});
	}

}
