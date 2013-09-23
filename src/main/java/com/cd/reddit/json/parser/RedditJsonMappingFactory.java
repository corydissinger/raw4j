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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.cd.reddit.RedditException;
import com.cd.reddit.json.mapping.RedditAccount;
import com.cd.reddit.json.mapping.RedditComment;
import com.cd.reddit.json.mapping.RedditLink;
import com.cd.reddit.json.mapping.RedditMessage;
import com.cd.reddit.json.mapping.RedditSubreddit;
import com.cd.reddit.json.mapping.RedditType;
import com.cd.reddit.json.util.RedditJsonConstants;

public class RedditJsonMappingFactory {
	public static List<RedditType> mapJsonArrayToList(JsonNode jsonArray, ObjectMapper mapper) throws RedditException{
		final List<RedditType> theTypes = new ArrayList<RedditType>(10);
		
		final Iterator<JsonNode> nodeItr = jsonArray.getElements();
		
		while(nodeItr.hasNext()){
			final JsonNode nextJson = nodeItr.next();
			final String jsonKind 	= nextJson.get(RedditJsonConstants.KIND).asText();
			
			if(RedditJsonConstants.LISTING.equals(jsonKind)){
				theTypes.addAll(mapJsonArrayToList(nextJson, mapper));
			}else{
				final JsonNode dataJson = nextJson.get(RedditJsonConstants.DATA);
				theTypes.add(mapJsonObjectToType(dataJson, jsonKind, mapper));	
			}
		}
		
		theTypes.removeAll(Collections.singleton(null));		
		
		return theTypes;
	}

	public static List<RedditType> mapJsonObjectToList(JsonNode jsonObject, String kind, ObjectMapper mapper) throws RedditException{
		final List<RedditType> theTypes;

		if(RedditJsonConstants.LISTING.equals(kind)){
			theTypes = mapJsonArrayToList(jsonObject, mapper);
		}else{
			theTypes = new ArrayList<RedditType>(1);
			theTypes.add(mapJsonObjectToType(jsonObject, kind, mapper));
		}
		
		theTypes.removeAll(Collections.singleton(null));		
		
		return theTypes;
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
			throw new RedditException(e.getMessage());
		}
		
		return theType;
	}	
	
}
