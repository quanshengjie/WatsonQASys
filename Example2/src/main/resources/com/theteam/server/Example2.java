package com.theteam.server;

public class Example2 {

	public static void main(String[] args) {
		QuestionAnswerSystem qasys = new Watson();
		qasys.Init();
		System.out.println(qasys.GetAnswer("What is ECE admission requirements?"));
	}

}
