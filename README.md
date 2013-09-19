[![Build Status](https://travis-ci.org/corydissinger/raw4j.png)](https://travis-ci.org/corydissinger/raw4j)
# Contributing
______________
**Please follow these rules before submitting pull requests...**


# Goals/Philosophy
______________
**Important Triple Bulleted List**

- Easy to understand and implement
- High/Low level APIs
- Java (Jersey+Jackson APIs) based Reddit API client

**raw4j** provide a light-weight, injectable dependency that allows for an intuitive but not overly opinionated Java Reddit API client. 


# Example Usage
______________

Here is an example with the current code of logging in with a user.

```java

Reddit reddit = new Reddit("my-user-agent");
reddit.login("myUsername", "myPassword");

List<RedditSubreddit> popularSubreddits = reddit.subredditsPopular();
List<RedditLink> topJavaLinks = reddit.listingFor("java", "top");

//More to come...

```

# To Dos
- Reconcile the RedditType(s)
- Easier Maven-based installation via something like Central



# License
______________
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



