package com.springapp.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTrimmer {

    public static String Trim(String str) {
        Pattern trimmer = Pattern.compile("^\\s+|\\s+$");
        Matcher m = trimmer.matcher(str);
        StringBuffer out = new StringBuffer();
        while (m.find())
            m.appendReplacement(out, "");
        m.appendTail(out);

        str = out.toString();

        return str;
    }

    public static String TrimAll(String str) {
        return str.replace(" ", "");
    }


}
