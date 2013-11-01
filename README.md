**Travis CI Build:** [![Build Status](https://travis-ci.org/corydissinger/raw4j.png)](https://travis-ci.org/corydissinger/raw4j)

*Please note:* the Travis CI build can fail due to the current nature of the TestNG suite hitting the Reddit API numerous times. Need to figure out a better way to run the full test suite...

# raw4j Goals/Philosophy
______________
**Important Triple Bulleted List**

- Easy to understand and implement
- Java based Reddit API client
- Compatible with as many Java platforms as possible (1.6, 1.7, Android)

**raw4j** provide a light-weight, injectable dependency that allows for an intuitive but not overly opinionated Java Reddit API client. HTTP communication is accomplished using the [Apache Http Components](http://hc.apache.org/) API. JSON parsing is handled using the [Jackson](https://github.com/FasterXML/jackson) API.

# [Contribution Rules](https://github.com/corydissinger/raw4j/blob/master/CONTRIBUTION_RULES.md)

*Hint: The above text is just a BIG link. :)*

# [Example Usage](https://github.com/corydissinger/raw4j/blob/master/IMPLEMENTED_CALLS.md)

The IMPLEMENTED_CALLS.md should be the go-to page for how to use implemented calls as well as which ones should be implemented.

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



