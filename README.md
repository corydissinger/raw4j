[![Build Status](https://travis-ci.org/corydissinger/raw4j.png)](https://travis-ci.org/corydissinger/raw4j)
# Contributing
______________
**Please follow these rules before submitting pull requests...**

- No Java 1.7 specific features in master branch (yet)
- 'mvn clean install' should build successfully on your machine


# Goals/Philosophy
______________
**Important Triple Bulleted List**

- Easy to understand and implement
- Java based Reddit API client
- Compatible with as many Java platforms as possible (1.6, 1.7, Android)

**raw4j** provide a light-weight, injectable dependency that allows for an intuitive but not overly opinionated Java Reddit API client. HTTP communication is accomplished using the [Apache Http Components](http://hc.apache.org/) API. JSON parsing is handled using the [Jackson](https://github.com/FasterXML/jackson) API.


# Example Usage
______________

Here is an example with the current code of logging in with a user.

```java

Reddit reddit = new Reddit("my-user-agent");

try{
  reddit.login("myUsername", "myPassword");
  List<RedditSubreddit> popularSubreddits = reddit.subredditsPopular();
  List<RedditLink> topJavaLinks = reddit.listingFor("java", "top");
  //More to come...  
  
}catch(RedditException re){
  e.printStackTrace();
}


```

# To Dos
- Implement all API calls (so many more...)
- Reconcile the class casting when using [Jackson](https://github.com/corydissinger/raw4j/blob/master/src/main/java/com/cd/reddit/json/jackson/RedditJsonParser.java)
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



