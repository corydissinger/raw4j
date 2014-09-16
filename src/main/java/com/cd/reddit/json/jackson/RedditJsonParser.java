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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;

import com.cd.reddit.RedditException;
import com.cd.reddit.exception.RedditRateLimitException;
import com.cd.reddit.json.mapping.RedditAccount;
import com.cd.reddit.json.mapping.RedditComment;
import com.cd.reddit.json.mapping.RedditJsonMessage;
import com.cd.reddit.json.mapping.RedditLink;
import com.cd.reddit.json.mapping.RedditMessage;
import com.cd.reddit.json.mapping.RedditMore;
import com.cd.reddit.json.mapping.RedditSubreddit;
import com.cd.reddit.json.mapping.RedditType;
import com.cd.reddit.json.util.RedditComments;
import com.cd.reddit.json.util.RedditJsonConstants;

/**
 *
 * Uses a combination of <a href="http://wiki.fasterxml.com/JacksonInFiveMinutes#JSON_Three_Ways">Jacksons Tree Model and Data Binding</a> APIs
 * to parse Reddit API JSON.
 * <br/>
 * <br/>
 * Future implementations can and should use <a href="http://wiki.fasterxml.com/JacksonStreamingApi">the Streaming API</a> for great justice.
 * <br/>
 * <br/>
 * 
 * @see <a href="http://www.reddit.com/dev/api">Reddit's Full, Live Built-In Documentation</a>
 * @see <a href="https://github.com/reddit/reddit/wiki/JSON">Reddit JSON Structure Reference</a>
 * 
 * @author <a href="https://github.com/corydissinger">Cory Dissinger</a>
 * @author <a href="https://github.com/ifrins">Francesc Bruguera</a>
 */
public class RedditJsonParser {
	
	private final String json;
	private JsonNode rootNode;
	
	private ObjectMapper mapper;	

	
	/**
	 * It would probably be smarter to use refernces to Stream objects rather than Strings. Strings are the bane of JVM performance.
	 * 
	 * @param string The entire JSON object
	 */
	public RedditJsonParser(String string){
		json 		= string;
		rootNode 	= null;
	}

	/**
	 * It would probably be smarter to use refernces to Stream objects rather than Strings. Strings are the bane of JVM performance.
	 * 
	 * @param startNode The entire JSON object
	 */
	public RedditJsonParser(JsonNode startNode){
		rootNode 	= startNode;
		json 		= null;
	}	
	
	/**
	 * This may not work for all Reddit API JSON response 'messages'
	 * Sometimes the message is nested within a "json" key, as is the case for /api/login and /api/comment.
	 * Do not assume this will work in all cases. YMMV.
	 * 
	 * @return RedditJsonMessage The parsed JSON message
	 * @throws RedditException
	 */
	public RedditJsonMessage parseJsonMessage() throws RedditException{
		init();
		
		return mapJsonMessage(rootNode.get("json"));
	}
	
	/**
	 * Comments are a strange beast. Currently this method only parses correctly for JSON retrieved by 
	 * {@link com.cd.reddit.Reddit#commentsFor(String, String) commentsFor} method. The structure of the json always
	 * has a parent Link, array of comments, and a 'more' object. This explains the need for a new POJO to encapsulate
	 * the data, as seen by {@link com.cd.reddit.json.util.RedditComments }
	 * 
	 * @return RedditComments Comments object.
	 * @throws RedditException
	 */
	public RedditComments parseComments() throws RedditException{
		init();
		
		return mapJsonComments();
	}
	
	/**
	 * Occassionally we will ONLY want to extract the Comment-type Things from a JSON string. 
	 * In those cases, use this method.
	 * 
	 * @return List<RedditComment> List of parsed comments
	 * @throws RedditException
	 */
	@SuppressWarnings("unchecked")
	public List<RedditComment> parseCommentsOnly() throws RedditException{
		init();
		
		return (List<RedditComment>) parseSpecificType(rootNode, RedditJsonConstants.TYPE_COMMENT);
	}	

	/**
	 * Parses JSON from a <a href="http://www.reddit.com/dev/api#POST_api_morechildren">Reddit API morechildren</a> call. 
	 * The structure of this JSON is very bizarre, please see src/test/resources/example-morechildren.json.
	 * 
	 * @return List<RedditComment> All of the Comment type things found in the morechildren JSON
	 * @throws RedditException
	 */
	public List<RedditComment> parseMoreChildren() throws RedditException{
		init();
		
		final List<RedditComment> parsedComments = new ArrayList<RedditComment>(10);
		final JsonNode thingsArray = rootNode.get(RedditJsonConstants.JSON).get(RedditJsonConstants.DATA).get(RedditJsonConstants.THINGS);
		final Iterator<JsonNode> thingsItr = thingsArray.getElements();
		
		while(thingsItr.hasNext()){
			final JsonNode commentThing = thingsItr.next();
			RedditComment theComment;
			
			try {
				theComment = mapper.readValue(commentThing.get(RedditJsonConstants.DATA), RedditComment.class);
			} catch (final Exception e) {
				throw new RedditException(e);
			}
			
			parsedComments.add(theComment);
		}
		
		return parsedComments;
	}	
	
