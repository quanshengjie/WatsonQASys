package com.theteam.server;


import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;

public class TestWatsonGetAnswerId {

	private AnswerIdManager CreateInstance()
	{
		AnswerIdManager ins = new WatsonGetAnswerId();
		return ins;
	}
	@Test
	public void test() {
		String ans = "Although you and your family are expected to contribute as much as you can toward expenses (from income, savings and other assets), financial aid can help you bridge the gap between your resources and the cost of your education.";
		AnswerIdManager ins = CreateInstance();
		Assert.assertEquals("5", ins.GetID(ans));
	}

}
