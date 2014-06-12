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
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Implements the Java bean version of the JSON found <a href="https://github.com/reddit/reddit/wiki/JSON#account">here</a>.
 * 
 * This class makes use of the @JsonProperty Jackson annotation to maintain camel case.
 * Please see <a href="http://stackoverflow.com/questions/10519265/jackson-overcoming-underscores-in-favor-of-camel-case">this StackOverflow post</a>
 * 
 * @author <a href="https://github.com/corydissinger">Cory Dissinger</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedditAccount extends RedditType{
	
	@JsonProperty("comment_karma")
	private int commentKarma;
	
	private long created;
	
	@JsonProperty("created_utc")
	private long createdUtc;
	
	@JsonProperty("gold_credits")
	private int goldCredits;
	
	@JsonProperty("has_mail")
	private String hasMail;	
	
	@JsonProperty("has_mod_mail")
	private boolean hasModMail;
	
	@JsonProperty("has_verified_email")
	private boolean hasVerifiedEmail;	
	
	private String id;
	
	@JsonProperty("is_friend")
	private boolean isFriend;
	
	@JsonProperty("is_gold")
	private boolean isGold;
	
	@JsonProperty("is_mod")
	private boolean isMod;
	
	@JsonProperty("link_karma")
	private int linkKarma;
	
	private String modhash;
	
	private String name;
	
	@JsonProperty("over_18")
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
	public int getGoldCredits() {
		return goldCredits;
	}
	public void setGoldCredits(int goldCredits) {
		this.goldCredits = goldCredits;
	}
	public String getHasMail() {
		return hasMail;
	}
	public void setHasMail(String hasMail) {
		this.hasMail = hasMail;
	}
	public boolean getHasModMail() {
		return hasModMail;
	}
	public void setHasModMail(boolean hasModMail) {
		this.hasModMail = hasModMail;
	}
	public boolean isHasVerifiedEmail() {
		return hasVerifiedEmail;
	}
	public void setHasVerifiedEmail(boolean hasVerifiedEmail) {
		this.hasVerifiedEmail = hasVerifiedEmail;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isFriend() {
		return isFriend;
	}
	public void setFriend(boolean isFriend) {
		this.isFriend = isFriend;
	}
	public boolean isGold() {
		return isGold;
	}
	public void setGold(boolean isGold) {
		this.isGold = isGold;
	}
	public boolean isMod() {
		return isMod;
	}
	public void setMod(boolean isMod) {
		this.isMod = isMod;
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
		builder.append(", goldCredits=");
		builder.append(goldCredits);
		builder.append(", hasMail=");
		builder.append(hasMail);
		builder.append(", hasModMail=");
		builder.append(hasModMail);
		builder.append(", hasVerifiedEmail=");
		builder.append(hasVerifiedEmail);
		builder.append(", id=");
		builder.append(id);
		builder.append(", isFriend=");
		builder.append(isFriend);
		builder.append(", isGold=");
		builder.append(isGold);
		builder.append(", isMod=");
		builder.append(isMod);
		builder.append(", linkKarma=");
		builder.append(linkKarma);
		builder.append(", modhash=");
		builder.append(modhash);
		builder.append(", name=");
		builder.append(name);
		builder.append(", over18=");
		builder.append(over18);
		builder.append("]");
		return builder.toString();
	}
}
