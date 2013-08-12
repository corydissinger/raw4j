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

package com.cd.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cd.requestor.RedditRequestor;
import com.cd.util.RedditApiParameterConstants;
import com.cd.util.RedditApiResourceConstants;
import com.cd.util.RedditRequestInput;

public class RedditRequestorDriver {

	public static void main(String[] args){
		List<String> testSegments = new ArrayList<String>();
		testSegments.add(RedditApiResourceConstants.API);
		testSegments.add(RedditApiResourceConstants.LOGIN);

		//Throway account for proof-of-concept purposes
		final String testUserAgent = "JavaJerseyTestBot/1.0 by Cory Dissinger";
		
		Map<String, String> testQueryParams = new HashMap<String, String>();
		
		testQueryParams.put(RedditApiParameterConstants.API_TYPE, RedditApiParameterConstants.JSON);
		testQueryParams.put(RedditApiParameterConstants.PASSWD, "JavaJerseyTestBot");
		testQueryParams.put(RedditApiParameterConstants.USER, "JavaJerseyTestBot");
		
		RedditRequestInput testInput = new RedditRequestInput(testSegments, testUserAgent, testQueryParams);
		RedditRequestor testRequestor = new RedditRequestor(testInput);
		testRequestor.executePost();
	}
	
}
