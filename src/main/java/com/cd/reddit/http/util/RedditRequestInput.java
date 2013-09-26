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

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class RedditRequestInput {

	private final List<String> pathSegments;
	private final Map<String, String> queryParams;
	private final Map<String, String> formParams;
	private int hashCode;
	
	public RedditRequestInput(List<String> thePathSegments){
		pathSegments = thePathSegments;
		queryParams = null;
		formParams = null;
	}
	
	public RedditRequestInput(List<String> thePathSegments,
							  Map<String, String> theQueryParams){
		pathSegments = thePathSegments;
		queryParams = theQueryParams;
		formParams = null;		
	}

	public RedditRequestInput(List<String> thePathSegments,
							  Map<String, String> theQueryParams,
							  Map<String, String> theBodyParams){
		pathSegments = thePathSegments;
		queryParams = theQueryParams;
		formParams = theBodyParams;				
	}

	public List<String> getPathSegments() {
		return pathSegments;
	}

	public Map<String, String> getQueryParams() {
		return queryParams;
	}

	public Map<String, String> getFormParams() {
		return formParams;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		String nl = System.getProperty("line.separator");
		
		builder.append("--- PATH SEGMENTS ---");
		builder.append(nl);
		for(String seg : pathSegments){
			builder.append(seg);
			builder.append(nl);			
		}
		builder.append(nl);
		
		builder.append("--- QUERY PARAMS ---");		
		builder.append(nl);
		
		if(queryParams != null){
			for(Map.Entry<String, String> entry : queryParams.entrySet()){
				builder.append(entry.getKey() + " = " + entry.getValue());
				builder.append(nl);			
			}		
			builder.append(nl);
		}
		
		builder.append("--- FORM PARAMS ---");		
		builder.append(nl);
		
		if(formParams != null){
			for(Map.Entry<String, String> entry : formParams.entrySet()){
				builder.append(entry.getKey() + " = " + entry.getValue());
				builder.append(nl);			
			}		
			builder.append(nl);
		}
		
		return builder.toString();
	}	
	
	@Override
	public int hashCode(){
		if(hashCode == 0){
			hashCode = new HashCodeBuilder(17, 41)
				.append(pathSegments)
				.append(queryParams)
				.append(queryParams)
				.toHashCode();			
		}
		
		return hashCode;
	}
}
