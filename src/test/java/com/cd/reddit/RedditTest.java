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


package com.cd.reddit;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.cd.reddit.exception.RedditException;
import com.cd.reddit.json.mapping.RedditLink;
import com.cd.reddit.json.mapping.RedditSubreddit;

public class RedditTest {

	Reddit testReddit;
	
	//Throway account for proof-of-concept purposes
	final String testUserAgent = "JavaJerseyTestBot/1.0 by Cory Dissinger";		
	
	@Before
	public void waitForNextRequest(){
		testReddit = null;
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	

	@Test
	public void testLogin(){
		testReddit = new Reddit(testUserAgent);
		
		try {
			testReddit.login("JavaJerseyTestBot", "JavaJerseyTestBot");
		//TODO: This exception fails to accurately pinpoint root cause in application!
		} catch (RedditException e) {
			e.printStackTrace();
		} 
	}
	
	@Test
	public void testSubredditsNew(){
		testReddit = new Reddit(testUserAgent);
		
		List<RedditSubreddit> subreddits = null;
		
		try {
			subreddits = testReddit.subredditsNew();
		//TODO: This exception fails to accurately pinpoint root cause in application!
		} catch (RedditException e) {
			e.printStackTrace();
		}		
		
		assertEquals(false, subreddits.isEmpty());
	}
	
	@Test
	public void testSubredditsPopular(){
		testReddit = new Reddit(testUserAgent);
		
		List<RedditSubreddit> subreddits = null;
		
		try {
			subreddits = testReddit.subredditsPopular();
		//TODO: This exception fails to accurately pinpoint root cause in application!
		} catch (RedditException e) {
			e.printStackTrace();
		}		
		
		assertEquals(false, subreddits.isEmpty());
	}
	
	@Test
	public void testListingsFor(){
		testReddit = new Reddit(testUserAgent);
		
		List<RedditLink> listing = null;
		
		try {
			listing = testReddit.listingFor("java", "top");
		//TODO: This exception fails to accurately pinpoint root cause in application!
		} catch (RedditException e) {
			e.printStackTrace();
		}		
		
		assertEquals(false, listing.isEmpty());
	}	
}
