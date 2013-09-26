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

import com.cd.reddit.http.apache.RedditApacheRequestor;
import com.cd.reddit.http.util.RedditApiParameterConstants;
import com.cd.reddit.http.util.RedditApiResourceConstants;
import com.cd.reddit.http.util.RedditRequestInput;
import com.cd.reddit.http.util.RedditRequestResponse;
import com.cd.reddit.json.jackson.RedditJsonParser;
import com.cd.reddit.json.mapping.RedditLink;
import com.cd.reddit.json.mapping.RedditSubreddit;

public class Reddit {
	private final RedditApacheRequestor requestor;
	
	public Reddit(String userAgent){
		requestor = new RedditApacheRequestor(userAgent);
	}
	
	public void login(final String userName, final String password) throws RedditException{
		final List<String> path = new ArrayList<String>();
		final Map<String, String> form = new HashMap<String, String>();
		
		path.add(RedditApiResourceConstants.API);
		path.add(RedditApiResourceConstants.LOGIN);
		
		form.put(RedditApiParameterConstants.USER, userName);
		form.put(RedditApiParameterConstants.PASSWD, password);
		
		final RedditRequestInput requestInput = new RedditRequestInput(path, null, form);
		final RedditRequestResponse response = requestor.executePost(requestInput);
	}

	public List<RedditSubreddit> subredditsNew() throws RedditException{
		final List<String> path = new ArrayList<String>();
		
		path.add(RedditApiResourceConstants.SUBREDDITS);
		path.add(RedditApiResourceConstants.NEW + RedditApiResourceConstants.DOT_JSON);
		
		final RedditRequestInput requestInput = new RedditRequestInput(path);
		final RedditRequestResponse response = requestor.executeGet(requestInput);
		
		RedditJsonParser parser = new RedditJsonParser(response.getBody());
		return parser.parseSubreddits();
	}

	public List<RedditSubreddit> subredditsPopular() throws RedditException{
		List<String> pathSegments = new ArrayList<String>();
		
		pathSegments.add(RedditApiResourceConstants.SUBREDDITS);
		pathSegments.add(RedditApiResourceConstants.POPULAR + RedditApiResourceConstants.DOT_JSON);
		
		final RedditRequestInput requestInput 
			= new RedditRequestInput(pathSegments);
		
		final RedditRequestResponse response = requestor.executeGet(requestInput);
		
		final RedditJsonParser parser = new RedditJsonParser(response.getBody());
		
		return parser.parseSubreddits();
	}
	
	public List<RedditLink> listingFor(final String subreddit, final String listingType) throws RedditException{
		final List<String> pathSegments = new ArrayList<String>();

		pathSegments.add(RedditApiResourceConstants.R);
		pathSegments.add(subreddit);		
		pathSegments.add(listingType + RedditApiResourceConstants.DOT_JSON);
		
		final RedditRequestInput requestInput 
			= new RedditRequestInput(pathSegments);
		
		final RedditRequestResponse response = requestor.executeGet(requestInput);
		
		final RedditJsonParser parser = new RedditJsonParser(response.getBody());
		
		return parser.parseLinks();
	}
	
}
