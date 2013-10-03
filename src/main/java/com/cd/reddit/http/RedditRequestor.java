/*
Copyright 2013 Cory Dissinger

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at 

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
*/

package com.cd.reddit.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cd.reddit.RedditException;
import com.cd.reddit.http.util.RedditRequestInput;
import com.cd.reddit.http.util.RedditRequestResponse;
import org.apache.commons.io.IOUtils;

public class RedditRequestor {
	
	private static final String HOST = "www.reddit.com/";
	
	private final String userAgent;
	
	public RedditRequestor(String userAgent){
		this.userAgent = userAgent;
	}

    private HttpURLConnection getConnection(RedditRequestInput input) throws RedditException{
        try{

            HttpURLConnection connection = (HttpURLConnection) generateURI(input.getPathSegments(), input.getQueryParams())
                    .toURL().openConnection();

            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("User-Agent", userAgent);

            return connection;
        } catch (Exception e) {
        throw new RedditException(e);
        }
    }
	
	public RedditRequestResponse executeGet(RedditRequestInput input) throws RedditException{
        HttpURLConnection connection = getConnection(input);

        InputStream stream = null;

        try {
            // Connection will be made here
            stream = connection.getInputStream();

            // Buffers internally
            String responseBody = IOUtils.toString(stream, "UTF-8");
            int statusCode = connection.getResponseCode();

            if(statusCode != 200){
                throw new RedditException(generateErrorString(statusCode, input, responseBody));
            }

            return new RedditRequestResponse(statusCode, responseBody);

        } catch (IOException e1) {
            throw new RedditException(e1);
        } finally {
            // Internally checks for null
            IOUtils.closeQuietly(stream);
        }
	}


	
	public RedditRequestResponse executePost(final RedditRequestInput input) throws RedditException{
		final RedditRequestResponse response;

        HttpURLConnection connection

		try {

			connection = (HttpURLConnection) generateURI(input.getPathSegments(), input.getQueryParams()).toURL().openConnection();
		
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
					throw new RedditException(generateErrorString(statusCode, input, responseBody));
				}
				
				response = new RedditRequestResponse(statusCode, responseBody);
			}finally{
				closeableResp.close();
			}
			
		} catch (Exception e) {
			throw new RedditException(e);
		}finally{
			try {
				httpclient.close();
			} catch (IOException e) {
				throw new RedditException(e);
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
