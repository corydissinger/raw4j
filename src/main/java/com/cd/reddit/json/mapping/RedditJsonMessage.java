package com.cd.reddit.json.mapping;

import java.util.List;

public class RedditJsonMessage {
	private List<String> errors;
	private String modhash;
	private String cookie;
	
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public String getModhash() {
		return modhash;
	}
	public void setModhash(String modhash) {
		this.modhash = modhash;
	}
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RedditJsonMessage [errors=");
		builder.append(errors);
		builder.append(", modhash=");
		builder.append(modhash);
		builder.append(", cookie=");
		builder.append(cookie);
		builder.append("]");
		return builder.toString();
	}
}
