package com.theteam.server;

import static com.mongodb.client.model.Filters.eq;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class RecordMissingQuestionToMongoDB implements IMissingQuestionsRecorder {

	final static String DATABASE_NAME = "question-record";
	static
	{
		
	}
	
	private Queue<String> questionQueue = new ConcurrentLinkedQueue<String>();
	private Queue<String> confidenceQueue = new ConcurrentLinkedQueue<String>();
	private Boolean threadBusy = false;
	
	public void AddMissingQuestion(String question, String confidence) {
		questionQueue.add(question);
		confidenceQueue.add(confidence);
		synchronized(threadBusy)
		{
			if(!threadBusy)
			{
				threadBusy = true;
				WriteToMongoDBThread thread = new WriteToMongoDBThread();
				thread.start();
			}
		}

	}
	
	private class WriteToMongoDBThread  extends Thread
	{
		private Thread t;
		
		public void run()
		{
			MongoClient mongoClient = new MongoClient();
			MongoDatabase db = mongoClient.getDatabase(DATABASE_NAME);
			while(questionQueue.size() > 0)
			{
				String question = questionQueue.poll();
				String confidence = confidenceQueue.poll();
				
				MongoCollection<Document> database = db.getCollection("missing-questions");
				FindIterable<Document> iterable = database.find(eq("question.question", question));
				if(iterable.first() == null)
				{
					database.insertOne(new Document("question", new Document()
							.append("question", question)
							.append("confidence", confidence)));
				}
			}
			mongoClient.close();
			synchronized(threadBusy)
			{
				threadBusy = false;
			}
		}
		
	   public void start ()
	   {
	      if (t == null)
	      {
	         t = new Thread (this, "WriteToMongoDB Thread");
	         t.start ();
	      }
	   }
	}
	
//	

}
