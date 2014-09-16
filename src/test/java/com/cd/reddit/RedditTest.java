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
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.codehaus.jackson.JsonNode;
import org.testng.Reporter;
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
 * @author Francesc
 * @author Wizang
 *
 * Uses TestNG because JUnit is not designed for state-based tests.
 *
 * @see <a href="http://testng.org/doc/documentation-main.html">Full TestNG Docs</a>
 */
public class RedditTest {

	Reddit testReddit = null;
	
	//For TestNG report output.
	final String nl = "<br/>";
	
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
			logRedditException(e);
		}
	}	

	@Test
	private void login(){
        Reporter.log(nl);    	
        Reporter.log("----------- TESTING LOGIN -----------");
        Reporter.log(nl);		
		
		RedditJsonMessage respMessage = null;
		
		try {
			respMessage = testReddit.login("JavaJerseyTestBot", "JavaJerseyTestBot");
		} catch (RedditException e) {
			logRedditException(e);
		} 
		
		Reporter.log(respMessage.toString());
	}

    @Test
    private void newCaptcha(){
        Reporter.log(nl);
        Reporter.log("----------- TESTING NEW CAPTCHA -----------");
        Reporter.log(nl);

        RedditJsonMessage respMessage = null;

        try {
            respMessage = testReddit.newCaptcha();
        } catch (RedditException e) {
            logRedditException(e);
        }

        Reporter.log(respMessage.toString());
        assertEquals(true, respMessage.getIden() != null);
    }

	@Test(dependsOnGroups = { "readReddit" },
		  dependsOnMethods = { "login" } )	
	public void testMeJson(){
        Reporter.log(nl);    	
        Reporter.log("----------- TESTING ME.JSON -----------");
        Reporter.log(nl);		
		
		RedditAccount account = null;
		
		try {
			account = testReddit.meJson();
		} catch (RedditException e) {
			logRedditException(e);
			return;
		} 
		
		Reporter.log(account.toString());
		assertEquals(true, account.getModhash() != null);		
	}

    @Test(dependsOnGroups = { "readReddit" },
            dependsOnMethods = { "login" } )
    public void testUserInfoFor(){
        Reporter.log(nl);
        Reporter.log("----------- TESTING /api/{username}/about.json -----------");
        Reporter.log(nl);

        RedditAccount account = null;

        try {
            account = testReddit.userInfoFor("JavaJerseyTestBot");
        } catch (RedditException e) {
            logRedditException(e);
        }

        Reporter.log(account.toString());
        assertNotNull(account);
    }

    // After logging in, we will 'read reddit' and not hard-code things to harass.

	@Test(groups = { "readReddit" },
		  dependsOnMethods = { "login" } )
	public void subredditsPopular(){
        Reporter.log(nl);    	
        Reporter.log("----------- TESTING SUBREDDITS NEW -----------");
        Reporter.log(nl);		
		
		List<RedditSubreddit> subreddits = null;
		
		try {
			subreddits = testReddit.subreddits("popular");
		} catch (RedditException e) {
			logRedditException(e);
			return;
		}		

		for(RedditSubreddit subreddit : subreddits){
			Reporter.log(subreddit.toString());
		}
		
		assertEquals(false, subreddits.isEmpty());
		
		targetSubreddit = subreddits.get(0);
	}
	
	@Test(groups = { "readReddit" },
		  dependsOnMethods = { "login", "subredditsPopular" } )
	public void listingsFor(){
        Reporter.log(nl);    	
        Reporter.log("----------- TESTING LISTING FOR -----------");
        Reporter.log(nl);		
		
		List<RedditLink> listing = null;
		String subredditString = targetSubreddit.getSubredditName();
		
		try {
			listing = testReddit.listingFor(subredditString, "top");
		} catch (RedditException e) {
			logRedditException(e);
			return;
		}		

		for(RedditLink link : listing){
			Reporter.log(link.toString());
		}		
		
		assertEquals(false, listing.isEmpty());
		
		targetLink = listing.get(0);
	}	

	@Test(groups = { "readReddit" },
		  dependsOnMethods = { "login", "listingsFor" } )
	public void commentsForAndMore(){
        Reporter.log(nl);    	
        Reporter.log("----------- TESTING COMMENTS FOR AND MORE -----------");
        Reporter.log(nl);		
		
		RedditComments comments = null;
		String subredditString = targetSubreddit.getSubredditName();
		
		try {
			comments = testReddit.commentsFor(subredditString, targetLink.getId());
		} catch (RedditException e) {
			logRedditException(e);
			return;
		}		

		Reporter.log(comments.toString());
		
		assertEquals(true, comments.getParentLink() != null);		
		assertEquals(false, comments.getComments().isEmpty());
		assertEquals(true, comments.getMore() != null);
		
		if(comments.getMore().getChildren().isEmpty())
			return;
		
		List<RedditComment> moreComments = null;
		
		try {
			moreComments = testReddit.moreChildrenFor(comments, "top");
		} catch (RedditException e) {
			logRedditException(e);
		}
		
		Reporter.log(moreComments.toString());		

		assertEquals(false, moreComments.isEmpty());		
		
		//Also test the 'more' and show an intuitive way of using..
		//final String childComment = comments.get(1).getId();
	}

    @Test(dependsOnGroups = { "readReddit" },
            dependsOnMethods = { "login", "listingsFor"} )
    public void vote() {
        Reporter.log(nl);
        Reporter.log("----------- TESTING VOTING AND UNVOTING -----------");
        Reporter.log(nl);

        String parentThing = targetLink.getName();

        try {
            testReddit.vote(1, parentThing);
        } catch (RedditException e) {
            logRedditException(e);
        }

        try {
            testReddit.vote(0, parentThing);
        } catch (RedditException e) {
            logRedditException(e);
        }

    }

    @Test(dependsOnGroups = { "readReddit" },
		  dependsOnMethods = { "login", "listingsFor"} )
	public void commentAndDelete(){
        Reporter.log(nl);    	
        Reporter.log("----------- TESTING COMMENT AND DELETE -----------");
        Reporter.log(nl);		
		
		String testComment = "I hope you don't mind my TEST at all! Sorry Reddit, just integration testing... deletion will occur soon enough.";
		String parentThing = targetLink.getName();
		RedditJsonMessage message = null;
		
		try {
			message = testReddit.comment(testComment, parentThing);
		} catch (RedditException e) {
			logRedditException(e);
			return;
		}		

		Reporter.log(message.toString());
        Reporter.log(nl);
        
		assertEquals(true, message.getErrors().isEmpty());
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			logRedditException(e);
		}		
		
		final JsonNode thingsNode = message.getData().get("things");
		
		//Annoying that we have to keep instantiating new Parsers...
		RedditJsonParser parser 			= new RedditJsonParser(thingsNode);
		List<RedditComment> theOneComment 	= null;
		
		try {
			theOneComment = parser.parseCommentsOnly();
		} catch (RedditException e) {
			logRedditException(e);
		}
        
        //The first and only Comment type Thing should be the one we just made.
        RedditComment theComment = theOneComment.get(0);

        Reporter.log(theComment.toString());        
        Reporter.log(nl);        
        
		try {
			//It seems that the /api/delete method returns HTTP 200 ok and an empty JSON object response... don't try to parse it (yet)
			testReddit.delete(theComment.getId());
		} catch (RedditException e) {
			logRedditException(e);
		}
		
	}

    @Test( dependsOnMethods = "login" )
	public void testMarkNSFW() {
        Reporter.log(nl);
        Reporter.log("----------- TESTING Mark Link NSFW -----------");
        Reporter.log(nl);

        String linkId = "t3_1roati"; //TEST POST BY JAVAJERSEYBOT


        int response = 0;
        try {
            response = testReddit.markNSFW(linkId);
        } catch (RedditException e) {
            logRedditException(e);
        }

        assertEquals(200, response); //Could use better assertion
    }

    @Test( dependsOnMethods = {"login", "testMarkNSFW"} )
    public void testUnmarkNSFW() {
        Reporter.log(nl);
        Reporter.log("----------- TESTING Unmark Link NSFW -----------");
        Reporter.log(nl);

        String linkId = "t3_1roati";

        int response = 0;
        try {
            response = testReddit.unmarkNSFW(linkId);
        } catch (RedditException e) {
            logRedditException(e);
        }

        assertEquals(200, response); //Could use better assertion
    }
	
	//TODO: Reconcile this beast... the actual API call is more robust.
	public void testInfoFor(){
        Reporter.log(nl);    	
        Reporter.log("----------- TESTING INFO FOR -----------");
        Reporter.log(nl);		
		
		List<RedditLink> listing = null;
		
		try {
			listing = testReddit.infoForId("t3_1n6uck");
		} catch (RedditException e) {
			logRedditException(e);
		}		

		for(RedditLink link : listing){
			Reporter.log(link.toString());
		}		
		
		assertEquals(false, listing.isEmpty());
	}	
        
	//TODO: Currently HTTP 302's?
	//@Test(dependsOnMethods = { "login" } )
    public void testInbox() {
        Reporter.log(nl);    	
        Reporter.log("----------- TESTING INBOX -----------");
        Reporter.log(nl);
        
        List<RedditMessage> messages = null;
        try {
            messages = testReddit.messages(RedditApiResourceConstants.INBOX);
        } catch (RedditException e) {
            logRedditException(e);
        }
        
        for (RedditMessage message : messages) {
            Reporter.log(message.toString());
        }
        
        assertEquals(false, messages.isEmpty());
    }
    
    private void logRedditException(final Exception e){
    	final String [] stackMessages = ExceptionUtils.getRootCauseStackTrace(e);
    	
    	if(stackMessages.length == 0){
    		return;
    	}
    	
    	for(String message : stackMessages){
    		Reporter.log(message);
    	}
    }
}
