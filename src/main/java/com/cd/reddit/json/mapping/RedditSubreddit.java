/*
This file is part of raw4j.

raw4j is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

raw4j is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with raw4j.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.cd.reddit.json.mapping;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RedditSubreddit extends RedditType{
	private int accountsActive;
	private int commentScoreHideMins;	
	private String description;	
	private boolean over18;
	private long subscribers;	
	private String title;
	private String url;
	
	public int getAccountsActive() {
		return accountsActive;
	}
	public void setAccountsActive(int accountsActive) {
		this.accountsActive = accountsActive;
	}
	public int getCommentScoreHideMins() {
		return commentScoreHideMins;
	}
	public void setCommentScoreHideMins(int commentScoreHideMins) {
		this.commentScoreHideMins = commentScoreHideMins;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isOver18() {
		return over18;
	}
	public void setOver18(boolean over18) {
		this.over18 = over18;
	}
	public long getSubscribers() {
		return subscribers;
	}
	public void setSubscribers(long subscribers) {
		this.subscribers = subscribers;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RedditSubreddit [accountsActive=");
		builder.append(accountsActive);
		builder.append(", commentScoreHideMins=");
		builder.append(commentScoreHideMins);
		builder.append(", description=");
		builder.append(description);
		builder.append(", over18=");
		builder.append(over18);
		builder.append(", subscribers=");
		builder.append(subscribers);
		builder.append(", title=");
		builder.append(title);
		builder.append(", url=");
		builder.append(url);
		builder.append("]");
		return builder.toString();
	}	
}
