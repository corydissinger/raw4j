/*
This file is part of raw4j.

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
*/


package com.cd.reddit;

import org.junit.Before;
import org.junit.Test;

public class RedditTest {

	@Before
	public void waitForNextRequest(){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	

	@Test
	public void testLogin(){
		
	}
	
	@Test
	public void testSubreddits(){
		
	}
	
	@Test
	public void testComments(){
		
	}	
}
