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

import org.testng.annotations.*;

import com.cd.reddit.http.util.RedditApiResourceConstants;
import com.cd.reddit.json.jackson.RedditJsonParser;
import com.cd.reddit.json.mapping.RedditAccount;
import com.cd.reddit.json.mapping.RedditComment;
import com.cd.reddit.json.mapping.RedditJsonMessage;
import com.cd.reddit.json.mapping.RedditLink;
import com.cd.reddit.json.mapping.RedditMessage;
import com.cd.reddit.json.mapping.RedditSubreddit;
import com.cd.reddit.json.util.RedditComments;

public class RedditTest {

	static Reddit testReddit = null;
	static final String nl = System.getProperty("line.separator");
	
	//Throway account for proof-of-concept purposes
	final String testUserAgent = "JavaJerseyTestBot/1.0 by Cory Dissinger";		

	@BeforeSuite
	public void init(){
		if(testReddit == null){
			testReddit = new Reddit(testUserAgent);
		}		
	}
	
	@BeforeMethod
	public void waitForNextRequest(){
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	

	@Test(groups = { "login" })
	private void testLogin(){
        System.out.println(nl);    	
        System.out.println("----------- TESTING LOGIN -----------");
        System.out.println(nl);		
		
		RedditJsonMessage respMessage = null;
		
		try {
			respMessage = testReddit.login("JavaJerseyTestBot", "JavaJerseyTestBot");
		} catch (RedditException e) {
			e.printStackTrace();
		} 
		
		System.out.println(respMessage);
	}

	@Test(dependsOnGroups = { "login"} )	
	public void testMeJson(){
        System.out.println(nl);    	
        System.out.println("----------- TESTING ME.JSON -----------");
        System.out.println(nl);		
		
		RedditAccount account = null;
		
		try {
			account = testReddit.meJson();
		} catch (RedditException e) {
			e.printStackTrace();
		} 
		
		System.out.println(account);
		assertEquals(true, account.getModhash() != null);		
	}	

	@Test(dependsOnGroups = { "login"} )
	public void testCommentAndDelete(){
        System.out.println(nl);    	
        System.out.println("----------- TESTING COMMENT AND DELETE -----------");
        System.out.println(nl);		
		
		String testComment = "I hope you don't mind my <h1>TEST</h1> at all!";
		String parentThing = "t3_1nsakt";
		RedditJsonMessage message = null;
		
		try {
			message = testReddit.comment(testComment, parentThing);
		} catch (RedditException e) {
			e.printStackTrace();
		}		
		
		System.out.println(message.toString());
		assertEquals(true, message.getErrors().isEmpty());
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		
		//TODO: Provide better mechanism for users to extract underlying data from a JsonMessage 
		//TODO: Prove comments can be deleted
		/*
		RedditJsonParser parser = new RedditJsonParser(message.getData().get("things").asText());
		RedditComment theComment = null;		
		
		try {
			theComment = parser.parseCommentsList().get(0);
		} catch (RedditException e) {
			e.printStackTrace();
		}
		
		try {
			message = testReddit.delete(theComment.getName());
		} catch (RedditException e) {
			e.printStackTrace();
		}*/		
	}	
	
	@Test(dependsOnGroups = { "login"} )
	public void testCommentsForAndMore(){
        System.out.println(nl);    	
        System.out.println("----------- TESTING COMMENTS FOR AND MORE -----------");
        System.out.println(nl);		
		
		RedditComments comments = null;
		
		try {
			comments = testReddit.commentsFor("videos", "1nfrqm");
		} catch (RedditException e) {
			e.printStackTrace();
		}		

		System.out.println(comments.toString());
		
		assertEquals(true, comments.getParentLink() != null);		
		assertEquals(false, comments.getComments().isEmpty());
		assertEquals(true, comments.getMore() != null);
		
		List<RedditComment> moreComments = null;
		
		try {
			moreComments = testReddit.moreChildrenFor(comments, "top");
		} catch (RedditException e) {
			e.printStackTrace();
		}
		
		System.out.println(moreComments.toString());		

		assertEquals(false, moreComments.isEmpty());		
		
		//Also test the 'more' and show an intuitive way of using..
		//final String childComment = comments.get(1).getId();
	}	
	
	@Test(dependsOnGroups = { "login"} )
	public void testSubredditsNew(){
        System.out.println(nl);    	
        System.out.println("----------- TESTING SUBREDDITS NEW -----------");
        System.out.println(nl);		
		
		testReddit = new Reddit(testUserAgent);
		List<RedditSubreddit> subreddits = null;
		
		try {
			subreddits = testReddit.subreddits("new");
		} catch (RedditException e) {
			e.printStackTrace();
		}		

		for(RedditSubreddit subreddit : subreddits){
			System.out.println(subreddit);
		}
		
		assertEquals(false, subreddits.isEmpty());
	}
	
	@Test(dependsOnGroups = { "login"} )
	public void testListingsFor(){
        System.out.println(nl);    	
        System.out.println("----------- TESTING LISTING FOR -----------");
        System.out.println(nl);		
		
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
	
	@Test(dependsOnGroups = { "login"} )
	public void testInfoFor(){
        System.out.println(nl);    	
        System.out.println("----------- TESTING INFO FOR -----------");
        System.out.println(nl);		
		
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
        
	@Test(dependsOnGroups = { "login"} )
    public void testInbox() {
        System.out.println(nl);    	
        System.out.println("----------- TESTING INBOX -----------");
        System.out.println(nl);
        
        List<RedditMessage> messages = null;
        try {
            messages = testReddit.messages(RedditApiResourceConstants.INBOX);
        } catch (RedditException e) {
            e.printStackTrace();
        }
        
        for (RedditMessage message : messages) {
            System.out.println(message.toString());
        }
        
        assertEquals(false, messages.isEmpty());
    }
}
