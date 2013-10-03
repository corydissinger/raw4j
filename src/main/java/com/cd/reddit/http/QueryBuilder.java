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
