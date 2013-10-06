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

/**
 * Implements the Java bean version of the JSON found <a href="https://github.com/reddit/reddit/wiki/JSON#account">here</a>.
 * 
 * @author <a href="https://github.com/corydissinger">Cory Dissinger</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedditAccount extends RedditType{
	private int commentKarma;
	private long created;
	private long createdUtc;
	private String hasMail;	
	private String hasModMail;
	private String id;
	private int linkKarma;
	private String modhash;	
	private String name;
	private boolean over18;
	
	public int getCommentKarma() {
		return commentKarma;
	}
	public void setCommentKarma(int commentKarma) {
		this.commentKarma = commentKarma;
	}
	public long getCreated() {
		return created;
	}
	public void setCreated(long created) {
		this.created = created;
	}
	public long getCreatedUtc() {
		return createdUtc;
	}
	public void setCreatedUtc(long createdUtc) {
		this.createdUtc = createdUtc;
	}
	public String getHasMail() {
		return hasMail;
	}
	public void setHasMail(String hasMail) {
		this.hasMail = hasMail;
	}
	public String getHasModMail() {
		return hasModMail;
	}
	public void setHasModMail(String hasModMail) {
		this.hasModMail = hasModMail;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getLinkKarma() {
		return linkKarma;
	}
	public void setLinkKarma(int linkKarma) {
		this.linkKarma = linkKarma;
	}
	public String getModhash() {
		return modhash;
	}
	public void setModhash(String modhash) {
		this.modhash = modhash;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isOver18() {
		return over18;
	}
	public void setOver18(boolean over18) {
		this.over18 = over18;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RedditAccount [commentKarma=");
		builder.append(commentKarma);
		builder.append(", created=");
		builder.append(created);
		builder.append(", createdUtc=");
		builder.append(createdUtc);
		builder.append(", hasMail=");
		builder.append(hasMail);
		builder.append(", hasModMail=");
		builder.append(hasModMail);
		builder.append(", id=");
		builder.append(id);
		builder.append(", linkKarma=");
		builder.append(linkKarma);
		builder.append(", name=");
		builder.append(name);
		builder.append(", over18=");
		builder.append(over18);
		builder.append("]");
		return builder.toString();
	}	
}
