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

#### GET /api/new_captcha
```java
  RedditJsonMessage captchaMessage = reddit.newCaptcha();
  String iden = captchaMessage.getIden();
```

### To be implemented:

* GET /api/needs_captcha.json
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

#### POST /api/del

```java
  //Works for Comments and Links as far as I know
  reddit.delete( "fullname of thing to delete" );
```

#### POST /api/morechildren

```java
  //We assume that you have retrieved some sort of data about the comment you wish to get morechildren for.
  RedditComments theComments = reddit.commentsFor( "some-subreddit", "link-id" );
  List<RedditComment> moreComments = testReddit.moreChildrenFor( theComments, "sort-order");
```

#### POST /api/vote

```java
  reddit.vote("link-id", 1);    //Upvote
  reddit.vote("link-id", 0);    //Remove vote
  reddit.vote("link-id", -1);   //Downvote
```

#### POST /api/marknsfw

```java
  reddit.markNSFW("link-id");   //Mark link "Not Safe for Work"
```

#### POST /api/unmarknsfw

```java
  reddit.unmarkNSFW("link-id");   //Unmark link "Not Safe for Work"
```

### To be implemented:

* POST /api/editusertext
* POST /api/hide
* GET /api/info
* POST /api/report
* POST /api/save
* POST /api/unhide
* POST /api/unsave

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

#### GET /user/username/about.json

```java
  RedditAccount account = reddit.userInfoFor("JavaJerseyTestBot");
```

### To be implemented:
* POST /api/friend
* POST /api/setpermissions
* POST /api/unfriend
* GET /api/username_available.json
* GET /user/username/*where*

#### GET /user/username/saved
```java
    List<RedditLink> listing = testReddit.userHistory(testUserName, RedditApiResourceConstants.SAVED);
```

#### GET /user/username/overview
```java
    List<RedditLink> listing = testReddit.userHistory(testUserName, RedditApiResourceConstants.OVERVIEW);
```

#### GET user/username/submitted
```java
    List<RedditLink> listing = testReddit.userHistory(testUserName, RedditApiResourceConstants.SUBMITTED);
```

#### GET /user/username/comments
```java
    List<RedditLink> listing = testReddit.userHistory(testUserName, RedditApiResourceConstants.COMMENTS);
```

#### GET /user/username/liked
```java
    List<RedditLink> listing = testReddit.userHistory(testUserName, RedditApiResourceConstants.LIKED);
```    

#### GET /user/username/disliked
```java
    List<RedditLink> listing = testReddit.userHistory(testUserName, RedditApiResourceConstants.DISLIKED);
```

#### GET /user/username/hidden
```java
    List<RedditLink> listing = testReddit.userHistory(testUserName, RedditApiResourceConstants.HIDDEN);
```

# wiki

### To be implemented:

* POST /api/wiki/alloweditor/add
* POST /api/wiki/alloweditor/del
* POST /api/wiki/alloweditor/act
* POST /api/wiki/edit
* POST /api/wiki/hide
* POST /api/wiki/revert
