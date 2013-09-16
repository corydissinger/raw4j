package com.cd.reddit.http.exception;

import com.cd.reddit.exception.RedditException;

public class RedditHttpException extends RedditException {
	private static final long serialVersionUID = 1710181522876101473L;

	public RedditHttpException(String message) {
		super(message);
	}	
	
}
