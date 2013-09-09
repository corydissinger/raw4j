# Goals/Philosophy
______________
**Important Triple Bulleted List**

- Java (Jersey+Jackson APIs) based Reddit API client
- Minimal memory footprint with fewest LoC
- Easy to understand and implement

**reddit-api-wrapper** provide a light-weight, injectable dependency that allows for an intuitive but not overly opinionated Java Reddit API client. 

As of 09/05/2013 no truly complete Java Reddit API clients really exist - [check here](https://github.com/reddit/reddit/wiki/API-Wrappers).


# Usage
______________
Most of this example was pulled directly from the ['Requestor'](https://github.com/corydissinger/reddit-api-wrapper/blob/master/src/test/java/com/cd/requestor/RedditRequestorTest.java) JUnit test class.

Please note I have omitted the @Test annotation from many of the methods to prevent frivolous API calls.

The Input and Output objects have utility toString and hashCode methods overridden for additional programmatic power.

Here is an example with the current code of logging in with a user.

```java
String userAgent = "SomeDudeGuyBot/1.0 by Cory Dissinger";

//Create an Iterable sequence of strings that will be joined with fw slash
List<String> testSegments = new ArrayList<String>();

testSegments.add(RedditApiResourceConstants.API);
testSegments.add(RedditApiResourceConstants.LOGIN);

Map<String, String> testBodyParams = new HashMap<String, String>();

testBodyParams.put(RedditApiParameterConstants.API_TYPE, RedditApiParameterConstants.JSON);
testBodyParams.put(RedditApiParameterConstants.USER, "my-user");
testBodyParams.put(RedditApiParameterConstants.PASSWD, "my-password");

// Input object is constructor-only as follows
// Arg 1: restful/resource/path
// Arg 2: user agent
// Arg 3: query ?params
// Arg 4: additional form/body data
// **Your mileage may vary with omitting arguments


RedditRequestInput testInput 
							= new RedditRequestInput(testSegments, 
													 testUserAgent, 
													 null,
													 testBodyParams);

final RedditRequestOutput output = RedditRequestor.executePost(testInput);

```

# To Dos
- Easier Maven-based installation via something like Central
- Convenience classes to abstract away the I/O objects?


# License
______________
reddit-api-wrapper is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

reddit-api-wrapper is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with reddit-api-wrapper.  If not, see <http://www.gnu.org/licenses/>.



