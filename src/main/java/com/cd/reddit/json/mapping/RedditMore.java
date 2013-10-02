package com.cd.reddit.json.mapping;

import java.util.List;

public class RedditMore {
	private int count;
	private String parent_id;
	private String id;
	private String name;
	private List<String> children;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
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
	public List<String> getChildren() {
		return children;
	}
	public void setChildren(List<String> children) {
		this.children = children;
	}
}
