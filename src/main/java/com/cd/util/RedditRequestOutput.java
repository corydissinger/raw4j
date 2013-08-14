package com.cd.util;

public class RedditRequestOutput {

	public final int status;	
	public final String body;

	public RedditRequestOutput(int theStatus, String theBody) {
		status = theStatus;
		body = theBody;
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
