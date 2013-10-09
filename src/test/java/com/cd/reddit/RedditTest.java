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

import org.codehaus.jackson.JsonNode;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.cd.reddit.http.util.RedditApiResourceConstants;
import com.cd.reddit.json.jackson.RedditJsonParser;
import com.cd.reddit.json.mapping.RedditAccount;
import com.cd.reddit.json.mapping.RedditComment;
import com.cd.reddit.json.mapping.RedditJsonMessage;
import com.cd.reddit.json.mapping.RedditLink;
import com.cd.reddit.json.mapping.RedditMessage;
import com.cd.reddit.json.mapping.RedditSubreddit;
import com.cd.reddit.json.util.RedditComments;

/**
 * @author Cory
 *
 * Uses TestNG because JUnit is not designed for state-based tests.
 *
 * @see <a href="http://testng.org/doc/documentation-main.html">Full TestNG Docs</a>
 */
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
		
		String testComment = "I hope you don't mind my TEST at all! Sorry Reddit, just integration testing...";
		//TODO: Stop harassing the same link with this bot's spammy comments
		String parentThing = "t3_1o2shx";
		RedditJsonMessage message = null;
		
		try {
			message = testReddit.comment(testComment, parentThing);
		} catch (RedditException e) {
			e.printStackTrace();
		}		

		System.out.println(message.toString());
        System.out.println(nl);
        
		assertEquals(true, message.getErrors().isEmpty());
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		
		final JsonNode thingsNode = message.getData().get("things");
		
		//Annoying that we have to keep instantiating new Parsers...
		RedditJsonParser parser 			= new RedditJsonParser(thingsNode);
		List<RedditComment> theOneComment 	= null;
		
		try {
			theOneComment = parser.parseCommentsOnly();
		} catch (RedditException e) {
			e.printStackTrace();
		}
        
        //The first and only Comment type Thing should be the one we just made.
        RedditComment theComment = theOneComment.get(0);

        System.out.println(theComment);        
        System.out.println(nl);        
        
		try {
			//It seems that the /api/delete method returns HTTP 200 ok and an empty JSON object response... don't try to parse it (yet)
			testReddit.delete(theComment.getId());
		} catch (RedditException e) {
			e.printStackTrace();
		}
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
