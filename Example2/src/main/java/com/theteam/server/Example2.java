package com.theteam.server;

public class Example2 {

	public static void main(String[] args) {
		IQuestionAnswerSystem qasys = new Watson();
		qasys.Init();
		System.out.println(qasys.GetAnswer("When is the deadline for Autumn 2016 undergraduate application?"));
	}

}
