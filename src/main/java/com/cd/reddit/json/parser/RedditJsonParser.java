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

package com.cd.reddit.json.parser;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;

import com.cd.reddit.json.RedditJacksonManager;
import com.cd.reddit.json.exception.RedditJsonException;
import com.cd.reddit.json.util.RedditJsonConstants;
import com.cd.reddit.json.util.RedditType;

public class RedditJsonParser {
	
	private final String json;

	private ObjectMapper mapper;	
	private JsonNode rootNode;

	
	public RedditJsonParser(String aJson){
		json = aJson;
	}
	
	public List<RedditType> parse() throws RedditJsonException{
		init();
		
		final JsonNode theKind = rootNode.get(RedditJsonConstants.KIND);
		
		if(RedditJsonConstants.LISTING.equals(theKind)){
			JsonNode childData = theKind.get(RedditJsonConstants.DATA).get(RedditJsonConstants.CHILDREN);
			String kind = childData.get(RedditJsonConstants.KIND).asText();
			return mapJsonArrayToList(childData, kind); 
		}
		
		if(theKind == null){
			throw new RedditJsonException();
		}
		
		return null;
	}
	
	private List<RedditType> mapJsonArrayToList(JsonNode jsonArray, String kind){
		return null;
	}
	
	private void init(){
		try {
			mapper = RedditJacksonManager.INSTANCE.getObjectMapper();
			rootNode = mapper.readTree(json);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
