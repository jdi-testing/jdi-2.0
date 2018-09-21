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

/**
 * Interface declares methods that are used for checking (contains, equals, empty...)
 */
public interface IChecker {
    IChecker ignoreCase();

    IChecker wait(int timeout);

    /**
     * Restore timeout to 0
     */
    void restoreTimeout();

    /**
     * Fails a test with the given message
     *
     * @param failMessage given message
     */
    void fail(String failMessage);
    // region areEquals

    /**
     * Checks that two objects are equal. If they are not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      the actual value
     * @param expected    the expected value
     * @param failMessage the assertion error message
     * @param <T>
     */
    <T> void areEquals(T actual, T expected, String failMessage);

    /**
     * Checks that two objects are equal. If they are not, an AssertionError is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     * @param <T>
     */
    <T> void areEquals(T actual, T expected);

    /**
     * Checks that two strings are equal. If they are not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      the actual string
     * @param expected    the expected string
     * @param failMessage the assertion error message
     */
    void areEquals(String actual, String expected, String failMessage);

    /**
     * Checks that two strings are equal. If they are not, an AssertionError is thrown.
     *
     * @param actual   the actual string
     * @param expected the expected string
     */
    void areEquals(String actual, String expected);

    /**
     * Checks that two objects are equal. If they are not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      the actual value
     * @param expected    the expected value
     * @param failMessage the assertion error message
     * @param <T>
     */
    <T> void areEquals(JFunc<T> actual, T expected, JFunc1<T, String> failMessage);

    /**
     * Checks that two objects are equal. If they are not, an AssertionError is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     * @param <T>
     */
    <T> void areEquals(JFunc<T> actual, T expected);

    /**
     * Checks that two objects are equal. If they are not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      the actual value
     * @param expected    the expected value
     * @param failMessage the assertion error message
     */
    void areEquals(JFunc<String> actual, String expected, JFunc1<String, String> failMessage);

    /**
     * Checks that two objects are equal. If they are not, an AssertionError is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     */
    void areEquals(JFunc<String> actual, String expected);
    // endregion

    // region contains

    /**
     * Checks that actual sting contains expected. If not, an AssertionError,
     * with the given message, is thrown. Strings are formatted.
     *
     * @param actual      the actual string
     * @param expected    the expected string
     * @param failMessage the assertion error message
     */
    void contains(String actual, String expected, String failMessage);

    /**
     * Checks that actual sting contains expected. If not, an AssertionError is thrown.
     *
     * @param actual   the actual string
     * @param expected the expected string
     */
    void contains(String actual, String expected);

    /**
     * Checks that actual string contains all strings from list Expected. If not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      the actual string
     * @param expected    the expected string
     * @param failMessage the assertion error message
     */
    void contains(String actual, List<String> expected, String failMessage);

    /**
     * Checks that actual string contains all strings from list Expected.
     * If not, an AssertionError is thrown.
     *
     * @param actual   the actual string
     * @param expected the expected string
     */
    void contains(String actual, List<String> expected);

    /**
     * Checks that actual string contains expected. If not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      the actual value
     * @param expected    the expected value
     * @param failMessage the assertion error message
     */
    void contains(JFunc<String> actual, String expected, JFunc1<String, String> failMessage);

    /**
     * Checks that actual string contains expected. If not, an AssertionError is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     */
    void contains(JFunc<String> actual, String expected);

    /**
     * Checks that actual string contains all strings from list Expected. If not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      the actual string
     * @param expected    the expected string
     * @param failMessage the assertion error message
     */
    void contains(JFunc<String> actual, List<String> expected, JFunc1<String, String> failMessage);

    /**
     * Checks that actual string contains all strings from list Expected.
     * If not, an AssertionError is thrown.
     *
     * @param actual   the actual string
     * @param expected the expected string
     */
    void contains(JFunc<String> actual, List<String> expected);
    //endregion

    // region matches

    /**
     * Checks whether or not this string matches the given. If not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      the actual string
     * @param regEx       the regular expression to which string is to be matched
     * @param failMessage the assertion error message
     */
    void matches(String actual, String regEx, String failMessage);

    void matches(String actual, String regEx);

    /**
     * Checks whether or not this string matches the given. If not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      the actual string
     * @param regEx       the regular expression to which string is to be matched
     * @param failMessage the assertion error message
     */
    void matches(JFunc<String> actual, String regEx, JFunc1<String, String> failMessage);

    void matches(JFunc<String> actual, String regEx);
    // endregion

    // region isTrue

    /**
     * Checks that a condition is true. If it isn't, an AssertionError, with the given message, is
     * thrown.
     *
     * @param condition   the condition to evaluate
     * @param failMessage the assertion error message
     */
    void isTrue(Boolean condition, String failMessage);

    /**
     * Checks that a condition is true. If it isn't, an AssertionError is thrown.
     *
     * @param condition the condition to evaluate
     */
    void isTrue(Boolean condition);

    /**
     * Checks that a condition is true. If it isn't, an AssertionError, with the given message, is
     *
     * @param condition   the condition to evaluate
     * @param failMessage the assertion error message
     */

