package com.ustbgao.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author gaoqi
 * created At 2015/3/11.
 */
public class ExtractContentUtil {
    public static void main(String [] args){
        String str = "<content>hello</content>";
        Pattern pattern = Pattern.compile("<content>(.+)</content>");
        Matcher matcher = pattern.matcher(str);
        while(matcher.matches()){
            System.out.println(matcher.group(1));
        }
    }
}
