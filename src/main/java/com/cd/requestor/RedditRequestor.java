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

package com.cd.requestor;

import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cd.util.RedditRequestInput;

public class RedditRequestor {

	private static final String REDDIT_BASE = "http://www.reddit.com";
	
	private Client aClientInstance 					= null;
	private RedditRequestInput anInputObject		= null;
	
	public RedditRequestor(RedditRequestInput theInput){
		anInputObject = theInput;
	}

	public Object executeGet(){
		initializeClient();
		
		final WebTarget redditTarget = buildTargetWithInput();
		
		Invocation.Builder invocationBuilder = redditTarget.request(MediaType.APPLICATION_JSON);
		invocationBuilder.header("User-Agent", anInputObject.userAgent);
		
		Response response = invocationBuilder.get();
		
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
		
		return null;
	}
	
	public Object executePost(){
		initializeClient();
		
		final WebTarget redditTarget = buildTargetWithInput();
		final Form formToPost  		 = buildFormWithInput();
		
		Invocation.Builder invocationBuilder = redditTarget.request(MediaType.APPLICATION_JSON_TYPE);
		invocationBuilder.header("User-Agent", anInputObject.userAgent);
		
		Response response = invocationBuilder.post(Entity.entity(formToPost, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
		
		return null;
	}	

	private void initializeClient(){
		if(aClientInstance == null){
			aClientInstance = ClientBuilder.newClient();
		}
	}
	
	private WebTarget buildTargetWithInput(){
		WebTarget redditTarget = aClientInstance.target(REDDIT_BASE);
		
		for(String segment : anInputObject.pathSegments){
			redditTarget = redditTarget.path(segment);
		}
		
		if(anInputObject.queryParams == null)
			return redditTarget;
		
		for(Map.Entry<String, String> entry : anInputObject.queryParams.entrySet()){
			redditTarget = redditTarget.queryParam(entry.getKey(), entry.getValue());
		}
		
		return redditTarget;
	}
	
	private Form buildFormWithInput() {
		Form form = new Form();
		
		if(anInputObject.formParams == null)
			return null;
		
		for(Map.Entry<String, String> entry : anInputObject.formParams.entrySet()){
			form.param(entry.getKey(), entry.getValue());
		}
		
		return form;
	}	
}
