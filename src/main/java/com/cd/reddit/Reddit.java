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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.cd.reddit.http.RedditRequestor;
import com.cd.reddit.http.util.RedditApiParameterConstants;
import com.cd.reddit.http.util.RedditApiResourceConstants;
import com.cd.reddit.http.util.RedditRequestInput;
import com.cd.reddit.http.util.RedditRequestResponse;
import com.cd.reddit.json.jackson.RedditJsonParser;
import com.cd.reddit.json.mapping.RedditAccount;
import com.cd.reddit.json.mapping.RedditComment;
import com.cd.reddit.json.mapping.RedditJsonMessage;
import com.cd.reddit.json.mapping.RedditLink;
import com.cd.reddit.json.mapping.RedditMessage;
import com.cd.reddit.json.mapping.RedditSubreddit;
import com.cd.reddit.json.util.RedditComments;

/**
 * Highest level API wrapper class. Primary goal should be to provide most intuitive, powerful methods to easily get de-serialized JSON
 * from the Reddit API.
 * <br/>
 * <br/>
 *  Please see the <a href="https://github.com/corydissinger/raw4j/blob/master/IMPLEMENTED_CALLS.md">Github Markdown</a> for full code examples.
 * <br/> 
 * <br/>
 * 
 * @see <a href="http://www.reddit.com/dev/api">Reddit's Full, Live Built-In Documentation</a>
 * 
 * @author <a href="https://github.com/corydissinger">Cory Dissinger</a>
 * @author <a href="https://github.com/ifrins">Francesc Bruguera</a>
 * @author <a href="https://github.com/wizang">Lucas Hill</a>
 *
 */
public class Reddit {
	private final RedditRequestor requestor;
	
	/**
	 * @param userAgent The user agent to set
	 */
	public Reddit(String userAgent){
        requestor = new RedditRequestor(userAgent);		
	}
        
    /**
     * 
     * @param userAgent The user agent to set
     * @param session The reddit session id to pass to the <code>RedditRequestor</code>
     */
    public Reddit(String userAgent, String session) {
        requestor = new RedditRequestor(userAgent);
        requestor.setSession(session);
    }
        
    /**
     * Once this method is called the cookie returned is set as the session and will be added to all future calls
     * 
     * @param userName Username of the reddit account
     * @param password Password of the reddit acount
     * @return A <code>RedditJsonMessage</code>
     * @throws RedditException 
     */
	public RedditJsonMessage login(final String userName, final String password) throws RedditException{
		final List<String> path = new ArrayList<String>(2);
		final Map<String, String> form = new HashMap<String, String>(2);
		
		path.add(RedditApiResourceConstants.API);
		path.add(RedditApiResourceConstants.LOGIN);

		form.put(RedditApiParameterConstants.API_TYPE, RedditApiParameterConstants.JSON);		
		form.put(RedditApiParameterConstants.USER, userName);
		form.put(RedditApiParameterConstants.PASSWD, password);
		
		final RedditRequestInput requestInput = new RedditRequestInput(path, null, form);
		final RedditRequestResponse response = requestor.executePost(requestInput);
		
		final RedditJsonParser parser = new RedditJsonParser(response.getBody());
		final RedditJsonMessage message = parser.parseJsonMessage();
		
		if(!message.getErrors().isEmpty()){
			throw new RedditException("Got errors while logging in: " + message.toString());
		}
		
        requestor.setSession(message.getCookie());
        requestor.setModhashHeader(message.getModhash());
        
		return message;
	}

    public RedditJsonMessage newCaptcha() throws RedditException {
        final List<String> path = new ArrayList<String>(2);
        final Map<String, String> form = new HashMap<String, String>(1);

        path.add(RedditApiResourceConstants.API);
        path.add(RedditApiResourceConstants.NEW_CAPTCHA);

        form.put(RedditApiParameterConstants.API_TYPE, RedditApiParameterConstants.JSON);

        final RedditRequestInput requestInput = new RedditRequestInput(path, null, form);
        final RedditRequestResponse response = requestor.executePost(requestInput);

        final RedditJsonParser parser = new RedditJsonParser(response.getBody());
        final RedditJsonMessage message = parser.parseJsonMessage();

        if (!message.getErrors().isEmpty()){
            throw new RedditException("Got errors while requesting captcha: " + message.toString());
        }

        return message;
    }

	
	public RedditAccount meJson() throws RedditException{
		final List<String> path = new ArrayList<String>(2);
		
		path.add(RedditApiResourceConstants.API);
		path.add(RedditApiResourceConstants.ME_JSON);		
		
		final RedditRequestInput requestInput = new RedditRequestInput(path);
		final RedditRequestResponse response = requestor.executeGet(requestInput);
		
		final RedditJsonParser parser = new RedditJsonParser(response.getBody());
		final RedditAccount account = parser.parseAccounts().get(0);
		
        requestor.setModhashHeader(account.getModhash());
		
		return account;
	}
	
