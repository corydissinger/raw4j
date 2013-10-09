package com.cd.reddit.exception;

import com.cd.reddit.RedditException;

public class RedditRateLimitException extends RedditException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2995616766053622672L;

	public RedditRateLimitException(Throwable cause) {
		super(cause);
	}

	public RedditRateLimitException(String message) {
		super(message);
	}	
	
}
