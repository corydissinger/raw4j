package com.cd.json;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;

public enum RedditJsonManager {
	INSTANCE;
	
	private final JsonFactory jsonFactory;
	private final ObjectMapper mapper;
	
	private RedditJsonManager(){
		jsonFactory = new JsonFactory();
		mapper = new ObjectMapper();
	}
}
