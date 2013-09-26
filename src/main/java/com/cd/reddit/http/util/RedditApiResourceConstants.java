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

public class RedditApiResourceConstants {
	//----------> Misc Helpers
	
	public static final String API	 				= "api";
	public static final String MESSAGE 				= "message";
	public static final String MINE 				= "mine";
	public static final String R	 				= "r";	
	public static final String SUBREDDITS			= "subreddits";
	public static final String USERS				= "users";	
	public static final String DOT_JSON				= ".json";
	
	//All constants should be organized by API sections found here: http://www.reddit.com/dev/api
	
	//----------> SECTION: account

	public static final String CLEAR_SESSIONS 		= "clear_sessions";	
	public static final String DELETE_USER 			= "delete_user";
	public static final String LOGIN 				= "login";
	public static final String ME_JSON 				= "me.json";
	public static final String REGISTER				= "register";
	public static final String UPDATE				= "update";
	//TODO: OAuth2-specific /v1/me is not implemented
	
	//----------> SECTION: apps	
	
	public static final String ADD_DEVELOPER		= "adddeveloper";
	public static final String DELETE_APP			= "deleteapp";
	public static final String REMOVE_DEVELOPER		= "removedeveloper";
	public static final String REVOKE_APP			= "revokeapp";
	public static final String SET_APP_ICON			= "setappicon";
	public static final String UPDATE_APP			= "updateapp";
	
	//----------> TODO: SECTION: captcha
	
	//----------> TODO: SECTION: flair
	
	//----------> SECTION: links & comments
	
	public static final String COMMENT		 		= "comment";
	public static final String DEL			 		= "del";
	public static final String EDITUSERTEXT	 		= "editusertext";
	public static final String HIDE			 		= "hide";
	public static final String INFO			 		= "info";
	public static final String MARKNSFW		 		= "marknsfw";
	public static final String MORECHILDREN	 		= "morechildren";
	public static final String REPORT		 		= "report";
	public static final String SAVE			 		= "save";
	public static final String SUBMIT			 	= "submit";
	public static final String UNHIDE			 	= "unhide";
	public static final String UNMARKNSFW		 	= "unmarknsfw";
	public static final String UNSAVE			 	= "unsave";
	public static final String VOTE				 	= "vote";

	//----------> SECTION: listings	
	
	public static final String COMMENTS			 	= "comments";
	public static final String ARTICLE			 	= "article";	
	public static final String CONTROVERSIAL	 	= "controversial";
	public static final String HOT				 	= "hot";
	public static final String LISTING			 	= "listing";
	public static final String NEW				 	= "new";
	public static final String RANDOM			 	= "random";
	public static final String TOP				 	= "top";	
	public static final String SORT				 	= "sort";
	
	//----------> SECTION: private messages
	
	public static final String BLOCK			 	= "block";
	public static final String COMPOSE			 	= "compose";
	public static final String READ_MESSAGE		 	= "read_message";
	public static final String UNREAD_MESSAGE		= "unread_message";
	public static final String INBOX			 	= "inbox";
	public static final String SENT				 	= "sent";
	public static final String UNREAD			 	= "unread";
	public static final String WHERE			 	= "where";
	
	//----------> SECTION: moderation
	
	public static final String APPROVE			 	= "approve";
	public static final String DISTINGUISH		 	= "distinguish";
	public static final String IGNORE_REPORTS	 	= "ignore_reports";
	public static final String LEAVECONTRIBUTOR	 	= "leavecontributor";
	public static final String LEAVEMODERATOR	 	= "leavemoderator";
	public static final String REMOVE			 	= "remove";
	public static final String UNIGNORE_REPORTS	 	= "unignore_reports";
	public static final String MODERATIONLOG	 	= "moderationlog";
	public static final String STYLESHEET		 	= "stylesheet";
	
	//----------> SECTION: TODO: multis - I don't quite understand these yet

	//----------> SECTION: search
	
	public static final String SEARCH			 	= "search";
	
	//----------> SECTION: subreddits
	
	public static final String ACCEPT_MODERATOR_INVITE 	= "accept_moderator_invite";
	public static final String DELETE_SR_HEADER		 	= "delete_sr_header";
	public static final String DELETE_SR_IMG		 	= "delete_sr_img";
	public static final String SITE_ADMIN			 	= "site_admin";
	public static final String SUBREDDIT_STYLESHEET	 	= "subreddit_stylesheet";
	public static final String SUBREDDITS_BY_TOPIC	 	= "subreddits_by_topic.json"; //Why is JSON here?
	public static final String SUBSCRIBE			 	= "subscribe";
	public static final String UPLOAD_SR_IMG		 	= "upload_sr_img";
	public static final String ABOUT				 	= "about.json";
	public static final String BANNED				 	= "banned";
	public static final String CONTRIBUTOR			 	= "contributor";
	public static final String MODERATOR			 	= "moderator";
	public static final String SUBSCRIBER			 	= "subscriber";
	public static final String POPULAR				 	= "popular";
	
	//----------> SECTION: users

	public static final String USER					 	= "user";	
	public static final String FRIEND				 	= "friend";
	public static final String SETPERMISSIONS		 	= "setpermissions";
	public static final String UNFRIEND				 	= "unfriend";
	public static final String USERNAME_AVAILABLE	 	= "username_available.json";
	public static final String DISLIKED				 	= "disliked";
	public static final String HIDDEN				 	= "hidden";
	public static final String LIKED				 	= "liked";
	public static final String OVERVIEW				 	= "overview";
	public static final String SAVED				 	= "saved";
	public static final String SUBMITTED			 	= "submitted";	
}
