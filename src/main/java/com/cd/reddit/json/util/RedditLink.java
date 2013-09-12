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
