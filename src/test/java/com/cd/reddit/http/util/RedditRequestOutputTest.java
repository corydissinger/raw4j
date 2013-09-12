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

package com.cd.reddit.http.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Before;
import org.junit.Test;

import com.cd.reddit.http.util.RedditRequestResponse;

public class RedditRequestOutputTest {
	
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
