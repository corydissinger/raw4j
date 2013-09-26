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
Copyright 2013 Cory Dissinger

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at 

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.



