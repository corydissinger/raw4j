reddit-jersey-client
====================


Project Goals
--------------------
PRAW currently resides as the premier option for Reddit API scraping. This makes sense as it allows for quicker time-to-market applications. However [performance differences between Java vs Python should be noted](http://old.nabble.com/C%2B%2B-vs-Java-vs-Python-vs-Ruby-%3A-a-first-impression-td6173984.html), and [a comparable Java API wrapper is nonexistent](https://github.com/reddit/reddit/wiki/API-Wrappers)


Open Questions
--------------------
1) Should RedditRequestor itself maintain respect for the Reddit API with 'timed locks' or should it be expected of the end-users?