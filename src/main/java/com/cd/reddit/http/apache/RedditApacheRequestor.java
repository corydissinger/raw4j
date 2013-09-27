/*
Copyright 2013 Cory Dissinger

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at 

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
*/

package com.cd.reddit.http.apache;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.cd.reddit.RedditException;
import com.cd.reddit.http.util.RedditRequestInput;
import com.cd.reddit.http.util.RedditRequestResponse;

public class RedditApacheRequestor {
	
	private static final String HOST = "www.reddit.com/";
	
	private final String userAgent;
	
	public RedditApacheRequestor(String userAgent){
		this.userAgent = userAgent;
	}
	
	public RedditRequestResponse executeGet(final RedditRequestInput input) throws RedditException{
		final CloseableHttpClient httpclient = HttpClientBuilder.create().setUserAgent(userAgent).build();
		final RedditRequestResponse response;
		
		try {
			final URI uri;

			uri = generateURI(input.getPathSegments(), 
							  input.getQueryParams());
		
			final HttpGet httpGet = new HttpGet(uri);
			httpGet.addHeader("accept", "application/json");		
			
			final CloseableHttpResponse closeableResp;
			
			closeableResp = httpclient.execute(httpGet);
			
			try{
				final int statusCode = closeableResp.getStatusLine().getStatusCode();
				final HttpEntity httpEnt = closeableResp.getEntity();
				final String responseBody = EntityUtils.toString(httpEnt);
				
				if(statusCode != 200){
					throw new RedditException(generateErrorString(statusCode, input, responseBody), null);
				}
				
				response = new RedditRequestResponse(statusCode, responseBody);
			}finally{
				closeableResp.close();
			}
			
		} catch (Exception e) {
			throw new RedditException(e.getMessage(), e.getCause());
		}finally{
			try {
				httpclient.close();
			} catch (IOException e) {
				throw new RedditException(e.getMessage(), e.getCause());
			}
		}
		
		return response;
	}
	
	public RedditRequestResponse executePost(final RedditRequestInput input) throws RedditException{
		final CloseableHttpClient httpclient = HttpClientBuilder.create().setUserAgent(userAgent).build();
		final RedditRequestResponse response;
		
		try {
			final URI uri;

			uri = generateURI(input.getPathSegments(), 
							  input.getQueryParams());
		
			final HttpPost httpPost = new HttpPost(uri);
			httpPost.addHeader("accept", "application/json");		
			httpPost.setEntity(generateUrlEncodedForm(input.getFormParams()));
			
			final CloseableHttpResponse closeableResp;
			
			closeableResp = httpclient.execute(httpPost);
			
			try{
				final int statusCode = closeableResp.getStatusLine().getStatusCode();
				final HttpEntity httpEnt = closeableResp.getEntity();
				final String responseBody = EntityUtils.toString(httpEnt);
				
				if(statusCode != 200){
					throw new RedditException(generateErrorString(statusCode, input, responseBody), null);
				}
				
				response = new RedditRequestResponse(statusCode, responseBody);
			}finally{
				closeableResp.close();
			}
			
		} catch (Exception e) {
			throw new RedditException(e.getMessage(), e.getCause());
		}finally{
			try {
				httpclient.close();
			} catch (IOException e) {
				throw new RedditException(e.getMessage(), e.getCause());
			}
		}
		
		return response;
	}
	
	private UrlEncodedFormEntity generateUrlEncodedForm(Map<String, String> formParams){
		final List<NameValuePair> nvps = new ArrayList<NameValuePair>(formParams.size());
		
		for(Map.Entry<String, String> entry : formParams.entrySet()){
			nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		
		return new UrlEncodedFormEntity(nvps, Consts.UTF_8);
	}
	
	private URI generateURI(final List<String> pathSegments, 
								 final Map<String, String> queryParams) throws URISyntaxException{
		URIBuilder builder = new URIBuilder();

		builder.setScheme("http");		
		builder.setHost(HOST);		
		
		if(pathSegments != null){
			StringBuilder pathBuilder = new StringBuilder();
			Iterator<String> itr = pathSegments.iterator();
			
			while(itr.hasNext()){
				pathBuilder.append(itr.next());
				
				if(itr.hasNext())
					pathBuilder.append("/");				
			}
			
			builder.setPath(pathBuilder.toString());
		}
		
		if(queryParams != null){
			for(Map.Entry<String, String> entry : queryParams.entrySet()){
				builder.addParameter(entry.getKey(), entry.getValue());
			}
		}
		
		return builder.build();
	}
	
	private String generateErrorString(final int statusCode, 
									   final RedditRequestInput input, 
									   final String responseBody){
		String nl = System.getProperty("line.separator");
		StringBuilder builder = new StringBuilder();
		
		builder.append("--- STATUS CODE ---");
		builder.append(nl);		
		builder.append(statusCode);
		builder.append(nl);
		builder.append("--- REQUEST INPUT---");
		builder.append(nl);		
		builder.append(input.toString());
		builder.append(nl);
		builder.append("--- RESPONSE BODY---");
		builder.append(nl);		
		builder.append(responseBody);
		builder.append(nl);
		
		return builder.toString();
	}
}
