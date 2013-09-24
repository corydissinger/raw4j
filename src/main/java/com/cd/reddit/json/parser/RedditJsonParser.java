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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;

import com.cd.reddit.RedditException;
import com.cd.reddit.json.RedditJacksonManager;
import com.cd.reddit.json.mapping.RedditAccount;
import com.cd.reddit.json.mapping.RedditComment;
import com.cd.reddit.json.mapping.RedditLink;
import com.cd.reddit.json.mapping.RedditMessage;
import com.cd.reddit.json.mapping.RedditSubreddit;
import com.cd.reddit.json.mapping.RedditType;
import com.cd.reddit.json.util.RedditJsonConstants;

public class RedditJsonParser {
	
	private final InputStream json;

	private ObjectMapper mapper;	
	private JsonNode rootNode;

	
	public RedditJsonParser(InputStream inJson){
		json = inJson;
	}
	
	@SuppressWarnings("unchecked")
	public List<RedditAccount> parseAccounts() throws RedditException{
		init();
		
		return (List<RedditAccount>) parseSpecificType(RedditJsonConstants.TYPE_ACCOUNT);
	}
	
	@SuppressWarnings("unchecked")
	public List<RedditComment> parseComments() throws RedditException{
		init();
		
		return (List<RedditComment>) parseSpecificType(RedditJsonConstants.TYPE_COMMENT);
	}
	
	@SuppressWarnings("unchecked")
	public List<RedditLink> parseLinks() throws RedditException{
		init();
		
		return (List<RedditLink>) parseSpecificType(RedditJsonConstants.TYPE_LINK);
	}
	
	@SuppressWarnings("unchecked")
	public List<RedditMessage> parseMessages() throws RedditException{
		init();
		
		return (List<RedditMessage>) parseSpecificType(RedditJsonConstants.TYPE_MESSAGE);
	}
	
	@SuppressWarnings("unchecked")
	public List<RedditSubreddit> parseSubreddits() throws RedditException{
		init();
		
		return (List<RedditSubreddit>) parseSpecificType(RedditJsonConstants.TYPE_SUBREDDIT);
	}	

	private List<? extends RedditType> parseSpecificType(String specifiedType) throws RedditException{
		try {
			if(rootNode.isArray()){
				Iterator<JsonNode> theEles = rootNode.getElements();
				return parseManyNodes(theEles, specifiedType);
			}else{
				return parseRedditTypes(rootNode, specifiedType);
			}
			
		} catch (Exception e) {
			throw new RedditException(e.getMessage());
		}		
	}
	
	private List<RedditType> parseManyNodes(Iterator<JsonNode> theEles, String specifiedType) throws RedditException {
		final List<RedditType> theTypes = new ArrayList<RedditType>(20);
		
		while(theEles.hasNext()){
			final JsonNode nextNode = theEles.next();
			List<RedditType> parsedTypes = parseRedditTypes(nextNode, specifiedType);
			
			theTypes.addAll(parsedTypes);
		}
		
		return theTypes;
	}

	private List<RedditType> parseRedditTypes(JsonNode aNode, String specifiedType) throws RedditException{
		final JsonNode kindNode = aNode.get(RedditJsonConstants.KIND);
		final String theKind;
		
		if(kindNode == null){
			throw new RedditException("No kind found for node: " + aNode.toString());
		}else{
			theKind = kindNode.asText();
		}
		
		if(RedditJsonConstants.LISTING.equals(theKind)){
			final JsonNode childData = aNode.get(RedditJsonConstants.DATA).get(RedditJsonConstants.CHILDREN);
			return RedditJsonMappingFactory.mapJsonArrayToList(childData, mapper, specifiedType); 
		}else{
			final JsonNode childData = aNode.get(RedditJsonConstants.DATA);
			final List<RedditType> singleType = new ArrayList<RedditType>(1);
			singleType.add(RedditJsonMappingFactory.mapJsonObjectToSpecifiedType(childData, theKind, mapper, specifiedType));
			return singleType;			
		} 
	}
	
	private void init() throws RedditException{
		try {
			mapper = RedditJacksonManager.INSTANCE.getObjectMapper();
			rootNode = mapper.readTree(json);
		} catch (JsonParseException e) {
			throw new RedditException(e.getMessage());
		} catch (IOException e) {
			throw new RedditException(e.getMessage());
		}		
	}
}
