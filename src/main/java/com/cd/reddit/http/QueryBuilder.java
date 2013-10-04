/*
Copyright 2013 Cory Dissinger

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at 

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
*/

package com.cd.reddit.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: Austin
 * Date: 10/3/13
 * Time: 7:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class QueryBuilder {

    private StringBuilder stringBuilder = new StringBuilder();

    private static final String ENCODING = "UTF-8";

    public void addParameter(String key, String value){
        if(stringBuilder.length() > 0)
            stringBuilder.append('&');
        try {
            stringBuilder.append(URLEncoder.encode(key, ENCODING))
                .append('=')
                .append(URLEncoder.encode(value, ENCODING));
        } catch (UnsupportedEncodingException e) {
            //This is a problem with the JVM, not ours.
            throw new RuntimeException(e);
        }
    }

    public String build(){
        return stringBuilder.toString();
    }
}
