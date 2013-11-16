/*
Copyright 2013 Cory Dissinger

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at 

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
*/

package com.cd.reddit.http.util;

public class RedditApiParameterConstants {

	//Misc constants
	public static final String JSON	 				= "json";
	public static final String SORT	 				= "sort";	
	public static final String TEXT		 			= "text";
	public static final String UH		 			= "uh";	
	
	//Fullname helpers
	public static final String T					= "t";	
	public static final String COMMENT_TYPE			= "t1_";
	public static final String ACCOUNT_TYPE			= "t2_";
	public static final String LINK_TYPE			= "t3_";
	public static final String MESSAGE_TYPE			= "t4_";
	public static final String SUBREDDIT_TYPE		= "t5_";
	public static final String AWARD_TYPE			= "t6_";
	public static final String PROMO_CAMPAIGN_TYPE  = "t6_";	
	
	
	//----------> SECTION: account	
	public static final String API_TYPE				= "api_type";
	public static final String PASSWD				= "passwd";
	public static final String REM					= "rem";
	public static final String USER					= "user";
	
	//----------> SECTION: listing
	public static final String AFTER				= "after";
	public static final String BEFORE				= "before";	
	
	public static final String HOUR					= "hour";
	public static final String DAY					= "day";
	public static final String WEEK					= "week";
	public static final String MONTH				= "month";
	public static final String YEAR					= "year";
	public static final String ALL					= "all";

	//----------> SECTION: links & comments	
	public static final String ID 					= "id";
	public static final String LINK_ID 				= "link_id";	
	public static final String THING_ID 			= "thing_id";
	public static final String CHILDREN 			= "children";
    public static final String VOTE_DIRECTION       = "dir";
}
