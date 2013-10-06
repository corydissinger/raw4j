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

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * As far as I know using enums are one of the easiest and most reliable ways to guarentee true Singleton (one reference) in the JVM Heap at run time. 
 * 
 * @author <a href="https://github.com/corydissinger">Cory Dissinger</a>
 *
 */
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
