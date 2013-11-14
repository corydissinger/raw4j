package com.cd.reddit.json.mapping;

import java.util.List;

import org.codehaus.jackson.JsonNode;

/**
 * This class is part of the current smelly code. Further investigation into the structure of the JSON and which API calls return them needs to be done. 
 * 
 * Used for the following API calls:
 * 		- /api/login
 * 		- /api/comment
 *
 * @author <a href="https://github.com/corydissinger">Cory Dissinger</a>
 */
public class RedditJsonMessage {
	private List<String> errors;
	private String modhash;
	private String cookie;
    private String iden;
	private JsonNode data;	
	
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
    public void setIden(String iden) {
        this.iden = iden;
    }
    public String getIden() {
        return iden;
    }
	
	public JsonNode getData() {
		return data;
	}
	public void setData(JsonNode data) {
		this.data = data;
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
        builder.append(", iden=");
        builder.append(iden);
		builder.append(", data=");
		builder.append(data);		
		builder.append("]");
		return builder.toString();
	}
}
