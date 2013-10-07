# raw4j API methods

The purpose of this document is to provide listing and short documentation of API methods implemented in raw4j

There are some assumptions taken here. 

##### 1) You must be aware that currently all [Exceptions](http://docs.oracle.com/javase/7/docs/api/java/lang/Exception.html) are thrown up to the [RedditException](http://corydissinger.github.io/raw4j/com/cd/reddit/RedditException.html), therefore your code should be defensive like so:

```java
try {
    //for example purposes, but it could be any of the calls
    //assume a response POJO if you want
    RedditJsonMessage response = reddit.login( "username", "password" );
} catch( RedditException re ) {
    //RedditException merely inherits Exception.
    re.printStackTrace();
}
```

##### 2) [Reddit](http://corydissinger.github.io/raw4j/com/cd/reddit/Reddit.html) currently keeps state and **requires** you to .login() before doing certain methods requiring validated reddit_cookie, modhash, etc.

# [account](http://corydissinger.github.io/raw4j/com/cd/reddit/json/mapping/RedditAccount.html)

#### POST /api/login

```java
    RedditAccount account = reddit.login( "username", "password" );
```

#### GET /api/me.json

```java
    RedditAccount account = reddit.meJson( "username", "password" );
```

### To be implemented:

* POST /api/clear_sessions
* POST /api/delete_user
* POST /api/register
* POST /api/update
* GET /v1/me

# apps

### To be implemented:

* POST /api/adddeveloper
* POST /api/deleteapp
* POST /api/removedeveloper
* POST /api/revokeapp
* POST /api/setappicon
* POST /api/updateapp

# captcha

### To be implemented:

* GET /api/needs_captcha.json
* POST /api/new_captcha
* GET captcha/*iden*

# flair

### To be implemented:

* POST /api/clearflairtemplates
* POST /api/deleteflair
* POST /api/deleteflairtemplate
* POST /api/flair
* POST /api/flairconfig
* POST /api/flaircsv
* GET /api/flairlist
* POST /api/flairtemplate
* POST /api/selectflair
* POST /api/setflairenabled

# [links](http://corydissinger.github.io/raw4j/com/cd/reddit/json/mapping/RedditLink.html) & [comments](http://corydissinger.github.io/raw4j/com/cd/reddit/json/util/RedditComments.html)
#### POST /api/comment

```java
  RedditJsonMessage responseMessage = reddit.comment( "the comment raw markdown stuff", "fullname_of_parent");
```

#### POST /api/morechildren

```java
  //We assume that you have retrieved some sort of data about the comment you wish to get morechildren for.
  RedditComments theComments = reddit.commentsFor( "some-subreddit", "link-id" );
  List<RedditComment> moreComments = testReddit.moreChildrenFor( theComments, "sort-order");
```

### To be implemented:

* POST /api/marknsfw
* POST /api/unmarknsfw
* POST /api/del
* POST /api/editusertext
* POST /api/hide
* GET /api/info
* POST /api/report
* POST /api/save
* POST /api/unhide
* POST /api/unsave
* POST /api/vote

# listings

#### GET /*hot*|*new*|*controversial*|*top*|*random*

```java
  //They have very similar calls, so we've lumped them all together
  List<RedditLink> links = reddit.listingFor( "some-subreddit", "*hot*|*new*|*controversial*|*top*|*random*");
```

#### GET /comments/article

```java
  //Assumes you already have the subreddit and thing ID
  //Also has special wrapper object
  RedditComments theComments = reddit.commentsFor( "some-subreddit", "thing-id");
```

# private [messages](http://corydissinger.github.io/raw4j/com/cd/reddit/json/mapping/RedditMessage.html)

#### GET /message/inbox

```java
  List<RedditMessage> inboxMessages = reddit.messages( "inbox");
```

### To be implemented:

* GET /message/sent
* GET /message/unread
* GET /message/*where*
* POST /api/compose
* POST /api/block
* POST /api/read_message
* POST /api/unread_message

#moderation

### To be implemented:

* POST /api/approve
* POST /api/distinguish
* POST /api/ignore_reports
* POST /api/leavecontributor
* POST /api/leavemoderator
* POST /api/remove
* POST /api/unignore_reports
* GET /moderationlog
* GET /stylesheet

#search

#### To be implemented:

* GET /search

# [subreddits](http://corydissinger.github.io/raw4j/com/cd/reddit/json/mapping/RedditSubreddit.html)

#### GET /subreddits/*popular*|*banned*|*new*

```java
  List<RedditSubreddit> subreddits = reddit.subreddits("popular");
```

#### To be implemented:

* POST /api/accept_moderator\_invite
* POST /api/delete_sr\_header
* POST /api/delete_sr\_img
* POST /api/site_admin
* POST /api/subreddit_stylesheet
* GET /api/subreddits_by\_topic.json
* POST /api/subscribe
* POST /api/upload_sr_img
* GET /r/subreddit/about.json
* GET /subreddits/mine/contributor
* GET /subreddits/mine/moderator
* GET /subreddits/mine/subscriber.json
* GET /subreddits/new
* GET /subreddits/search
* GET /subreddits/*where*

# users

### To be implemented:

* GET /user/username/disliked
* GET /user/username/hidden
* GET /user/username/liked
* GET user/username/submitted
* GET /user/username/about.json
* GET /user/username/comments
* POST /api/friend
* POST /api/setpermissions
* POST /api/unfriend
* GET /api/username_available.json
* GET /user/username/overview
* GET /user/username/saved
* GET /user/username/*where*

# wiki

### To be implemented:

* POST /api/wiki/alloweditor/add
* POST /api/wiki/alloweditor/del
* POST /api/wiki/alloweditor/act
* POST /api/wiki/edit
* POST /api/wiki/hide
* POST /api/wiki/revert
