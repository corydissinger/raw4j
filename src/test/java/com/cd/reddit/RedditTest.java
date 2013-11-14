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

	Reddit testReddit = null;
	final String nl = System.getProperty("line.separator");
	
	//Throway account for proof-of-concept purposes
	final String testUserAgent = "JavaJerseyTestBot/1.0 by Cory Dissinger";		

	//Dynamic values that change with each test
	RedditSubreddit targetSubreddit = null;
	RedditLink	   	targetLink	   	= null;
	
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

	@Test
	private void login(){
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

    @Test
    private void newCaptcha(){
        System.out.println(nl);
        System.out.println("----------- TESTING NEW CAPTCHA -----------");
        System.out.println(nl);

        RedditJsonMessage respMessage = null;

        try {
            respMessage = testReddit.newCaptcha();
        } catch (RedditException e) {
            e.printStackTrace();
        }

        System.out.println(respMessage);
        assertEquals(true, respMessage.getIden() != null);
    }

	@Test(dependsOnGroups = { "readReddit" },
		  dependsOnMethods = { "login" } )	
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

	// After logging in, we will 'read reddit' and not hard-code things to harass.

	@Test(groups = { "readReddit" },
		  dependsOnMethods = { "login" } )
	public void subredditsPopular(){
        System.out.println(nl);    	
        System.out.println("----------- TESTING SUBREDDITS NEW -----------");
        System.out.println(nl);		
		
		List<RedditSubreddit> subreddits = null;
		
		try {
			subreddits = testReddit.subreddits("popular");
		} catch (RedditException e) {
			e.printStackTrace();
		}		

		for(RedditSubreddit subreddit : subreddits){
			System.out.println(subreddit);
		}
		
		assertEquals(false, subreddits.isEmpty());
		
		targetSubreddit = subreddits.get(0);
	}
	
	@Test(groups = { "readReddit" },
		  dependsOnMethods = { "login", "subredditsPopular" } )
	public void listingsFor(){
        System.out.println(nl);    	
        System.out.println("----------- TESTING LISTING FOR -----------");
        System.out.println(nl);		
		
		List<RedditLink> listing = null;
		String subredditString = targetSubreddit.getUrl().substring(3);
		
		try {
			listing = testReddit.listingFor(subredditString, "top");
		} catch (RedditException e) {
			e.printStackTrace();
		}		

		for(RedditLink link : listing){
			System.out.println(link);
		}		
		
		assertEquals(false, listing.isEmpty());
		
		targetLink = listing.get(0);
	}	

	@Test(groups = { "readReddit" },
		  dependsOnMethods = { "login", "listingsFor" } )
	public void commentsForAndMore(){
        System.out.println(nl);    	
        System.out.println("----------- TESTING COMMENTS FOR AND MORE -----------");
        System.out.println(nl);		
		
		RedditComments comments = null;
		String subredditString = targetSubreddit.getUrl().substring(3);
		
		try {
			comments = testReddit.commentsFor(subredditString, targetLink.getId());
		} catch (RedditException e) {
			e.printStackTrace();
		}		

		System.out.println(comments.toString());
		
		assertEquals(true, comments.getParentLink() != null);		
		assertEquals(false, comments.getComments().isEmpty());
		assertEquals(true, comments.getMore() != null);
		
		if(comments.getMore().getChildren().isEmpty())
			return;
		
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

	@Test(dependsOnGroups = { "readReddit" },
		  dependsOnMethods = { "login", "listingsFor"} )
	public void commentAndDelete(){
        System.out.println(nl);    	
        System.out.println("----------- TESTING COMMENT AND DELETE -----------");
        System.out.println(nl);		
		
		String testComment = "I hope you don't mind my TEST at all! Sorry Reddit, just integration testing... deletion will occur soon enough.";
		String parentThing = targetLink.getName();
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
			Thread.sleep(4000);
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
	
	
	//TODO: Reconcile this beast... the actual API call is more robust.
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
        
	//TODO: Currently HTTP 302's?
	//@Test(dependsOnMethods = { "login" } )
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
