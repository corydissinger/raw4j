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

import com.cd.reddit.exception.RedditException;
import com.cd.reddit.http.requestor.RedditRequestor;
import com.cd.reddit.http.util.RedditApiParameterConstants;
import com.cd.reddit.http.util.RedditApiResourceConstants;
import com.cd.reddit.http.util.RedditRequestInput;
import com.cd.reddit.http.util.RedditRequestResponse;
import com.cd.reddit.json.mapping.RedditAccount;
import com.cd.reddit.json.mapping.RedditType;
import com.cd.reddit.json.parser.RedditJsonParser;

public class Reddit {
	private final String userAgent;
	
	public Reddit(String userAgent){
		this.userAgent = userAgent;
	}
	
	public RedditAccount login(String userName, String password) throws RedditException{
		List<String> pathSegments = new ArrayList<String>();
		
		pathSegments.add(RedditApiResourceConstants.API);
		pathSegments.add(RedditApiResourceConstants.LOGIN);		
		
		Map<String, String> bodyParams = new HashMap<String, String>();
		
		bodyParams.put(RedditApiParameterConstants.API_TYPE, RedditApiParameterConstants.JSON);
		bodyParams.put(RedditApiParameterConstants.USER, userName);
		bodyParams.put(RedditApiParameterConstants.PASSWD, password);		
		
		final RedditRequestInput requestInput 
			= new RedditRequestInput(pathSegments, userAgent, null, bodyParams);
		
		final RedditRequestResponse response = RedditRequestor.executePost(requestInput);
		
		final RedditJsonParser parser = new RedditJsonParser(response.getBody());
		List<RedditType> parsedResp;
		
		try {
			parsedResp = parser.parse();
		} catch (Exception e) {
			throw new RedditException(e.getMessage());
		}  
		
		return (RedditAccount) parsedResp.get(0);
	}
}
