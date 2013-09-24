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


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;

import retrofit.RestAdapter;
import retrofit.client.ApacheClient;

import com.cd.reddit.http.retrofit.converter.RedditJacksonConverter;
import com.cd.reddit.http.retrofit.service.RedditAccountService;
import com.cd.reddit.http.retrofit.service.RedditSubredditsService;
import com.cd.reddit.http.util.RedditApiParameterConstants;
import com.cd.reddit.json.mapping.RedditSubreddit;

public class Reddit {
	private final RestAdapter retrofitRest;
	
	public Reddit(String userAgent){
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreProtocolPNames.USER_AGENT, userAgent);
		retrofitRest = new RestAdapter.Builder()
							.setClient(new ApacheClient(client))
							.setConverter(new RedditJacksonConverter())
							.setServer("http://www.reddit.com")
							.setLogLevel(RestAdapter.LogLevel.FULL)
							.build();
	}
	
	//TODO: This should not return a simple String. Rather, it should return a success/fail message Object.
	public String login(String userName, String password) throws RedditException{
		RedditAccountService service = retrofitRest.create(RedditAccountService.class);
		
		return service.login(RedditApiParameterConstants.JSON, userName, password);
	}

	public List<RedditSubreddit> subredditsNew() throws RedditException{
		RedditSubredditsService service = retrofitRest.create(RedditSubredditsService.class);

		return service.subreddits("new");
	}

	/*	
	public List<RedditSubreddit> subredditsPopular() throws RedditException{
		List<String> pathSegments = new ArrayList<String>();
		
		pathSegments.add(RedditApiResourceConstants.SUBREDDITS);
		pathSegments.add(RedditApiResourceConstants.POPULAR + RedditApiResourceConstants.DOT_JSON);
		
		final RedditRequestInput requestInput 
			= new RedditRequestInput(pathSegments, userAgent);
		
		final RedditRequestResponse response = RedditRequestor.executeGet(requestInput);
		
		RedditJsonParser parser = new RedditJsonParser(response.getBody());
		
		return parser.parseSubreddits();
	}
	
	public List<RedditLink> listingFor(String subreddit, String listingType) throws RedditException{
		List<String> pathSegments = new ArrayList<String>();

		pathSegments.add(RedditApiResourceConstants.R);
		pathSegments.add(subreddit);		
		pathSegments.add(listingType + RedditApiResourceConstants.DOT_JSON);
		
		final RedditRequestInput requestInput 
			= new RedditRequestInput(pathSegments, userAgent);
		
		final RedditRequestResponse response = RedditRequestor.executeGet(requestInput);
		
		RedditJsonParser parser = new RedditJsonParser(response.getBody());
		
		return parser.parseLinks();
	}
	*/	
}
