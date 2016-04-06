package com.theteam.server;

public final class MissingQuestionItem
{
	private String question;
	private String confidence;
	
	MissingQuestionItem(String question, String confidence)
	{
		this.question = question;
		this.confidence = confidence;
	}
	
	String getQuestion()	{	return this.question;	}
	String getConfidence()	{	return this.confidence;	}
}