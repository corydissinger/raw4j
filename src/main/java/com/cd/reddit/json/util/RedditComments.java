package com.cd.reddit.json.util;

import java.util.List;

import com.cd.reddit.json.mapping.RedditComment;
import com.cd.reddit.json.mapping.RedditLink;
import com.cd.reddit.json.mapping.RedditMore;

public class RedditComments {
	private final RedditLink parentLink;
	private final List<RedditComment> comments;
	private final RedditMore more;
	
	public RedditComments(final RedditLink theParentLink,
						  final List<RedditComment> theParsedTypes, 
						  final RedditMore theMore){
		parentLink = theParentLink;
		comments = theParsedTypes;
		more = theMore;
	}

	public RedditLink getParentLink(){
		return parentLink;
	}
	
	public List<RedditComment> getComments() {
		return comments;
	}

	public RedditMore getMore() {
		return more;
	}
}
