/*
This file is part of reddit-jersey-client.

reddit-jersey-client is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

reddit-jersey-client is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with reddit-jersey-client.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.cd.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

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
		String userAgent1 = "test1";
		obj1 = new RedditRequestInput(testSegments1, userAgent1, queryParams, formParams);
		
		System.out.println(obj1);		
	}
	
	@Test
	public void testHashCodeTrue(){
		System.out.println("Testing testHashCodeTrue");
		System.out.println(nl);		
		
		List<String> testSegments1 = createTestSegments1();
		Map<String, String> queryParams = createTestQueryParams1();
		Map<String, String> formParams = createTestFormParams1();
		String userAgent1 = "test1";
		obj1 = new RedditRequestInput(testSegments1, userAgent1, queryParams, formParams);
		
		List<String> testSegments2 = createTestSegments1();
		Map<String, String> queryParams2 = createTestQueryParams1();
		Map<String, String> formParams2 = createTestFormParams1();
		String userAgent2 = "test1";
		obj2 = new RedditRequestInput(testSegments2, userAgent2, queryParams2, formParams2);
		
		assertEquals(obj1.hashCode(), obj2.hashCode());
	}
	
	@Test
	public void testHashCodeFalse(){
		System.out.println("Testing testHashCodeFalse");
		System.out.println(nl);		
		
		List<String> testSegments1 = createTestSegments1();
		Map<String, String> queryParams = createTestQueryParams1();
		Map<String, String> formParams = createTestFormParams1();
		String userAgent1 = "test1";
		obj1 = new RedditRequestInput(testSegments1, userAgent1, queryParams, formParams);
		
		List<String> testSegments2 = createTestSegments2();
		Map<String, String> queryParams2 = createTestQueryParams2();
		Map<String, String> formParams2 = createTestFormParams2();
		String userAgent2 = "mytests";
		obj2 = new RedditRequestInput(testSegments2, userAgent2, queryParams2, formParams2);
		
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
