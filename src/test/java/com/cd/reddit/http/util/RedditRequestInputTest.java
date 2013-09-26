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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.cd.reddit.http.util.RedditRequestInput;

public class RedditRequestInputTest {

	private static final String nl = System.getProperty("line.separator");	
	
	RedditRequestInput obj1;
	RedditRequestInput obj2;
	
	@Before
	public void resetTestObjects(){
		obj1 = null;
		obj2 = null;
	}
	
	@Test
	public void testToString(){
		System.out.println("Testing testToString");
		System.out.println(nl);		
		
		List<String> testSegments1 = createTestSegments1();
		Map<String, String> queryParams = createTestQueryParams1();
		Map<String, String> formParams = createTestFormParams1();
		obj1 = new RedditRequestInput(testSegments1, queryParams, formParams);
		
		System.out.println(obj1);		
	}
	
	@Test
	public void testHashCodeTrue(){
		System.out.println("Testing testHashCodeTrue");
		System.out.println(nl);		
		
		List<String> testSegments1 = createTestSegments1();
		Map<String, String> queryParams = createTestQueryParams1();
		Map<String, String> formParams = createTestFormParams1();
		obj1 = new RedditRequestInput(testSegments1, queryParams, formParams);
		
		List<String> testSegments2 = createTestSegments1();
		Map<String, String> queryParams2 = createTestQueryParams1();
		Map<String, String> formParams2 = createTestFormParams1();
		obj2 = new RedditRequestInput(testSegments2, queryParams2, formParams2);
		
		assertEquals(obj1.hashCode(), obj2.hashCode());
	}
	
	@Test
	public void testHashCodeFalse(){
		System.out.println("Testing testHashCodeFalse");
		System.out.println(nl);		
		
		List<String> testSegments1 = createTestSegments1();
		Map<String, String> queryParams = createTestQueryParams1();
		Map<String, String> formParams = createTestFormParams1();
		obj1 = new RedditRequestInput(testSegments1, queryParams, formParams);
		
		List<String> testSegments2 = createTestSegments2();
		Map<String, String> queryParams2 = createTestQueryParams2();
		Map<String, String> formParams2 = createTestFormParams2();
		obj2 = new RedditRequestInput(testSegments2, queryParams2, formParams2);
		
		assertNotSame(obj1.hashCode(), obj2.hashCode());
	}
	
	private List<String> createTestSegments1() {
		List<String> testSegments1 = new ArrayList<String>(); 
		testSegments1.add("MYTEST");
		testSegments1.add("MYTEST2");
		return testSegments1;
	}	

	private List<String> createTestSegments2() {
		List<String> testSegments2 = new ArrayList<String>(); 
		testSegments2.add("MYTEST3");
		testSegments2.add("MYTEST4");
		return testSegments2;
	}
	
	private Map<String, String> createTestQueryParams1() {
		Map<String, String> testFormParams1 = new HashMap<String, String>(); 
		testFormParams1.put("MYTEST", "MYTEST2");
		testFormParams1.put("MYTEST2", "MYTEST3");
		return testFormParams1;
	}	

	private Map<String, String> createTestQueryParams2() {
		Map<String, String> testQueryParams2 = new HashMap<String, String>(); 
		testQueryParams2.put("MYTEST5", "MYTEST6");
		testQueryParams2.put("MYTEST7", "MYTEST8");
		return testQueryParams2;
	}
	
	private Map<String, String> createTestFormParams1() {
		Map<String, String> testFormParams1 = new HashMap<String, String>(); 
		testFormParams1.put("TEST", "TEST2");
		testFormParams1.put("TEST2", "TEST3");
		return testFormParams1;
	}	

	private Map<String, String> createTestFormParams2() {
		Map<String, String> testFormParams2 = new HashMap<String, String>(); 
		testFormParams2.put("TEST5", "TEST6");
		testFormParams2.put("TEST7", "TEST8");
		return testFormParams2;
	}	
}
