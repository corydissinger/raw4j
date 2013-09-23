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

package com.cd.reddit.http.util;

public class RedditApiParameterConstants {

	//Misc constants
	public static final String JSON	 				= "json";	

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
}
