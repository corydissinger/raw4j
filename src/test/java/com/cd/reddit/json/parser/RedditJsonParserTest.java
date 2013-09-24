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

import java.io.InputStream;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
		List<RedditLink> parsedTypes = null;
		InputStream jsonStream = this.getClass().getResourceAsStream("/politicsnew.json");
		testParser = new RedditJsonParser(jsonStream);
		
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
		List<RedditComment> parsedTypes = null;		
		InputStream jsonStream = this.getClass().getResourceAsStream("/comments.json");
		testParser = new RedditJsonParser(jsonStream);
		
		try {
			parsedTypes = testParser.parseComments();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(RedditType parsedType : parsedTypes){
			System.out.println(parsedType.toString());			
		}		
		
		assertEquals(true, parsedTypes.get(0) instanceof RedditComment);
	}
	
	@Test
	public void parseSubredditsJson(){
		List<RedditSubreddit> parsedTypes = null;		
		InputStream jsonStream = this.getClass().getResourceAsStream("/subreddits-popular.json");
		testParser = new RedditJsonParser(jsonStream);
		
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
		testParser = new RedditJsonParser(jsonStream);
		
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
	
	//TODO
	//Add message test json
}
