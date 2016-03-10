package com.theteam.server;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestWatsonPinger1 {

	private static IWatsonPinger CreateInstance()
	{
		return new WatsonPinger1();
	}
	@Test
	public final void testLightPing() {
		IWatsonPinger ins = CreateInstance();
		int result = ins.lightPing();
		assertEquals(2, result/100);
	}

	@Test
	public final void testHeavyPing() {
		IWatsonPinger ins = CreateInstance();
		int result = ins.heavyPing();
		assertEquals(2, result/100);
	}

}
