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
public class RedditMessage extends RedditType{

	//Thing
	private String id;
	private String name;
	private String kind;
	private String data;
	
	//Created
	private long created_utc;
	
	//Comment
	private String author;
	private String body;
	private String replies;
	private String subject;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getReplies() {
		return replies;
	}
	public void setReplies(String replies) {
		this.replies = replies;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RedditMessage [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", kind=");
		builder.append(kind);
		builder.append(", data=");
		builder.append(data);
		builder.append(", created_utc=");
		builder.append(created_utc);
		builder.append(", author=");
		builder.append(author);
		builder.append(", body=");
		builder.append(body);
		builder.append(", replies=");
		builder.append(replies);
		builder.append(", subject=");
		builder.append(subject);
		builder.append("]");
		return builder.toString();
	}	
}
