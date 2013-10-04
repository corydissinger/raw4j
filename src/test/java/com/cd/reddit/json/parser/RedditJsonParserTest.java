/*
Copyright 2013 Cory Dissinger

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at 

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
*/

package com.cd.reddit.json.parser;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cd.reddit.json.jackson.RedditJsonParser;
import com.cd.reddit.json.mapping.RedditAccount;
import com.cd.reddit.json.mapping.RedditComment;
import com.cd.reddit.json.mapping.RedditLink;
import com.cd.reddit.json.mapping.RedditSubreddit;
import com.cd.reddit.json.mapping.RedditType;
import com.cd.reddit.json.util.RedditComments;

public class RedditJsonParserTest {

	RedditJsonParser testParser;
	long before;
	long after;
	
	@Before
	public void cleanUp() throws InterruptedException{
		testParser = null;
		System.gc();
		System.runFinalization();
		Thread.sleep(1000);
		before = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	}
	
	@After
	public void measureUsage() throws InterruptedException{
		System.gc();
		System.runFinalization();
		Thread.sleep(1000);
		after = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long assumedSize = after - before;
		System.out.println("Amount of bytes used before/after=" + assumedSize);
	}
	
	@Test
	public void parseListingJson(){
		List<RedditLink> parsedTypes = null;
		InputStream jsonStream = this.getClass().getResourceAsStream("/politicsnew.json");
		testParser = new RedditJsonParser(convertStreamToString(jsonStream));
		
		try {
			parsedTypes = testParser.parseLinks();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(RedditType parsedType : parsedTypes){
			System.out.println(parsedType.toString());			
		}
		
		assertEquals(true, parsedTypes.get(0) instanceof RedditLink);
	}
	
	@Test
	public void parseCommentsJson(){
		RedditComments parsedComments = null;		
		InputStream jsonStream = this.getClass().getResourceAsStream("/comments.json");
		testParser = new RedditJsonParser(convertStreamToString(jsonStream));
		
		try {
			parsedComments = testParser.parseComments();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(RedditType parsedType : parsedComments.getComments()){
			System.out.println(parsedType.toString());			
		}		
		
		assertEquals(true, parsedComments.getComments().get(0) instanceof RedditComment);
	}
	
	@Test
	public void parseSubredditsJson(){
		List<RedditSubreddit> parsedTypes = null;		
		InputStream jsonStream = this.getClass().getResourceAsStream("/subreddits-popular.json");
		testParser = new RedditJsonParser(convertStreamToString(jsonStream));
		
		try {
			parsedTypes = testParser.parseSubreddits();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(RedditType parsedType : parsedTypes){
			System.out.println(parsedType.toString());			
		}		
		
		assertEquals(true, parsedTypes.get(0) instanceof RedditSubreddit);
	}	

	@Test
	public void parseAccountJson(){
		List<RedditAccount> parsedTypes = null;		
		InputStream jsonStream = this.getClass().getResourceAsStream("/user.json");
		testParser = new RedditJsonParser(convertStreamToString(jsonStream));
		
		try {
			parsedTypes = testParser.parseAccounts();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(RedditType parsedType : parsedTypes){
			System.out.println(parsedType.toString());			
		}		
		
		assertEquals(true, parsedTypes.get(0) instanceof RedditAccount);
	}	
	
	//TODO: Add message test json. This means put a test resource in the appropriate src/test/resources/ directory
	
	//Never do this in non-test code, Scanner is not closed!
	private String convertStreamToString(InputStream is){
		Scanner sc = new Scanner(is).useDelimiter("\\A");
		return sc.hasNext() ? sc.next() : "";
	}	
}
