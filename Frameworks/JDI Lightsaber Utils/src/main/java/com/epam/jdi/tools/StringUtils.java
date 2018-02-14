package com.epam.jdi.tools;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.map.MapArray;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Character.toLowerCase;
import static java.util.Arrays.asList;
import static java.util.regex.Matcher.quoteReplacement;

public final class StringUtils {
    public static final String LINE_BREAK = System.getProperty("line.separator");

    public static boolean namesEqual(String name1, String name2) {
        return name1.toLowerCase().replace(" ", "").equals(name2.toLowerCase().replace(" ", ""));
    }

    public static String msgFormat(String template, List<Object> args) {
        String result = template;
        for (int i=0;i<args.size();i++)
            if (template.contains("{"+i+"}"))
                result = result.replaceAll("\\{"+i+"}", args.get(i).toString());
        return result;
    }
    public static String msgFormat(String template, MapArray<String, Object> args) {
        final Matcher matcher = Pattern.compile("\\{([^}]*)}").matcher(template);
        final StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            final String pattern = matcher.group(1);
            Object replacement = args.get(pattern);
            if (replacement == null) replacement = matcher.group();
            matcher.appendReplacement(sb, quoteReplacement(replacement.toString()));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    public static boolean contains(String string, String[] strings) {
        return contains(string, asList(strings));
    }
    public static boolean contains(String string, List<String> strings) {
        for (String s : strings)
            if (!string.contains(s)) return false;
        return true;
    }
    public static String splitLowerCase(String text) {
        String result = "";
        for (int i = 0; i < text.length(); i++) {
            if (isCapital(text.charAt(i)))
                result += " ";
            result += toLowerCase(text.charAt(i));
        }
        return result;
    }

    public static String splitCamelCase(String camel) {
        String result = (camel.charAt(0) + "").toUpperCase();
        for (int i = 1; i < camel.length() - 1; i++)
            result += (isCapital(camel.charAt(i)) && !isCapital(camel.charAt(i - 1)) ? " " : "") + camel.charAt(i);
        return result + camel.charAt(camel.length() - 1);
    }
    private static boolean isCapital(char ch) {
        return 'A' < ch && ch < 'Z';
    }

    private StringUtils() {}
}