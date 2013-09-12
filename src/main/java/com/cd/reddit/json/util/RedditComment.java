package com.cd.reddit.json.util;

public class RedditComment {

	//Thing
	private String id;
	private String name;
	private String kind;
	private String data;
	
	//Votable
	private int ups;
	private int downs;
	//Uncomment if this ever matters private boolean likes;
	
	//Created
	private long created_utc;
	
	//Comment
	private String author;
	private String body;
	
}
