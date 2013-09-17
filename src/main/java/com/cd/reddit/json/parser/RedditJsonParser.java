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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.cd.reddit.exception.RedditException;
import com.cd.reddit.json.RedditJacksonManager;
import com.cd.reddit.json.exception.RedditJsonException;
import com.cd.reddit.json.mapping.RedditSubreddit;
import com.cd.reddit.json.mapping.RedditType;
import com.cd.reddit.json.util.RedditJsonConstants;

public class RedditJsonParser {
	
	private final String json;

	private ObjectMapper mapper;	
	private JsonNode rootNode;

	
	public RedditJsonParser(String aJson){
		json = aJson;
	}
	
	public List<? extends RedditType> parse() throws RedditException{
		init();
		
		try {
			
			if(rootNode.isArray()){
				Iterator<JsonNode> theEles = rootNode.getElements();
				return parseManyNodes(theEles);
			}else{
				return parseRedditTypes(rootNode);
			}
			
		} catch (Exception e) {
			throw new RedditException(e.getMessage());
		}
	}
	
	private List<RedditType> parseManyNodes(Iterator<JsonNode> theEles) throws JsonParseException, JsonMappingException, RedditJsonException, IOException {
		final List<RedditType> theTypes = new ArrayList<RedditType>(20);
		
		while(theEles.hasNext()){
			final JsonNode nextNode = theEles.next();
			List<RedditType> parsedTypes = parseRedditTypes(nextNode);
			
			theTypes.addAll(parsedTypes);
		}
		
		return theTypes;
	}

	private List<RedditType> parseRedditTypes(JsonNode aNode) throws RedditJsonException, JsonParseException, JsonMappingException, IOException{
		final JsonNode kindNode = aNode.get(RedditJsonConstants.KIND);
		final String theKind;
		
		if(kindNode == null){
			throw new RedditJsonException("No kind found for node: " + aNode.toString());
		}else{
			theKind = kindNode.asText();
		}
		
		if(RedditJsonConstants.LISTING.equals(theKind)){
			final JsonNode childData = aNode.get(RedditJsonConstants.DATA).get(RedditJsonConstants.CHILDREN);
			return RedditJsonMappingFactory.mapJsonArrayToList(childData, mapper); 
		}else{
			final JsonNode childData = aNode.get(RedditJsonConstants.DATA);
			return RedditJsonMappingFactory.mapJsonObjectToList(childData, theKind, mapper);			
		} 
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
