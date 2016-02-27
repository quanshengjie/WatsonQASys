package com.theteam.server;

import static org.junit.Assert.*;

import org.junit.Test;

public abstract class AnswerURLManagerTest {

	public abstract IAnswerURLManager GetInstance();
	
	private IAnswerURLManager instance = GetInstance();

	@Test
	public void test1() {
		String expected = "http://undergrad.osu.edu/visit/index.html";
		String id = "1";
		instance.Init();
		assertEquals(expected, instance.GetURL(id));
	}
	
	@Test
	public void test2() {
		String expected = "http://undergrad.osu.edu/money-matters/money-management.html";
		String id = "2";
		instance.Init();
		assertEquals(expected, instance.GetURL(id));
	}

	@Test
	public void test3() {
		String expected = "http://undergrad.osu.edu/admissions/quick-facts.html";
		String id = "3";
		instance.Init();
		assertEquals(expected, instance.GetURL(id));
	}
	@Test
	public void test17() {
		String expected = "https://engineering.osu.edu/news/2015/10/making-cars-future-stronger-and-lighter-while-using-less-energy";
		String id = "17";
		instance.Init();
		assertEquals(expected, instance.GetURL(id));
	}
	
	@Test
	public void test233() {
		String expected = "https://cse.osu.edu/department/visitors";
		String id = "233";
		instance.Init();
		assertEquals(expected, instance.GetURL(id));
	}
	
	@Test
	public void test165() {
		String expected = "https://my.osu.edu/";
		String id = "165";
		instance.Init();
		assertEquals(expected, instance.GetURL(id));
	}
}
