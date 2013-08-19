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

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class RedditRequestOutput {

	private final int status;	
	private final String body;
	private int hashCode;
	
	public RedditRequestOutput(int theStatus, String theBody) {
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
		StringBuilder builder = new StringBuilder();
		String nl = System.getProperty("line.separator");
		
		builder.append("--- RESPONSE CODE ---");
		builder.append(nl);		
		builder.append(status);
		builder.append(nl);
		
		builder.append("--- RESPONSE BODY ---");
		builder.append(nl);
		builder.append(body);
		builder.append(nl);
		
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
