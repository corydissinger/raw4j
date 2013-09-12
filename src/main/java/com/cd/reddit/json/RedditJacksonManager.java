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
