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

package com.cd.util;

import java.util.List;
import java.util.Map;

public class RedditRequestInput {

	private final List<String> pathSegments;
	private final Map<String, String> queryParams;
	private final Map<String, String> formParams;
	private final String userAgent;
	
	public RedditRequestInput(List<String> thePathSegments,
							  String aUserAgent){
		pathSegments = thePathSegments;
		userAgent = aUserAgent;
		queryParams = null;
		formParams = null;
	}
	
	public RedditRequestInput(List<String> thePathSegments,
							  String aUserAgent,
							  Map<String, String> theQueryParams){
		pathSegments = thePathSegments;
		userAgent = aUserAgent;		
		queryParams = theQueryParams;
		formParams = null;		
	}

	public RedditRequestInput(List<String> thePathSegments,
			  				  String aUserAgent,			
							  Map<String, String> theQueryParams,
							  Map<String, String> theBodyParams){
		pathSegments = thePathSegments;
		userAgent = aUserAgent;		
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

	public String getUserAgent() {
		return userAgent;
	}	
}
