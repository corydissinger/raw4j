[![Build Status](https://travis-ci.org/corydissinger/raw4j.png)](https://travis-ci.org/corydissinger/raw4j)
# Goals/Philosophy
______________
**Important Triple Bulleted List**

- Java (Jersey+Jackson APIs) based Reddit API client
- Minimal memory footprint with fewest LoC
- Easy to understand and implement

**raw4j** provide a light-weight, injectable dependency that allows for an intuitive but not overly opinionated Java Reddit API client. 

As of 09/05/2013 no truly complete Java Reddit API clients really exist - [check here](https://github.com/reddit/reddit/wiki/API-Wrappers).


# Usage
______________
Most of this example was pulled directly from the ['Requestor'](https://github.com/corydissinger/raw4j/blob/master/src/test/java/com/cd/requestor/RedditRequestorTest.java) JUnit test class.

Please note I have omitted the @Test annotation from many of the methods to prevent frivolous API calls.

The Input and Output objects have utility toString and hashCode methods overridden for additional programmatic power.

Here is an example with the current code of logging in with a user.

```java

Reddit reddit = new Reddit("my-user-agent");
reddit.login("myUsername", "myPassword");

//More to come...

```

# To Dos
- Easier Maven-based installation via something like Central
- Convenience classes to abstract away the I/O objects?


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