    void isTrue(JFunc<Boolean> condition, String failMessage);

    /**
     * Checks that a condition is true. If it isn't, an AssertionError is thrown.
     *
     * @param condition the condition to evaluate
     */
    void isTrue(JFunc<Boolean> condition);
    // endregion

    // region isFalse

    /**
     * Checks that a condition is false. If it isn't, an AssertionError, with the given message, is
     * thrown.
     *
     * @param condition   the condition to evaluate
     * @param failMessage the assertion error message
     */
    void isFalse(Boolean condition, String failMessage);

    /**
     * Checks that a condition is false. If it isn't, an AssertionError is thrown.
     *
     * @param condition the condition to evaluate
     */
    void isFalse(Boolean condition);

    /**
     * Checks that a condition is false. If it isn't, an AssertionError, with the given message, is
     * thrown.
     *
     * @param condition   the condition to evaluate
     * @param failMessage the assertion error message
     */
    void isFalse(JFunc<Boolean> condition, String failMessage);

    /**
     * Checks that a condition is false. If it isn't, an AssertionError is thrown.
     *
     * @param condition the condition to evaluate
     */
    void isFalse(JFunc<Boolean> condition);
    // endregion

    // region Exceptions
    <E extends Exception> void throwException(String actionName, JAction action, Class<E> exceptionClass, String exceptionText);

    <E extends Exception> void throwException(JAction action, Class<E> exceptionClass, String exceptionText);

    /**
     * Checks that required action has no exceptions
     *
     * @param actionName the name of requested action
     * @param action     the requested action
     */
    void hasNoExceptions(String actionName, JAction action);

    /**
     * Checks that required action has no exceptions
     *
     * @param action the requested action
     */
    void hasNoExceptions(JAction action);
    // endregion

    // region Objects

    /**
     * Checks whether the object is empty. If NOT, an AssertionError,
     * with the given message, is thrown.
     *
     * @param obj         object to verify
     * @param failMessage the assertion error message
     */
    void isEmpty(Object obj, String failMessage);

    /**
     * Checks whether the object is empty. If NOT, an AssertionError is thrown.
     *
     * @param obj object to verify
     */
    default void isEmpty(Object obj) {
        isEmpty(obj, "Check that Object is empty");
    }

    /**
     * Checks whether the object is NOT empty. If empty, an AssertionError,
     * with the given message, is thrown.
     *
     * @param obj         object to verify
     * @param failMessage the assertion error message
     */
    void isNotEmpty(Object obj, String failMessage);

    /**
     * Checks whether the object is NOT empty. If empty, an AssertionError is thrown.
     *
     * @param obj object to verify
     */
    default void isNotEmpty(Object obj) {
        isNotEmpty(obj, "Check that Object is NOT empty");
    }

    /**
     * Checks whether the object is empty. If NOT, an AssertionError,
     * with the given message, is thrown.
     *
     * @param obj         object to verify
     * @param failMessage the assertion error message
     */
    void isEmpty(JFunc<Object> obj, String failMessage);

    /**
     * Checks whether the object is empty. If NOT, an AssertionError is thrown.
     *
     * @param obj object to verify
     */
    default void isEmpty(JFunc<Object> obj) {
        isEmpty(obj, "Check that Object is empty");
    }

    /**
     * Checks whether the object is NOT empty. If empty, an AssertionError,
     * with the given message, is thrown.
     *
     * @param obj         object to verify
     * @param failMessage the assertion error message
     */
    void isNotEmpty(JFunc<Object> obj, String failMessage);

    /**
     * Checks whether the object is NOT empty. If empty, an AssertionError is thrown.
     *
     * @param obj object to verify
     */
    default void isNotEmpty(JFunc<Object> obj) {
        isNotEmpty(obj, "Check that Object is NOT empty");
    }
    // endregion

    // region Lists

    /**
     * Checks if two collections of {@link T} are equals. If not, an AssertionError,
     * with the given message, is thrown.
     * @param actual        actual collection of T
     * @param expected      expected collection of T
     * @param failMessage   the assertion error message
     * @param <T>
     */
    <T> void listEquals(Collection<T> actual, Collection<T> expected, String failMessage);

    /**
     * Checks if two collections of {@link T} are equals. If not, an AssertionError is thrown.
     * @param actual        actual collection of T
     * @param expected      expected collection of T
     * @param <T>
     */
    <T> void listEquals(Collection<T> actual, Collection<T> expected);

    /**
     *
     * @param actual        actual collection of T
     * @param expected      expected collection of T
     * @param failMessage   the assertion error message
     * @param <T>
     */
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

    default <T> void listContains(Collection<T> actual, T expected, String failMessage) {
        listContains(actual, asList(expected), failMessage);
    }

    default <T> void entitiesAreEquals(T actual, T expected, String failMessage) {
        listEquals(getFields(actual.getClass()), getFields(expected.getClass()), failMessage);
    }

    default <T> void entitiesAreEquals(T actual, T expected) {
        listEquals(getFields(actual.getClass()), getFields(expected.getClass()));
    }
}