/*
This file is part of raw4j.

raw4j is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

raw4j is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with raw4j.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.cd.reddit.json.parser;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cd.reddit.json.exception.RedditJsonException;
import com.cd.reddit.json.mapping.RedditAccount;
import com.cd.reddit.json.mapping.RedditComment;
import com.cd.reddit.json.mapping.RedditLink;
import com.cd.reddit.json.mapping.RedditSubreddit;
import com.cd.reddit.json.mapping.RedditType;

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
		List<RedditType> parsedTypes = null;
		InputStream jsonStream = this.getClass().getResourceAsStream("/politicsnew.json");
		String testJson = convertStreamToString(jsonStream);
		testParser = new RedditJsonParser(testJson);
		
		try {
			parsedTypes = testParser.parse();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (RedditJsonException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(RedditType parsedType : parsedTypes){
			System.out.println(parsedType.toString());			
		}
		
		assertEquals(true, parsedTypes.get(0) instanceof RedditLink);
	}
	
	@Test
	public void parseCommentsJson(){
		List<RedditType> parsedTypes = null;		
		InputStream jsonStream = this.getClass().getResourceAsStream("/comments.json");
		String testJson = convertStreamToString(jsonStream);
		testParser = new RedditJsonParser(testJson);
		
		try {
			parsedTypes = testParser.parse();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (RedditJsonException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(RedditType parsedType : parsedTypes){
			System.out.println(parsedType);			
		}		
		
		//Assert that first RedditType is the original link
		//Assert that all remaining elements are of comment type
		assertEquals(true, parsedTypes.get(0) instanceof RedditLink);
		assertEquals(true, parsedTypes.get(1) instanceof RedditComment);
	}
	
	@Test
	public void parseSubredditsJson(){
		List<RedditType> parsedTypes = null;		
		InputStream jsonStream = this.getClass().getResourceAsStream("/subreddits-popular.json");
		String testJson = convertStreamToString(jsonStream);
		testParser = new RedditJsonParser(testJson);
		
		try {
			parsedTypes = testParser.parse();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (RedditJsonException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(RedditType parsedType : parsedTypes){
			System.out.println(parsedType);			
		}		
		
		assertEquals(true, parsedTypes.get(0) instanceof RedditSubreddit);
	}	

	@Test
	public void parseAccountJson(){
		List<RedditType> parsedTypes = null;		
		InputStream jsonStream = this.getClass().getResourceAsStream("/user.json");
		String testJson = convertStreamToString(jsonStream);
		testParser = new RedditJsonParser(testJson);
		
		try {
			parsedTypes = testParser.parse();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (RedditJsonException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(RedditType parsedType : parsedTypes){
			System.out.println(parsedType);			
		}		
		
		assertEquals(true, parsedTypes.get(0) instanceof RedditAccount);
	}	
	
	//TODO
	//Add message test json
	
	//Never do this in non-test code, Scanner is not closed!
	private String convertStreamToString(InputStream is){
		Scanner sc = new Scanner(is).useDelimiter("\\A");
		return sc.hasNext() ? sc.next() : "";
	}
}
