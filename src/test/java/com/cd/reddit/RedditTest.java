/*
Copyright 2013 Cory Dissinger

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at 

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
*/


package com.cd.reddit;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.cd.reddit.json.mapping.RedditLink;
import com.cd.reddit.json.mapping.RedditSubreddit;
import com.cd.reddit.json.util.RedditJsonConstants;

public class RedditTest {

	Reddit testReddit = null;
	
	//Throway account for proof-of-concept purposes
	final String testUserAgent = "JavaJerseyTestBot/1.0 by Cory Dissinger";		
	
	@Before
	public void waitForNextRequest(){
		if(testReddit == null){
			testReddit = new Reddit(testUserAgent);
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	

	@Test
	public void testLogin(){
		try {
			testReddit.login("JavaJerseyTestBot", "JavaJerseyTestBot");
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
		} catch (RedditException e) {
			e.printStackTrace();
		}		

		for(RedditSubreddit subreddit : subreddits){
			System.out.println(subreddit);
		}		
		
		assertEquals(false, subreddits.isEmpty());
	}
	
	@Test
	public void testSubredditsPopular(){
		testReddit = new Reddit(testUserAgent);
		
		List<RedditSubreddit> subreddits = null;
		
		try {
			subreddits = testReddit.subredditsPopular();
		} catch (RedditException e) {
			e.printStackTrace();
		}		
		
		for(RedditSubreddit subreddit : subreddits){
			System.out.println(subreddit);
		}
		
		assertEquals(false, subreddits.isEmpty());
	}
	
	@Test
	public void testListingsFor(){
		testReddit = new Reddit(testUserAgent);
		
		List<RedditLink> listing = null;
		
		try {
			listing = testReddit.listingFor("java", "top");
		} catch (RedditException e) {
			e.printStackTrace();
		}		

		for(RedditLink link : listing){
			System.out.println(link);
		}		
		
		assertEquals(false, listing.isEmpty());
	}
	
	@Test
	public void testInfoFor(){
		testReddit = new Reddit(testUserAgent);
		
		List<RedditLink> listing = null;
		
		try {
			listing = testReddit.infoForId("t3_1n6uck");
		} catch (RedditException e) {
			e.printStackTrace();
		}		

		for(RedditLink link : listing){
			System.out.println(link);
		}		
		
		assertEquals(false, listing.isEmpty());
	}	
}
