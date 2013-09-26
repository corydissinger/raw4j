/*
Copyright 2013 Cory Dissinger

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at 

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
*/

package com.cd.reddit.json.mapping;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonValue;

@JsonIgnoreProperties(ignoreUnknown = true)
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
	
	@JsonValue
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@JsonValue
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getUps() {
		return ups;
	}
	public void setUps(int ups) {
		this.ups = ups;
	}
	public int getDowns() {
		return downs;
	}
	public void setDowns(int downs) {
		this.downs = downs;
	}
	public long getCreated_utc() {
		return created_utc;
	}
	public void setCreated_utc(long created_utc) {
		this.created_utc = created_utc;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public int getNumComments() {
		return numComments;
	}
	public void setNumComments(int numComments) {
		this.numComments = numComments;
	}
	public boolean isOver18() {
		return over18;
	}
	public void setOver18(boolean over18) {
		this.over18 = over18;
	}
	public String getPermalink() {
		return permalink;
	}
	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}
	public String getSubreddit() {
		return subreddit;
	}
	public void setSubreddit(String subreddit) {
		this.subreddit = subreddit;
	}
	public String getSubredditId() {
		return subredditId;
	}
	public void setSubredditId(String subredditId) {
		this.subredditId = subredditId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RedditLink [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", kind=");
		builder.append(kind);
		builder.append(", data=");
		builder.append(data);
		builder.append(", ups=");
		builder.append(ups);
		builder.append(", downs=");
		builder.append(downs);
		builder.append(", created_utc=");
		builder.append(created_utc);
		builder.append(", author=");
		builder.append(author);
		builder.append(", domain=");
		builder.append(domain);
		builder.append(", numComments=");
		builder.append(numComments);
		builder.append(", over18=");
		builder.append(over18);
		builder.append(", permalink=");
		builder.append(permalink);
		builder.append(", subreddit=");
		builder.append(subreddit);
		builder.append(", subredditId=");
		builder.append(subredditId);
		builder.append(", title=");
		builder.append(title);
		builder.append("]");
		return builder.toString();
	}
}
