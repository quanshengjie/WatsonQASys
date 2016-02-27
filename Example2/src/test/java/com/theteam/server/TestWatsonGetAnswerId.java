package com.theteam.server;


import static org.junit.Assert.*;

import org.junit.Test;

public class TestWatsonGetAnswerId {

	private IAnswerIdManager CreateInstance()
	{
		IAnswerIdManager ins = new WatsonGetAnswerId();
		return ins;
	}
	@Test
	public void test() {
		String ans = "Although you and your family are expected to contribute as much as you can toward expenses (from income, savings and other assets), financial aid can help you bridge the gap between your resources and the cost of your education.";
		IAnswerIdManager ins = CreateInstance();
		assertEquals("5", ins.GetID(ans));
	}

}
