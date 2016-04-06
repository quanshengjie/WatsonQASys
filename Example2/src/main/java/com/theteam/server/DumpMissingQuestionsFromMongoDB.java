package com.theteam.server;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

public final class DumpMissingQuestionsFromMongoDB implements IDumpMissingQuestions {

	final static String DATABASE_NAME = "question-record";
	
	public List<MissingQuestionItem> Dump() {
		final List<MissingQuestionItem> list = new ArrayList<MissingQuestionItem>();
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase(DATABASE_NAME);
	
		FindIterable<Document> iterable = db.getCollection("missing-questions").find();
		iterable.forEach(new Block<Document>() {
		    public void apply(final Document document) {
		    	if(document.containsKey("question"))
		    	{
		    		Document questionItem = (Document)document.get("question");
		    		MissingQuestionItem item = new MissingQuestionItem(questionItem.getString("question"),
		    														   questionItem.getString("confidence"));
		    		list.add(item);
		    	}
		    }
		});
		mongoClient.close();
		return list;
	}

}
