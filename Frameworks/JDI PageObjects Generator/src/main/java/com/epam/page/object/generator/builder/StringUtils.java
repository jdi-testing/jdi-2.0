package com.epam.page.object.generator.builder;

/**
 * Created by Roman_Iovlev on 10/16/2017.
 */
public class StringUtils {

    public static String splitCamelCase(String camel) {
        String trim = camel.replaceAll("[^A-Za-z0-9 ]", "").trim();
        String result = (trim.charAt(0) + "").toLowerCase();
        int spaces = 0;
        for (int i = 1; i < trim.length(); i++) {
            String letter = trim.charAt(i)+"";
            if (letter.equals(" ")) {
                if (++spaces == 3) return result; }
            else
                result += trim.charAt(i-1) == ' '
                        ? letter.toUpperCase()
                        : letter.toLowerCase();
        }
        return result;
    }
    public static String firstLetterUp(String text) {
        return (text.charAt(0)+"").toUpperCase() + text.substring(1);
    }
    public static String firstLetterDown(String text) {
        return (text.charAt(0)+"").toLowerCase() + text.substring(1);
    }
}
