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

package com.cd.reddit.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cd.reddit.http.requestor.RedditRequestor;
import com.cd.reddit.http.util.RedditApiParameterConstants;
import com.cd.reddit.http.util.RedditApiResourceConstants;
import com.cd.reddit.http.util.RedditRequestInput;
import com.cd.reddit.http.util.RedditRequestResponse;
import com.cd.reddit.json.exception.RedditJsonException;
import com.cd.reddit.json.parser.RedditJsonParser;

public class RedditRequestorDriver {

	public static void main(String[] args){
		List<String> testSegments = new ArrayList<String>();
		testSegments.add(RedditApiResourceConstants.R);		
		testSegments.add("politics");
		testSegments.add(RedditApiResourceConstants.NEW + RedditApiResourceConstants.DOT_JSON);

		//Throway account for proof-of-concept purposes
		final String testUserAgent = "JavaJerseyTestBot/1.0 by Cory Dissinger";
		
		RedditRequestInput testInput = new RedditRequestInput(testSegments, 
															  testUserAgent); 
		
		RedditRequestResponse response =  RedditRequestor.executeGet(testInput);
		RedditJsonParser parser = new RedditJsonParser(response.getBody());
		
		try{
			parser.parse();
		}catch(RedditJsonException re) {
			re.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