	/**
	 * Parses JSON containing any number of Link type Things.
	 * <br/>
	 * Useful with:
	 * <br/>
	 * 
	 * @return List<RedditLink> All of the Link type things found in the JSON object
	 * @throws RedditException
	 */
	@SuppressWarnings("unchecked")
	public List<RedditLink> parseLinks() throws RedditException{
		init();
		
		return (List<RedditLink>) parseSpecificType(rootNode, RedditJsonConstants.TYPE_LINK);
	}

	/**
	 * Parses all Account type Things from a given JSON object.
	 * 
	 * <br/>
	 * Useful with:
	 * <br/>
	 * 
	 * @return A list of the Account type Things found within the current JSON object
	 * @throws RedditException
	 */
	@SuppressWarnings("unchecked")
	public List<RedditAccount> parseAccounts() throws RedditException{
		init();
		
		return (List<RedditAccount>) parseSpecificType(rootNode, RedditJsonConstants.TYPE_ACCOUNT);
	}	
	
	/**
	 * Parses JSON containing any number of Message type Things.
	 * <br/>
	 * Useful with:
	 * <br/>
	 * 
	 * @return List<RedditMessage>
	 * @throws RedditException
	 */
	@SuppressWarnings("unchecked")
	public List<RedditMessage> parseMessages() throws RedditException{
		init();
		
		return (List<RedditMessage>) parseSpecificType(rootNode, RedditJsonConstants.TYPE_MESSAGE);
	}
	
	/**
	 * Parses JSON containing any number of Subreddit type Things.
	 * <br/>
	 * Useful with:
	 * <br/>
	 * 
	 * @return List<RedditSubreddit>
	 * @throws RedditException
	 */
	@SuppressWarnings("unchecked")
	public List<RedditSubreddit> parseSubreddits() throws RedditException{
		init();
		
		return (List<RedditSubreddit>) parseSpecificType(rootNode, RedditJsonConstants.TYPE_SUBREDDIT);
	}	

	//****************************************************************
	//			Begin private methods, mostly for mapping
	//****************************************************************
	
	/**
	 * Gets a reference to the should-be singleton org.codehaus.jackson.map {@link org.codehaus.jackson.map.ObjectMapper# ObjectMapper}.
	 * Uses Jacksons 
	 * 
	 * @throws RedditException
	 */
	private void init() throws RedditException{
		try {
			mapper = RedditJacksonManager.INSTANCE.getObjectMapper();

			//Allow the constructor option to parse String or JsonNode
			if(rootNode != null)
				return;
			
			rootNode = mapper.readTree(json);
			
			if(rootNode.size() == 0)
				throw new RedditException("JSON object to be parsed should not be empty!");
			
			identifyJsonExceptions();
			
		} catch (JsonParseException e) {
			throw new RedditException(e);
		} catch (IOException e) {
			throw new RedditException(e);
		} catch (RedditException e)		 {
			throw new RedditException(e);
		}
	}	
	
	/**
	 * Please put any and all Reddit JSON exceptions found here in order to allow better handling of errors for caller.
	 * 
	 * @throws RedditException
	 */
	private void identifyJsonExceptions() throws RedditException{
		final JsonNode rateLimit = rootNode.get("ratelimit");
		
		if(rateLimit != null){
			throw new RedditRateLimitException(rootNode.asText());
		}
	}
	
