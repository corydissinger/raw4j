package com.cd.reddit.http.retrofit.converter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

import com.cd.reddit.RedditException;
import com.cd.reddit.json.mapping.RedditSubreddit;
import com.cd.reddit.json.parser.RedditJsonParser;

public class RedditJacksonConverter implements Converter {

	RedditJsonParser parser;
	
	//TODO: This converter business is very strange, need to reconcile...
	@Override
	public Object fromBody(TypedInput body, Type type) throws ConversionException {
		List<RedditSubreddit> subreddits = null;
		
		try {
			parser = new RedditJsonParser(body.in());
		} catch (IOException e) {
			throw new ConversionException(e.getMessage());
		}
		
		try {
			//TODO: This is not going to work going forward..
			subreddits = parser.parseSubreddits();
		} catch (RedditException e) {
			throw new ConversionException(e.getMessage());
		}
		
		return subreddits;
	}

	@Override
	public TypedOutput toBody(Object object) {
		// TODO Not sure if this needs to be implemented
		return null;
	}

}
