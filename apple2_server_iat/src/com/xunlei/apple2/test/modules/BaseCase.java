package com.xunlei.apple2.test.modules;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

public class BaseCase {
	public static ApiExcutor excutor;
	
	public BaseCase() {
		excutor = new ApiExcutor();
	}
	
	@Rule
	public TestName testName = new TestName();
	
	@Before
	public void setup() {
		System.out.println("**********" + testName.getMethodName()
				+ " START**********");
	}

	@After
	public void tearDown() {
		System.out.println("**********" + testName.getMethodName()
				+ " END**********");
	}

	public static void sleep(int time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
