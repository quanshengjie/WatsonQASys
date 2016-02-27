package com.theteam.server;

public class AnswerURLManagerLocalFileTest extends AnswerURLManagerTest {

	@Override
	public IAnswerURLManager GetInstance() {
		return new AnswerURLManagerLocalFile();
	}

}
