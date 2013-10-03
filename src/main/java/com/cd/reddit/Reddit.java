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

import com.cd.reddit.http.RedditRequestor;
import com.cd.reddit.http.util.RedditApiParameterConstants;
import com.cd.reddit.http.util.RedditApiResourceConstants;
import com.cd.reddit.http.util.RedditRequestInput;
import com.cd.reddit.http.util.RedditRequestResponse;
import com.cd.reddit.json.jackson.RedditJsonParser;
import com.cd.reddit.json.mapping.RedditJsonMessage;
import com.cd.reddit.json.mapping.RedditLink;
import com.cd.reddit.json.mapping.RedditSubreddit;
import com.cd.reddit.json.util.RedditComments;

public class Reddit {
	private final RedditRequestor requestor;
	
	public Reddit(String userAgent){
		requestor = new RedditRequestor(userAgent);
	}
	
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
			//TODO: Should also provide a string representation of the RedditJsonMessage
			throw new RedditException("Got errors while logging in!");
		}
		
		return message;
	}

	public List<RedditSubreddit> subredditsNew() throws RedditException{
		final List<String> path = new ArrayList<String>(2);
		
		path.add(RedditApiResourceConstants.SUBREDDITS);
		path.add(RedditApiResourceConstants.NEW + RedditApiResourceConstants.DOT_JSON);
		
		final RedditRequestInput requestInput = new RedditRequestInput(path);
		final RedditRequestResponse response = requestor.executeGet(requestInput);
		
		RedditJsonParser parser = new RedditJsonParser(response.getBody());
		return parser.parseSubreddits();
	}

	public List<RedditSubreddit> subredditsPopular() throws RedditException{
		List<String> pathSegments = new ArrayList<String>(2);
		
		pathSegments.add(RedditApiResourceConstants.SUBREDDITS);
		pathSegments.add(RedditApiResourceConstants.POPULAR + RedditApiResourceConstants.DOT_JSON);
		
		final RedditRequestInput requestInput 
			= new RedditRequestInput(pathSegments);
		
		final RedditRequestResponse response = requestor.executeGet(requestInput);
		
		final RedditJsonParser parser = new RedditJsonParser(response.getBody());
		
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

	public RedditComments moreChildrenFor(final RedditComments theComments) throws RedditException{
		return null;
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
}
