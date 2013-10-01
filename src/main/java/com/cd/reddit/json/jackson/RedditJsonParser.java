/*
Copyright 2013 Cory Dissinger

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at 

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
*/

package com.cd.reddit.json.jackson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;

import com.cd.reddit.RedditException;
import com.cd.reddit.json.mapping.RedditAccount;
import com.cd.reddit.json.mapping.RedditComment;
import com.cd.reddit.json.mapping.RedditJsonMessage;
import com.cd.reddit.json.mapping.RedditLink;
import com.cd.reddit.json.mapping.RedditMessage;
import com.cd.reddit.json.mapping.RedditSubreddit;
import com.cd.reddit.json.mapping.RedditType;
import com.cd.reddit.json.util.RedditJsonConstants;

public class RedditJsonParser {
	
	private final String json;

	private ObjectMapper mapper;	
	private JsonNode rootNode;

	
	public RedditJsonParser(String string){
		json = string;
	}
	
	public RedditJsonMessage parseJsonMessage() throws RedditException{
		init();
		
		return RedditJsonMappingFactory.mapJsonMessage(rootNode.get("json"), mapper);
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
			throw new RedditException(e);
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
			throw new RedditException(e);
		} catch (IOException e) {
			throw new RedditException(e);
		}		
	}
}
