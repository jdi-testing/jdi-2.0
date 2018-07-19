package com.epam.matcher.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.*;

import java.util.Collection;
import java.util.List;

import static com.epam.jdi.tools.ReflectionUtils.getFields;
import static java.util.Arrays.asList;

public interface IChecker {
    IChecker ignoreCase();
    IChecker wait(int timeout);

    void restoreTimeout();

    void fail(String failMessage);
    // region areEquals
    <T> void areEquals(T actual, T expected, String failMessage);

    <T> void areEquals(T actual, T expected);
    void areEquals(String actual, String expected, String failMessage);
    void areEquals(String actual, String expected);
    <T> void areEquals(JFunc<T> actual, T expected, JFunc1<T, String> failMessage);
    <T> void areEquals(JFunc<T> actual, T expected);
    void areEquals(JFunc<String> actual, String expected, JFunc1<String, String> failMessage);
    void areEquals(JFunc<String> actual, String expected);
    // endregion

    // region contains
    void contains(String actual, String expected, String failMessage);
    void contains(String actual, String expected);
    void contains(String actual, List<String> expected, String failMessage);
    void contains(String actual, List<String> expected);
    void contains(JFunc<String> actual, String expected, JFunc1<String, String> failMessage);
    void contains(JFunc<String> actual, String expected);
    void contains(JFunc<String> actual, List<String> expected, JFunc1<String, String> failMessage);    void contains(JFunc<String> actual, List<String> expected);
    //endregion

    // region matches
    void matches(String actual, String regEx, String failMessage);
    void matches(String actual, String regEx);
    void matches(JFunc<String> actual, String regEx, JFunc1<String, String> failMessage);
    void matches(JFunc<String> actual, String regEx);
    // endregion

    // region isTrue
    void isTrue(Boolean condition, String failMessage);
    void isTrue(Boolean condition);
    void isTrue(JFunc<Boolean> condition, String failMessage);
    void isTrue(JFunc<Boolean> condition);
    // endregion

    // region isFalse
    void isFalse(Boolean condition, String failMessage);
    void isFalse(Boolean condition);
    void isFalse(JFunc<Boolean> condition, String failMessage);
    void isFalse(JFunc<Boolean> condition);
    // endregion

    // region Exceptions
    <E extends Exception> void throwException(String actionName, JAction action, Class<E> exceptionClass, String exceptionText);
    <E extends Exception> void throwException(JAction action, Class<E> exceptionClass, String exceptionText);
    void hasNoExceptions(String actionName, JAction action);
    void hasNoExceptions(JAction action);
    // endregion

    // region Objects
    void isEmpty(Object obj, String failMessage);
    default void isEmpty(Object obj) {
        isEmpty(obj, "Check that Object is empty");
    }
    void isNotEmpty(Object obj, String failMessage);
    default void isNotEmpty(Object obj) {
        isNotEmpty(obj, "Check that Object is NOT empty");
    }

    void isEmpty(JFunc<Object> obj, String failMessage);
    default void isEmpty(JFunc<Object> obj) {
        isEmpty(obj, "Check that Object is empty");
    }
    void isNotEmpty(JFunc<Object> obj, String failMessage);
    default void isNotEmpty(JFunc<Object> obj) {
        isNotEmpty(obj, "Check that Object is NOT empty");
    }
    // endregion

    // region Lists
    <T> void listEquals(Collection<T> actual, Collection<T> expected, String failMessage);
    <T> void listEquals(Collection<T> actual, Collection<T> expected);
    <T> void listContains(Collection<T> actual, Collection<T> expected, String failMessage);
    <T> void listContains(Collection<T> actual, Collection<T> expected);
    <T> void arrayEquals(T[] actual, T[] expected, String failMessage);
    <T> void arrayEquals(T[] actual, T[] expected);
    // endregion

    // TODO entityIncludeMapArray

    // region Sort Array Integer
    void isSortedByAsc(int[] array, String failMessage);
    void isSortedByAsc(int[] array);
    void isSortedByDesc(int[] array, String failMessage);
    void isSortedByDesc(int[] array);
    // endregion

    // ListProcessor
    <T> Check.ListChecker each(Collection<T> list);
    default <T> Check.ListChecker each(T[] array) {
        return each(asList(array));
    }
    
    
    default void throwException(String actionName, JAction action, String exceptionText) {
        throwException(action, null, exceptionText);
    }
    default <E extends Exception> void throwException(String actionName, JAction action, Class<E> exceptionClass) {
        throwException(action, exceptionClass, "");
    }
    
    default void throwException(JAction action, String exceptionText) {
        throwException(action, null, exceptionText);
    }
    
    default <E extends Exception> void throwException(JAction action, Class<E> exceptionClass) {
        throwException(action, exceptionClass, "");
    }

    default <T> void listContains(Collection<T> actual, T expected, String failMessage) { listContains(actual, asList(expected), failMessage);};

    default <T> void entitiesAreEquals(T actual, T expected, String failMessage) {
        listEquals(getFields(actual.getClass()), getFields(expected.getClass()), failMessage);
    }
    default <T> void entitiesAreEquals(T actual, T expected) {
        listEquals(getFields(actual.getClass()), getFields(expected.getClass()));
    }
}