/*
This file is part of reddit-jersey-client.

reddit-jersey-client is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

reddit-jersey-client is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with reddit-jersey-client.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.cd.util;

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
