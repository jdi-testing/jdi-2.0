package com.epam.page.object.generator.builder;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

public class StringUtils {
    public static String firstLetterUp(String text) {
        return (text.charAt(0)+"").toUpperCase() + text.substring(1);
    }
    public static String firstLetterDown(String text) {
        return (text.charAt(0)+"").toLowerCase() + text.substring(1);
    }
}
