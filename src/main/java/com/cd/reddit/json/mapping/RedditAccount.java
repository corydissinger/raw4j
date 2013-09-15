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
public class RedditAccount extends RedditType{
	private int commentKarma;
	private long created;
	private long createdUtc;
	private String hasMail;	
	private String hasModMail;
	private String id;
	private int linkKarma;
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
