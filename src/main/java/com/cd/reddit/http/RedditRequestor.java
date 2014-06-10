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
import java.io.OutputStream;
import java.net.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cd.reddit.RedditException;
import com.cd.reddit.http.util.RedditRequestInput;
import com.cd.reddit.http.util.RedditRequestResponse;

import org.apache.commons.io.IOUtils;

/**
 * Necessary implementation of HttpURLConnection and friends.
 * Apache HTTP Client, while easier to use, is avoided to retain Android compatibility.
 * <br/> 
 * <br/>
 * Needs to have SSL/HTTPS support as well as further analysis of other needs.
 * 
 * @author <a href="https://github.com/corydissinger">Cory Dissinger</a>
 * @author <a href="https://github.com/cybergeek94">Austin Bonander</a>
 */
public class RedditRequestor {
	//TODO: Add ability to connect to ssl.reddit.com because credentials should not be in plain-text!
	private static final String HOST = "www.reddit.com";
	
	private final String userAgent;
	
	private String modhash = null;
    private String session = null;

    public RedditRequestor(String userAgent) {
        this.userAgent = userAgent;
    }
    
    private HttpURLConnection getConnection(RedditRequestInput input) throws RedditException {
        try {
            HttpURLConnection connection = (HttpURLConnection) generateURL(input.getPathSegments(), input.getQueryParams())
                    .openConnection();
            
            connection.setRequestProperty("User-Agent", userAgent);
            
            if(session != null)
            	connection.setRequestProperty("Cookie", "reddit_session=" + session);

            if(modhash != null)
                connection.setRequestProperty("X-Modhash", modhash);
            
            return connection;
        } catch (Exception e) {
            throw new RedditException(e);
        }
    }

    public RedditRequestResponse executeGet(RedditRequestInput input) throws RedditException {
        return readResponse(getConnection(input), input);
    }

    public RedditRequestResponse executePost(RedditRequestInput input) throws RedditException {
        HttpURLConnection connection = getConnection(input);
        
        connection.setDoOutput(true);
        
        try {
			connection.setRequestMethod("POST");
		} catch (ProtocolException e) {
			throw new RedditException(e);
		}

        OutputStream outputStream = null;
        
        try {
            outputStream = connection.getOutputStream();
            IOUtils.write(generateUrlEncodedForm(input.getFormParams()), outputStream, "UTF-8");
        } catch (IOException e) {
            throw new RedditException(e);
        } finally {
            IOUtils.closeQuietly(outputStream);
        }

        return readResponse(connection, input);
    }

    private RedditRequestResponse readResponse(HttpURLConnection connection, RedditRequestInput input) throws RedditException {
        InputStream stream = null;

        try {
            // Connection will be made here
            stream = connection.getInputStream();

            // Buffers internally
            String responseBody = IOUtils.toString(stream, "UTF-8");
            int statusCode = connection.getResponseCode();

            if (statusCode != HttpURLConnection.HTTP_OK) {
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

    private String generateUrlEncodedForm(Map<String, String> formParams) {
        QueryBuilder builder = new QueryBuilder();

        for (Map.Entry<String, String> entry : formParams.entrySet()) {
            builder.addParameter(entry.getKey(), entry.getValue());
        }

        return builder.build();
    }

    private URL generateURL(List<String> pathSegments,
            Map<String, String> queryParams) throws MalformedURLException {

        String path = "";
        String query = "";

        if (pathSegments != null) {
            StringBuilder pathBuilder = new StringBuilder("/");
            Iterator<String> itr = pathSegments.iterator();

            while (itr.hasNext()) {
                pathBuilder.append(itr.next());

                if (itr.hasNext()) {
                    pathBuilder.append("/");
                }
            }

            path = pathBuilder.toString();
        }

        if (queryParams != null) {
            QueryBuilder builder = new QueryBuilder();

            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                builder.addParameter(entry.getKey(), entry.getValue());
            }

            query = "?" + builder.build();
		}
		
		return new URL("http", HOST, path+query);
	}

	public void setModhashHeader(String modhash) {
		this.modhash = modhash;
	}
	
    public void setSession(String session) {
        this.session = session;
    }

    private String generateErrorString(final int statusCode,
            						   final RedditRequestInput input,
            						   final String responseBody) {
    	
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
