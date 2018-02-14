package com.epam.jdi.tools;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public final class RegExUtils {
    private RegExUtils() { }
    public static List<String> matches(String str, String regEx) {
        List<String> result = new ArrayList<>();
        Pattern pattern = compile(regEx);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find())
            result.add(matcher.group());
        return result;
    }
}