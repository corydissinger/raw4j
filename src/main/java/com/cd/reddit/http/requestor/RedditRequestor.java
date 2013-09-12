/*
This file is part of reddit-jersey-client.

reddit-jersey-client is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

reddit-jersey-client is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with reddit-jersey-client.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.cd.reddit.http.requestor;

import java.util.Map;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cd.reddit.http.util.RedditRequestInput;
import com.cd.reddit.http.util.RedditRequestResponse;

public class RedditRequestor {

	private static final String REDDIT_BASE = "http://www.reddit.com";

	public static RedditRequestResponse executeGet(RedditRequestInput theInput){
		final WebTarget redditTarget = buildTargetWithInput(theInput);
		
		Invocation.Builder invocationBuilder = redditTarget.request(MediaType.APPLICATION_JSON_TYPE);
		invocationBuilder.header("User-Agent", theInput.getUserAgent());
		
		Response response = invocationBuilder.get();
		
		return new RedditRequestResponse(response.getStatus(), response.readEntity(String.class));
	}
	
	public static RedditRequestResponse executePost(RedditRequestInput theInput){
		final WebTarget redditTarget = buildTargetWithInput(theInput);
		final Form formToPost  		 = buildFormWithInput(theInput);
		
		Invocation.Builder invocationBuilder = redditTarget.request(MediaType.APPLICATION_JSON_TYPE);
		invocationBuilder.header("User-Agent", theInput.getUserAgent());
		
		Response response = invocationBuilder.post(Entity.entity(formToPost, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		
		return new RedditRequestResponse(response.getStatus(), response.readEntity(String.class));
	}	

	private static WebTarget buildTargetWithInput(RedditRequestInput theInput){
		WebTarget redditTarget = ClientBuilder.newClient().target(REDDIT_BASE);
		
		for(String segment : theInput.getPathSegments()){
			redditTarget = redditTarget.path(segment);
		}
		
		if(theInput.getQueryParams() == null)
			return redditTarget;
		
		for(Map.Entry<String, String> entry : theInput.getQueryParams().entrySet()){
			redditTarget = redditTarget.queryParam(entry.getKey(), entry.getValue());
		}
		
		return redditTarget;
	}
	
	private static Form buildFormWithInput(RedditRequestInput theInput) {
		if(theInput.getFormParams() == null)
			return null;
		
		Form form = new Form();
		
		for(Map.Entry<String, String> entry : theInput.getFormParams().entrySet()){
			form.param(entry.getKey(), entry.getValue());
		}
		
		return form;
	}	
}
