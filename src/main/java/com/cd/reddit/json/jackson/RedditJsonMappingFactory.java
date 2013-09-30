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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
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

public class RedditJsonMappingFactory {
	
	//Feels like an ugly hack
	public static RedditJsonMessage mapJsonMessage(JsonNode jsonMessage, ObjectMapper mapper) throws RedditException{
		RedditJsonMessage parsedMessage = new RedditJsonMessage();
		final List<String> errors = new ArrayList<String>(1);
		final Iterator<JsonNode> nodeItr = jsonMessage.get(RedditJsonConstants.ERRORS).getElements();
		
		while(nodeItr.hasNext()){
			final String error = nodeItr.next().asText();
			errors.add(error);
		}
		
		final JsonNode data = jsonMessage.get(RedditJsonConstants.DATA);
		
		parsedMessage.setErrors(errors);
		parsedMessage.setCookie(data.get(RedditJsonConstants.COOKIE).asText());
		parsedMessage.setModhash(data.get(RedditJsonConstants.MODHASH).asText());		
		
		return parsedMessage;
	}
	
	public static List<RedditType> mapJsonArrayToList(JsonNode jsonArray, ObjectMapper mapper, String specifiedType) throws RedditException{
		final List<RedditType> theTypes = new ArrayList<RedditType>(10);
		
		final Iterator<JsonNode> nodeItr = jsonArray.getElements();
		
		while(nodeItr.hasNext()){
			final JsonNode nextJson = nodeItr.next();
			final String jsonKind 	= nextJson.get(RedditJsonConstants.KIND).asText();
			
			if(RedditJsonConstants.LISTING.equals(jsonKind)){
				theTypes.addAll(mapJsonArrayToList(nextJson, mapper, specifiedType));
			}else{
				final JsonNode dataJson = nextJson.get(RedditJsonConstants.DATA);
				theTypes.add(mapJsonObjectToSpecifiedType(dataJson, jsonKind, mapper, specifiedType));	
			}
		}
		
		theTypes.removeAll(Collections.singleton(null));		
		
		return theTypes;
	}

	public static RedditType mapJsonObjectToSpecifiedType(JsonNode jsonObject, String kind, ObjectMapper mapper, String specifiedType) throws RedditException{
		RedditType theType = null;
		
		if(specifiedType.equals(kind)){
			theType = mapJsonObjectToType(jsonObject, specifiedType, mapper);				
		}
		
		return theType;
	}

	public static RedditType mapJsonObjectToType(JsonNode jsonObject, String kind, ObjectMapper mapper) throws RedditException{
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
			throw new RedditException(e.getMessage(), e.getCause());
		}
		
		return theType;
	}	
	
}
