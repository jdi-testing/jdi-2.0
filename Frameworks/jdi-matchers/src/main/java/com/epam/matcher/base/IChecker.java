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
    /**
     * Returns checker with ignoreCase = true
     *
     * @return IChecker
     */
    IChecker ignoreCase();

    /**
     * Returns checker with defined timeout
     *
     * @param timeout timeout to define
     * @return IChecker
     */
    IChecker wait(int timeout);

    /**
     * Restore timeout to 0
     */
    void restoreTimeout();

    /**
     * Fails a test with the given message
     *
     * @param failMessage the assertion error message
     */
    void fail(String failMessage);
    // region areEquals

    /**
     * Asserts that two objects are equal. If they are not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      the actual value
     * @param expected    the expected value
     * @param failMessage the assertion error message
     * @param <T>
     */
    <T> void areEquals(T actual, T expected, String failMessage);

    /**
     * Asserts that two objects are equal. If they are not, an AssertionError is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     * @param <T>      class of objects
     */
    <T> void areEquals(T actual, T expected);

    /**
     * Asserts that two strings are equal. If they are not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      the actual string
     * @param expected    the expected string
     * @param failMessage the assertion error message
     */
    void areEquals(String actual, String expected, String failMessage);

    /**
     * Asserts that two strings are equal. If they are not, an AssertionError is thrown.
     *
     * @param actual   the actual string
     * @param expected the expected string
     */
    void areEquals(String actual, String expected);

    /**
     * Asserts that two objects are equal. If they are not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      the actual value
     * @param expected    the expected value
     * @param failMessage the assertion error message
     * @param <T>
     */
    <T> void areEquals(JFunc<T> actual, T expected, JFunc1<T, String> failMessage);

    /**
     * Asserts that two objects are equal. If they are not, an AssertionError is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     * @param <T>
     */
    <T> void areEquals(JFunc<T> actual, T expected);

    /**
     * Asserts that two objects are equal. If they are not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      the actual value
     * @param expected    the expected value
     * @param failMessage the assertion error message
     */
    void areEquals(JFunc<String> actual, String expected, JFunc1<String, String> failMessage);

    /**
     * Asserts that two objects are equal. If they are not, an AssertionError is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     */
    void areEquals(JFunc<String> actual, String expected);
    // endregion

    // region contains

    /**
     * Asserts that actual sting contains expected. If not, an AssertionError,
     * with the given message, is thrown. Strings are formatted.
     *
     * @param actual      the actual string
     * @param expected    the expected string
     * @param failMessage the assertion error message
     */
    void contains(String actual, String expected, String failMessage);

    /**
     * Asserts that actual sting contains expected. If not, an AssertionError is thrown.
     *
     * @param actual   the actual string
     * @param expected the expected string
     */
    void contains(String actual, String expected);

    /**
     * Asserts that actual string contains all strings from list Expected. If not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      the actual string
     * @param expected    the expected string
     * @param failMessage the assertion error message
     */
    void contains(String actual, List<String> expected, String failMessage);

    /**
     * Asserts that actual string contains all strings from list Expected.
     * If not, an AssertionError is thrown.
     *
     * @param actual   the actual string
     * @param expected the expected string
     */
    void contains(String actual, List<String> expected);

    /**
     * Asserts that actual string contains expected. If not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      the actual value
     * @param expected    the expected value
     * @param failMessage the assertion error message
     */
    void contains(JFunc<String> actual, String expected, JFunc1<String, String> failMessage);

    /**
     * Asserts that actual string contains expected. If not, an AssertionError is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     */
    void contains(JFunc<String> actual, String expected);

    /**
     * Asserts that actual string contains all strings from list Expected. If not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      the actual string
     * @param expected    the expected string
     * @param failMessage the assertion error message
     */
    void contains(JFunc<String> actual, List<String> expected, JFunc1<String, String> failMessage);

    /**
     * Asserts that actual string contains all strings from list Expected.
     * If not, an AssertionError is thrown.
     *
     * @param actual   the actual string
     * @param expected the expected string
     */
    void contains(JFunc<String> actual, List<String> expected);
    //endregion

    // region matches

    /**
     * Asserts whether or not this string matches the given. If not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      the actual string
     * @param regEx       the regular expression to which string is to be matched
     * @param failMessage the assertion error message
     */
    void matches(String actual, String regEx, String failMessage);

    void matches(String actual, String regEx);

    /**
     * Asserts whether or not this string matches the given. If not, an AssertionError,
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
     * Asserts that a condition is true. If it isn't, an AssertionError, with the given message, is
     * thrown.
     *
     * @param condition   the condition to evaluate
     * @param failMessage the assertion error message
     */
    void isTrue(Boolean condition, String failMessage);

    /**
     * Asserts that a condition is true. If it isn't, an AssertionError is thrown.
     *
     * @param condition the condition to evaluate
     */
    void isTrue(Boolean condition);

    /**
     * Asserts that a condition is true. If it isn't, an AssertionError, with the given message, is
     *
     * @param condition   the condition to evaluate
     * @param failMessage the assertion error message
     */

    void isTrue(JFunc<Boolean> condition, String failMessage);

    /**
     * Asserts that a condition is true. If it isn't, an AssertionError is thrown.
     *
     * @param condition the condition to evaluate
     */
    void isTrue(JFunc<Boolean> condition);
    // endregion

    // region isFalse

    /**
     * Asserts that a condition is false. If it isn't, an AssertionError, with the given message, is
     * thrown.
     *
     * @param condition   the condition to evaluate
     * @param failMessage the assertion error message
     */
    void isFalse(Boolean condition, String failMessage);

    /**
     * Asserts that a condition is false. If it isn't, an AssertionError is thrown.
     *
     * @param condition the condition to evaluate
     */
    void isFalse(Boolean condition);

    /**
     * Asserts that a condition is false. If it isn't, an AssertionError, with the given message, is
     * thrown.
     *
     * @param condition   the condition to evaluate
     * @param failMessage the assertion error message
     */
    void isFalse(JFunc<Boolean> condition, String failMessage);

    /**
     * Asserts that a condition is false. If it isn't, an AssertionError is thrown.
     *
     * @param condition the condition to evaluate
     */
    void isFalse(JFunc<Boolean> condition);
    // endregion

    // region Exceptions

    /**
     * Asserts if method throws required exception. If not, an AssertionError is thrown.
     *
     * @param actionName     name of action to catch required exception
     * @param action         action to catch required exception
     * @param exceptionClass required exception class
     * @param exceptionText  required exception text
     * @param <E>
     */
    <E extends Exception> void throwException(String actionName, JAction action, Class<E> exceptionClass, String exceptionText);

    /**
     * Asserts if method throws required exception. If not, an AssertionError is thrown.
     *
     * @param action         action to catch required exception
     * @param exceptionClass required exception class
     * @param exceptionText  required exception text
     * @param <E>
     */
    <E extends Exception> void throwException(JAction action, Class<E> exceptionClass, String exceptionText);

    /**
     * Asserts that required action has no exceptions
     *
     * @param actionName the name of requested action
     * @param action     the requested action
     */
    void hasNoExceptions(String actionName, JAction action);

    /**
     * Asserts that required action has no exceptions
     *
     * @param action the requested action
     */
    void hasNoExceptions(JAction action);
    // endregion

    // region Objects

    /**
     * Asserts whether the object is empty. If NOT, an AssertionError,
     * with the given message, is thrown.
     *
     * @param obj         object to verify
     * @param failMessage the assertion error message
     */
    void isEmpty(Object obj, String failMessage);

    /**
     * Asserts whether the object is empty. If NOT, an AssertionError is thrown.
     *
     * @param obj object to verify
     */
    default void isEmpty(Object obj) {
        isEmpty(obj, "Check that Object is empty");
    }

    /**
     * Asserts whether the object is NOT empty. If empty, an AssertionError,
     * with the given message, is thrown.
     *
     * @param obj         object to verify
     * @param failMessage the assertion error message
     */
    void isNotEmpty(Object obj, String failMessage);

    /**
     * Asserts whether the object is NOT empty. If empty, an AssertionError is thrown.
     *
     * @param obj object to verify
     */
    default void isNotEmpty(Object obj) {
        isNotEmpty(obj, "Check that Object is NOT empty");
    }

    /**
     * Asserts whether the object is empty. If NOT, an AssertionError,
     * with the given message, is thrown.
     *
     * @param obj         object to verify
     * @param failMessage the assertion error message
     */
    void isEmpty(JFunc<Object> obj, String failMessage);

    /**
     * Asserts whether the object is empty. If NOT, an AssertionError is thrown.
     *
     * @param obj object to verify
     */
    default void isEmpty(JFunc<Object> obj) {
        isEmpty(obj, "Check that Object is empty");
    }

    /**
     * Asserts whether the object is NOT empty. If empty, an AssertionError,
     * with the given message, is thrown.
     *
     * @param obj         object to verify
     * @param failMessage the assertion error message
     */
    void isNotEmpty(JFunc<Object> obj, String failMessage);

    /**
     * Asserts whether the object is NOT empty. If empty, an AssertionError is thrown.
     *
     * @param obj object to verify
     */
    default void isNotEmpty(JFunc<Object> obj) {
        isNotEmpty(obj, "Check that Object is NOT empty");
    }
    // endregion

    // region Lists

    /**
     * Asserts if two collections of {@link T} are equals. If not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      actual collection of T
     * @param expected    expected collection of T
     * @param failMessage the assertion error message
     * @param <T>
     */
    <T> void listEquals(Collection<T> actual, Collection<T> expected, String failMessage);

    /**
     * Asserts if two collections of {@link T} are equals. If not, an AssertionError is thrown.
     *
     * @param actual   actual collection of T
     * @param expected expected collection of T
     * @param <T>
     */
    <T> void listEquals(Collection<T> actual, Collection<T> expected);

    /**
     * Asserts that actual collection contains expected. If not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      actual collection of T
     * @param expected    expected collection of T
     * @param failMessage the assertion error message
     * @param <T>
     */
    <T> void listContains(Collection<T> actual, Collection<T> expected, String failMessage);

    /**
     * Asserts that actual collection contains expected. If not, an AssertionError is thrown.
     *
     * @param actual   actual collection of T
     * @param expected expected collection of T
     * @param <T>
     */
    <T> void listContains(Collection<T> actual, Collection<T> expected);

    /**
     * Asserts that two arrays are equals. If not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      actual array
     * @param expected    expected array
     * @param failMessage the assertion error message
     * @param <T>
     */
    <T> void arrayEquals(T[] actual, T[] expected, String failMessage);

    /**
     * Asserts that two arrays are equals. If not, an AssertionError is thrown.
     *
     * @param actual   actual array
     * @param expected expected array
     * @param <T>
     */
    <T> void arrayEquals(T[] actual, T[] expected);
    // endregion

    // TODO entityIncludeMapArray

    // region Sort Array Integer

    /**
     * Check that array is sorted ascending. If not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param array       array to check sorting order
     * @param failMessage the assertion error message
     */
    void isSortedByAsc(int[] array, String failMessage);

    /**
     * Check that array is sorted ascending. If not, an AssertionError is thrown.
     *
     * @param array array to check sorting order
     */
    void isSortedByAsc(int[] array);

    /**
     * Check that array is sorted descending. If not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param array       array to check sorting order
     * @param failMessage the assertion error message
     */
    void isSortedByDesc(int[] array, String failMessage);

    /**
     * Check that array is sorted descending. If not, an AssertionError is thrown.
     *
     * @param array array to check sorting order
     */
    void isSortedByDesc(int[] array);
    // endregion

    // ListProcessor

    /**
     * Creates exemplar of ListChecker with collection for assertions
     * @param list  collection that need assertions
     * @param <T>   class of objects in collection
     * @return      ListChecker
     */
    <T> Check.ListChecker each(Collection<T> list);

    /**
     * Creates exemplar of ListChecker with collection for assertions
     * @param array array that need assertions
     * @param <T>   class of objects in collection
     * @return      ListChecker
     */
    default <T> Check.ListChecker each(T[] array) {
        return each(asList(array));
    }

    /**
     * Asserts if method throws exception with required text. If not, an AssertionError is thrown.
     *
     * @param actionName    name of action to catch required exception
     * @param action        action to catch required exception
     * @param exceptionText required exception text
     */
    default void throwException(String actionName, JAction action, String exceptionText) {
        throwException(actionName, action, null, exceptionText);
    }

    /**
     * Asserts if method throws exception with required class. If not, an AssertionError is thrown.
     *
     * @param actionName     name of action to catch required exception
     * @param action         action to catch required exception
     * @param exceptionClass required exception class
     * @param <E>
     */
    default <E extends Exception> void throwException(String actionName, JAction action, Class<E> exceptionClass) {
        throwException(actionName, action, exceptionClass, "");
    }

    /**
     * Asserts if method throws exception with required text. If not, an AssertionError is thrown.
     *
     * @param action        action to catch required exception
     * @param exceptionText required exception text
     */
    default void throwException(JAction action, String exceptionText) {
        throwException(action, null, exceptionText);
    }

    /**
     * Asserts if method throws exception with required class. If not, an AssertionError is thrown.
     *
     * @param action         action to catch required exception
     * @param exceptionClass required exception class
     * @param <E>
     */
    default <E extends Exception> void throwException(JAction action, Class<E> exceptionClass) {
        throwException(action, exceptionClass, "");
    }

    /**
     * Asserts that actual collection contains expected object. If not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      actual collection of T
     * @param expected    expected object T
     * @param failMessage the assertion error message
     * @param <T>
     */
    default <T> void listContains(Collection<T> actual, T expected, String failMessage) {
        listContains(actual, asList(expected), failMessage);
    }

    /**
     * Asserts if two collections of classes of {@link T} are equals. If not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      actual collection of classes of T
     * @param expected    expected collection of classes of T
     * @param failMessage the assertion error message
     * @param <T>
     */
    default <T> void entitiesAreEquals(T actual, T expected, String failMessage) {
        listEquals(getFields(actual.getClass()), getFields(expected.getClass()), failMessage);
    }

    /**
     * Asserts if two collections of classes of {@link T} are equals.
     * If not, an AssertionError is thrown.
     *
     * @param actual   actual collection of classes of T
     * @param expected expected collection of classes of T
     * @param <T>
     */
    default <T> void entitiesAreEquals(T actual, T expected) {
        listEquals(getFields(actual.getClass()), getFields(expected.getClass()));
    }
}