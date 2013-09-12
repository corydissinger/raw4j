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

package com.cd.reddit.json;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;

public enum RedditJacksonManager {
	INSTANCE;
	
	private final JsonFactory jsonFactory;
	private final ObjectMapper mapper;
	
	private RedditJacksonManager(){
		jsonFactory = new JsonFactory();
		mapper = new ObjectMapper();
	}
	
	public ObjectMapper getObjectMapper(){
		return mapper;
	}
}
