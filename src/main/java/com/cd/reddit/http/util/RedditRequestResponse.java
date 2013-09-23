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
