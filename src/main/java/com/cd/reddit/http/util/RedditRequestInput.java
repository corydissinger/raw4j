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
