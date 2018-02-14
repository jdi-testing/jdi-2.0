package com.epam.jdi.uitests.core.utils.common;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

public class Filters {
    public static IFilter<String> equalsTo(String expected) {
        return actual -> actual.equals(expected);
    }
    public static IFilter<String> equals(String expected) {
        return equalsTo(expected);
    }
    public static IFilter<String> withValue(String expected) {
        return equalsTo(expected);
    }
    public static IFilter<String> contains(String expected) {
        return actual -> actual.contains(expected);
    }
    public static IFilter<String> matchesRegEx(String expected) {
        return actual -> actual.matches(expected);
    }
    public static IFilter<String> isMatch(String expected) {
        return matchesRegEx(expected);
    }
    public static IFilter<String> match(String expected) {
        return matchesRegEx(expected);
    }
    public static IFilter<String> equalsToLower(String expected) {
        return actual -> actual.toLowerCase().trim().equals(expected.toLowerCase().trim());
    }
    public static IFilter<String> containsLetters(String expected) {
        return actual -> actual.toLowerCase().trim().contains(expected.toLowerCase().trim());
    }

    public static IFilter<Integer> equalsTo(int number) { return i -> i==number; }
    public static IFilter<Integer> moreThan(int number) { return i -> i>number; }
    public static IFilter<Integer> lessThan(int number) { return i -> i<number; }

    public static <T> IFilter<T> not(IFilter<T> filter) { return t -> !filter.execute(t); }
}
