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
