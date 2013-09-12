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