	private List<? extends RedditType> parseSpecificType(JsonNode theNode, String specifiedType) throws RedditException{
		try {
			if(theNode.isArray()){
				Iterator<JsonNode> theEles = theNode.getElements();
				return parseManyNodes(theEles, specifiedType);
			}else{
				return parseRedditTypes(theNode, specifiedType);
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
			return mapJsonArrayToList(childData, specifiedType); 
		}else{
			final JsonNode childData = aNode.get(RedditJsonConstants.DATA);
			final List<RedditType> singleType = new ArrayList<RedditType>(1);
			singleType.add(mapJsonObjectToSpecifiedType(childData, theKind, specifiedType));
			return singleType;			
		} 
	}
	
	@SuppressWarnings("unchecked")
	private RedditComments mapJsonComments() throws RedditException{
		final JsonNode parentLinkNode	= rootNode.get(0);
		final JsonNode commentsNode		= rootNode.get(1);
		final ArrayNode childrenNode  	= (ArrayNode)commentsNode.get(RedditJsonConstants.DATA)
												  .get(RedditJsonConstants.CHILDREN);
		
		final JsonNode moreNode			= childrenNode.get(childrenNode.size() - 1).get(RedditJsonConstants.DATA);
		
		//See the 'replies' attribute of RedditComment. It is a JsonNode
		final List<RedditLink> theParentLink	   = (List<RedditLink>) parseSpecificType(parentLinkNode, RedditJsonConstants.TYPE_LINK);
		final List<RedditComment> topLevelComments = (List<RedditComment>) parseSpecificType(rootNode, RedditJsonConstants.TYPE_COMMENT);
		final RedditMore theMore;
		
		try {
			theMore = mapper.readValue(moreNode, RedditMore.class);
		} catch (Exception e) {
			throw new RedditException(e);
		}
		
		return new RedditComments(theParentLink.get(0), topLevelComments, theMore);
	}
	
	//TODO: These messages are prevalent throughout the possible JSON responses from the API. There has to be a better parsing strategy than this one.
	private RedditJsonMessage mapJsonMessage(JsonNode jsonMessage) throws RedditException{
		RedditJsonMessage parsedMessage = new RedditJsonMessage();
		final List<String> errors = new ArrayList<String>(1);
		final Iterator<JsonNode> nodeItr = jsonMessage.get(RedditJsonConstants.ERRORS).getElements();
		
		while(nodeItr.hasNext()){
			final String error = nodeItr.next().asText();
			errors.add(error);
		}

		parsedMessage.setErrors(errors);		
		
		final JsonNode data 			= jsonMessage.get(RedditJsonConstants.DATA);
		
		if(data == null){
			throw new RedditException("The following strange JSON was recieved:" + jsonMessage.asText());
		}
			
		
		final JsonNode cookieNode 		= data.get(RedditJsonConstants.COOKIE);
		final JsonNode modhashNode 		= data.get(RedditJsonConstants.MODHASH);
        final JsonNode idenNode         = data.get(RedditJsonConstants.IDEN);
		
		parsedMessage.setData(data);
		
		if(cookieNode != null)
			parsedMessage.setCookie(cookieNode.asText());
		
		if(modhashNode != null)
			parsedMessage.setModhash(modhashNode.asText());

        if(idenNode != null)
            parsedMessage.setIden(idenNode.asText());
		
		return parsedMessage;
	}
	
	private List<RedditType> mapJsonArrayToList(JsonNode jsonArray, String specifiedType) throws RedditException{
		final List<RedditType> theTypes = new ArrayList<RedditType>(10);
		
		final Iterator<JsonNode> nodeItr = jsonArray.getElements();
		
		while(nodeItr.hasNext()){
			final JsonNode nextJson = nodeItr.next();
			final String jsonKind 	= nextJson.get(RedditJsonConstants.KIND).asText();
			
			if(RedditJsonConstants.LISTING.equals(jsonKind)){
				theTypes.addAll(mapJsonArrayToList(nextJson, specifiedType));
			}else{
				final JsonNode dataJson = nextJson.get(RedditJsonConstants.DATA);
				theTypes.add(mapJsonObjectToSpecifiedType(dataJson, jsonKind, specifiedType));	
			}
		}
		
		theTypes.removeAll(Collections.singleton(null));		
		
		return theTypes;
	}

	private RedditType mapJsonObjectToSpecifiedType(JsonNode jsonObject, String kind, String specifiedType) throws RedditException{
		RedditType theType = null;
		
		if(specifiedType.equals(kind)){
			theType = mapJsonObjectToType(jsonObject, specifiedType);				
		}
		
		return theType;
	}

	private RedditType mapJsonObjectToType(JsonNode jsonObject, String kind) throws RedditException{
		RedditType theType = null;
		
		try{
			if(RedditJsonConstants.TYPE_ACCOUNT.equals(kind)){
				theType = mapper.readValue(jsonObject, RedditAccount.class);
			}else if(RedditJsonConstants.TYPE_COMMENT.equals(kind)){
				theType = mapper.readValue(jsonObject, RedditComment.class);	
			}else if(RedditJsonConstants.TYPE_LINK.equals(kind)){
				theType = mapper.readValue(jsonObject, RedditLink.class);	
			}else if(RedditJsonConstants.TYPE_MESSAGE.equals(kind)){
				theType = mapper.readValue(jsonObject, RedditMessage.class);	
			}else if(RedditJsonConstants.TYPE_SUBREDDIT.equals(kind)){
				theType = mapper.readValue(jsonObject, RedditSubreddit.class);	
			}
		}catch(Exception e){
			throw new RedditException(e);
		}
		
		return theType;
	}	
}
