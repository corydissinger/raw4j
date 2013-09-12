package com.cd.reddit.json.util;

public class RedditLink extends RedditType{
	
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
	
	//Link
	private String author;
	private String domain;
	private int numComments;
	private boolean over18;
	private String permalink;
	private String subreddit;
	private String subredditId;
	private String title;
}
