/*
Copyright 2013 Cory Dissinger

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at 

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.*/

package com.cd.reddit.http.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Before;
import org.junit.Test;

import com.cd.reddit.http.util.RedditRequestResponse;

public class RedditRequestResponseTest {
	
	private static final String nl = System.getProperty("line.separator");	
	
	RedditRequestResponse obj1;
	RedditRequestResponse obj2;
	
	@Before
	public void resetTestObjects(){
		obj1 = null;
		obj2 = null;
	}
	
	@Test
	public void testToString(){
		System.out.println("Testing testToString");
		System.out.println(nl);
		
		int testStatus1	 = 342;
		String testBody1 = "mytest";
		obj1 = new RedditRequestResponse(testStatus1, testBody1);
		
		System.out.println(obj1);
	}
	
	@Test
	public void testHashCodeTrue(){
		System.out.println("Testing testHashCodeTrue");
		System.out.println(nl);
		
		int testStatus1	 = 342;
		String testBody1 = "mytest";
		obj1 = new RedditRequestResponse(testStatus1, testBody1);
		
		
		int testStatus2	 = 342;
		String testBody2 = "mytest";
		obj2 = new RedditRequestResponse(testStatus2, testBody2);
		
		assertEquals(obj1.hashCode(), obj2.hashCode());
	}
	
	@Test
	public void testHashCodeFalse(){
		System.out.println("Testing testHashCodeFalse");
		System.out.println(nl);		
		
		int testStatus1	 = 342;
		String testBody1 = "mytest";
		obj1 = new RedditRequestResponse(testStatus1, testBody1);
		
		
		int testStatus2	 = 345;
		String testBody2 = "mytest!!";
		obj2 = new RedditRequestResponse(testStatus2, testBody2);
		
		assertNotSame(obj1.hashCode(), obj2.hashCode());
	}	
}
