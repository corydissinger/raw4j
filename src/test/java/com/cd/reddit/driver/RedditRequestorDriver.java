/*
Copyright 2013 Cory Dissinger

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at 

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
*/

package com.cd.reddit.driver;

import java.util.ArrayList;
import java.util.List;

import com.cd.reddit.http.util.RedditApiResourceConstants;
import com.cd.reddit.http.util.RedditRequestInput;

public class RedditRequestorDriver {

	public static void main(String[] args){
		List<String> testSegments = new ArrayList<String>();
		testSegments.add(RedditApiResourceConstants.R);		
		testSegments.add("politics");
		testSegments.add(RedditApiResourceConstants.NEW + RedditApiResourceConstants.DOT_JSON);

		//Throway account for proof-of-concept purposes
		final String testUserAgent = "JavaJerseyTestBot/1.0 by Cory Dissinger";
		
		RedditRequestInput testInput = new RedditRequestInput(testSegments); 
	}
	
}
