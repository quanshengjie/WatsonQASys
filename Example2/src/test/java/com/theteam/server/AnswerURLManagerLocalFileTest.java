package com.theteam.server;

public class AnswerURLManagerLocalFileTest extends AnswerURLManagerTest {

	@Override
	public AnswerURLManager GetInstance() {
		return new AnswerURLManagerLocalFile();
	}

}