	//See Reddit's built-in documentation for more detailed info: http://www.reddit.com/dev/api#GET_subreddits_{where}
	public List<RedditSubreddit> subreddits(String byGrouping) throws RedditException{
		final List<String> path = new ArrayList<String>(2);
		
		path.add(RedditApiResourceConstants.SUBREDDITS);
		path.add(byGrouping + RedditApiResourceConstants.DOT_JSON);
		
		final RedditRequestInput requestInput = new RedditRequestInput(path);
		final RedditRequestResponse response = requestor.executeGet(requestInput);
		
		RedditJsonParser parser = new RedditJsonParser(response.getBody());
		return parser.parseSubreddits();
	}
	
	public List<RedditLink> listingFor(final String subreddit, final String listingType) throws RedditException{
		final List<String> pathSegments = new ArrayList<String>(3);

		pathSegments.add(RedditApiResourceConstants.R);
		pathSegments.add(subreddit);		
		pathSegments.add(listingType + RedditApiResourceConstants.DOT_JSON);
		
		final RedditRequestInput requestInput 
			= new RedditRequestInput(pathSegments);
		
		final RedditRequestResponse response = requestor.executeGet(requestInput);
		
		final RedditJsonParser parser = new RedditJsonParser(response.getBody());
		
		return parser.parseLinks();
	}

	//TODO: The can of worms begins here. Check example-morechildren.json to see what I mean.
	public List<RedditComment> moreChildrenFor(RedditComments theComments, String desiredSort) throws RedditException{
		final List<String> pathSegments = new ArrayList<String>(2);
		final Map<String, String> form = new HashMap<String, String>(2);
		
		pathSegments.add(RedditApiResourceConstants.API);
		pathSegments.add(RedditApiResourceConstants.MORECHILDREN);

		final List<String> childrenList = theComments.getMore().getChildren();
		final String linkId 			= theComments.getParentLink().getName();
		
		form.put(RedditApiParameterConstants.API_TYPE, RedditApiParameterConstants.JSON);		
		form.put(RedditApiParameterConstants.CHILDREN, StringUtils.join(childrenList.iterator(), ","));
		form.put(RedditApiParameterConstants.LINK_ID, linkId);

		final RedditRequestInput requestInput = new RedditRequestInput(pathSegments, null, form);
		
		final RedditRequestResponse response = requestor.executePost(requestInput);
		
		final RedditJsonParser parser = new RedditJsonParser(response.getBody());
		System.out.println();
		
		return parser.parseMoreChildren();
	}	

	public RedditComments commentsFor(final String subreddit, final String linkId) throws RedditException{
		final List<String> pathSegments 		= new ArrayList<String>(2);
		
		pathSegments.add(RedditApiResourceConstants.R);
		pathSegments.add(subreddit);
		pathSegments.add(RedditApiResourceConstants.COMMENTS);
		pathSegments.add(linkId + RedditApiResourceConstants.DOT_JSON);		
		
		final RedditRequestInput requestInput 
			= new RedditRequestInput(pathSegments);
		
		final RedditRequestResponse response = requestor.executeGet(requestInput);
		
		final RedditJsonParser parser = new RedditJsonParser(response.getBody());
		
		return parser.parseComments();
	}	
	
	//TODO: Is this always a Link?
	public List<RedditLink> infoForId(final String id) throws RedditException{
		final List<String> pathSegments = new ArrayList<String>(2);
		final Map<String, String> queryParams = new HashMap<String, String>(1);
		
		pathSegments.add(RedditApiResourceConstants.API);
		pathSegments.add(RedditApiResourceConstants.INFO + RedditApiResourceConstants.DOT_JSON);		
		
		queryParams.put(RedditApiParameterConstants.ID, id);
		
		final RedditRequestInput requestInput 
			= new RedditRequestInput(pathSegments, queryParams);
		
		final RedditRequestResponse response = requestor.executeGet(requestInput);
		
		final RedditJsonParser parser = new RedditJsonParser(response.getBody());
		
		return parser.parseLinks();
	}
	
