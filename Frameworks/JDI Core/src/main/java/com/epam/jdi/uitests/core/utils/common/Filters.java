package com.epam.jdi.uitests.core.utils.common;

/**
 * Created by Roman_Iovlev on 10/31/2017.
 */
public class Filters {
    public static IStringFilter equalsTo(String expected) {
        return actual -> actual.equals(expected);
    }
    public static IStringFilter equals(String expected) {
        return equalsTo(expected);
    }
    public static IStringFilter withValue(String expected) {
        return equalsTo(expected);
    }
    public static IStringFilter isNot(String expected) {
        return actual -> !actual.equals(expected);
    }
    public static IStringFilter contains(String expected) {
        return actual -> actual.contains(expected);
    }
    public static IStringFilter matchesRegEx(String expected) {
        return actual -> actual.matches(expected);
    }
    public static IStringFilter isMatch(String expected) {
        return matchesRegEx(expected);
    }
    public static IStringFilter match(String expected) {
        return matchesRegEx(expected);
    }
    public static IStringFilter equalsToLower(String expected) {
        return actual -> actual.toLowerCase().trim().equals(expected.toLowerCase().trim());
    }
    public static IStringFilter containsLetters(String expected) {
        return actual -> actual.toLowerCase().trim().contains(expected.toLowerCase().trim());
    }

}
