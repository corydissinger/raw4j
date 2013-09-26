/*
Copyright 2013 Cory Dissinger

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at 

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
*/

package com.cd.reddit.http.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class RedditRequestResponse {

	private final int status;	
	private final String body;
	private int hashCode;
	
	public RedditRequestResponse(int theStatus, String theBody) {
		status = theStatus;
		body = theBody;
	}
	
	public int getStatus() {
		return status;
	}

	public String getBody() {
		return body;
	}

	@Override
	public String toString(){
		List<String> builder = new ArrayList<String>(10);
		String nl = System.getProperty("line.separator");
		
		builder.add("--- RESPONSE CODE ---");
		builder.add(nl);		
		builder.add(Integer.toString(status));
		builder.add(nl);
		builder.add(nl);		
		
		builder.add("--- RESPONSE BODY ---");
		builder.add(nl);
		builder.add(body);
		builder.add(nl);
		builder.add(nl);		
		
		return builder.toString();
	}
	
	@Override
	public int hashCode(){
		if(hashCode == 0){
			hashCode = new HashCodeBuilder(17, 13)
				.append(status)
				.append(body)
				.toHashCode();			
		}
		
		return hashCode;
	}	
}
