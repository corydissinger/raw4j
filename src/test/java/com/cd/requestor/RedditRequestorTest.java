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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.cd.util.RedditApiParameterConstants;
import com.cd.util.RedditApiResourceConstants;
import com.cd.util.RedditRequestInput;

public class RedditRequestorTest {

	RedditRequestor testRequestor;
	RedditRequestInput testInput;

	//Throway account for proof-of-concept purposes
	final String testUserAgent = "JavaJerseyTestBot/1.0 by Cory Dissinger";	
	
	@Test
	public void loginTest(){
		List<String> testSegments = new ArrayList<String>();
		testSegments.add(RedditApiResourceConstants.API);
		testSegments.add(RedditApiResourceConstants.LOGIN);

		Map<String, String> testBodyParams = new HashMap<String, String>();
		
		testBodyParams.put(RedditApiParameterConstants.API_TYPE, RedditApiParameterConstants.JSON);
		testBodyParams.put(RedditApiParameterConstants.PASSWD, "JavaJerseyTestBot");
		testBodyParams.put(RedditApiParameterConstants.USER, "JavaJerseyTestBot");
		
		RedditRequestInput testInput 
							= new RedditRequestInput(testSegments, 
													 testUserAgent, 
													 null,
													 testBodyParams);
		
		RedditRequestor testRequestor = new RedditRequestor(testInput);
		testRequestor.executePost();		
	}
}