	public RedditJsonMessage comment(String rawMarkdown, String parentId) throws RedditException{
		final List<String> pathSegments = new ArrayList<String>(2);
		final Map<String, String> form = new HashMap<String, String>(3);
		
		pathSegments.add(RedditApiResourceConstants.API);
		pathSegments.add(RedditApiResourceConstants.COMMENT);

		form.put(RedditApiParameterConstants.API_TYPE, RedditApiParameterConstants.JSON);		
		form.put(RedditApiParameterConstants.TEXT, rawMarkdown);
		form.put(RedditApiParameterConstants.THING_ID, parentId);

		final RedditRequestInput requestInput = new RedditRequestInput(pathSegments, null, form);
		
		final RedditRequestResponse response = requestor.executePost(requestInput);
		
		final RedditJsonParser parser = new RedditJsonParser(response.getBody());
		
		return parser.parseJsonMessage();
	}

	public void delete(String fullname) throws RedditException{
		final List<String> pathSegments = new ArrayList<String>(2);
		final Map<String, String> form = new HashMap<String, String>(2);
		
		pathSegments.add(RedditApiResourceConstants.API);
		pathSegments.add(RedditApiResourceConstants.DEL);

		form.put(RedditApiParameterConstants.API_TYPE, RedditApiParameterConstants.JSON);		
		form.put(RedditApiParameterConstants.ID, fullname);

		final RedditRequestInput requestInput = new RedditRequestInput(pathSegments, null, form);
		
		final RedditRequestResponse response = requestor.executePost(requestInput);
	}
	
	
    public List<RedditMessage> messages(String inboxType) throws RedditException {
        final List<String> pathSegments = new ArrayList<String>(2);
        
        pathSegments.add(RedditApiResourceConstants.MESSAGE);
        pathSegments.add(inboxType + RedditApiResourceConstants.DOT_JSON);
        
        final RedditRequestInput requestInput
                = new RedditRequestInput(pathSegments);
        
        final RedditRequestResponse response = requestor.executeGet(requestInput);
        final RedditJsonParser parser = new RedditJsonParser(response.getBody());
        
        return parser.parseMessages();
    }

    public void vote(int voteDirection, String fullname) throws RedditException{
        final List<String> pathSegments = new ArrayList<String>(2);
        final Map<String, String> form = new HashMap<String, String>(2);

        pathSegments.add(RedditApiResourceConstants.API);
        pathSegments.add(RedditApiResourceConstants.VOTE);

        form.put(RedditApiParameterConstants.VOTE_DIRECTION, Integer.toString(voteDirection));
        form.put(RedditApiParameterConstants.ID, fullname);

        final RedditRequestInput requestInput = new RedditRequestInput(pathSegments, null, form);
        final RedditRequestResponse response = requestor.executePost(requestInput);

        final RedditJsonParser parser = new RedditJsonParser(response.getBody());
        final RedditJsonMessage message = parser.parseJsonMessage();

        if(!message.getErrors().isEmpty()){
            throw new RedditException("Got errors while voting: " + message.toString());
        }
    }

    public int markNSFW(String linkId) throws RedditException {
        final List<String> pathSegments = new ArrayList<String>(2);

        pathSegments.add(RedditApiResourceConstants.API);
        pathSegments.add(RedditApiResourceConstants.MARKNSFW);

        final Map<String, String> formParams = new HashMap<String, String>(1);

        formParams.put(RedditApiParameterConstants.ID, linkId);

        final RedditRequestInput requestInput = new RedditRequestInput(pathSegments, null, formParams);

        final RedditRequestResponse response = requestor.executePost(requestInput);

        return response.getStatus();
    }

    public int unmarkNSFW(String linkId) throws RedditException {
        final List<String> pathSegments = new ArrayList<String>(2);

        pathSegments.add(RedditApiResourceConstants.API);
        pathSegments.add(RedditApiResourceConstants.UNMARKNSFW);

        final Map<String, String> formParams = new HashMap<String, String>(1);

        formParams.put(RedditApiParameterConstants.ID, linkId);

        final RedditRequestInput requestInput = new RedditRequestInput(pathSegments, null, formParams);

        final RedditRequestResponse response = requestor.executePost(requestInput);

        return response.getStatus();
    }

    public RedditAccount userInfoFor(String username) throws RedditException{
        final List<String> pathSegments = new ArrayList<String>(3);

        pathSegments.add(RedditApiResourceConstants.USER);
        pathSegments.add(username);
        pathSegments.add(RedditApiResourceConstants.ABOUT);

        final RedditRequestInput requestInput = new RedditRequestInput(pathSegments);
        final RedditRequestResponse response = requestor.executeGet(requestInput);

        final RedditJsonParser parser = new RedditJsonParser(response.getBody());
        final List<RedditAccount> accounts = parser.parseAccounts();

        if (accounts.size() == 1) {
            return accounts.get(0);
        } else {
            throw new RedditException("Username " + username + " does not exist.");
        }
    }
}
