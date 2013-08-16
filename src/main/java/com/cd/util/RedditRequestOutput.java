package com.cd.util;

public class RedditRequestOutput {

	private final int status;	
	private final String body;

	public RedditRequestOutput(int theStatus, String theBody) {
		status = theStatus;
		body = theBody;
	}
	
	public int getStatus() {
		return status;
	}

	public String getBody() {
		return body;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		String nl = System.getProperty("line.separator");
		builder.append(status);
		builder.append(nl);
		builder.append(body);
		builder.append(nl);
		return builder.toString();
	}
}
