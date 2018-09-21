package com.epam.matcher.base;

import com.epam.jdi.tools.Timer;
import com.epam.jdi.tools.func.JAction;
import com.epam.jdi.tools.func.JFunc;
import com.epam.jdi.tools.func.JFunc1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.epam.jdi.tools.LinqUtils.*;
import static com.epam.jdi.tools.LinqUtils.all;
import static com.epam.jdi.tools.PrintUtils.print;
import static com.epam.jdi.tools.ReflectionUtils.isInterface;
import static java.lang.String.format;
import static java.lang.reflect.Array.getLength;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.*;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

/**
 * Implements methods that are declared in interface.
 * Provides realisation of different verifications
 */
public class Check implements IChecker {
    private String checkMessage = "";
    public static int WAIT_TIMEOUT = 0;
    private MatcherSettings settings = new MatcherSettings();

    public Check() {
    }

    public Check(String checkMessage) {
        this.checkMessage = getCheckMessage(checkMessage);
    }

    /**
     * Converts the required message to  'Check that...' message
     *
     * @param checkMessage required message
     * @return String       'Check that..." message
     */
    private String getCheckMessage(String checkMessage) {
        if (isBlank(checkMessage))
            return "";
        String firstWord = checkMessage.split(" ")[0];
        return (!firstWord.equalsIgnoreCase("check") || firstWord.equalsIgnoreCase("verify"))
                ? "Check that " + checkMessage
                : checkMessage;
    }

    /**
     * Returns checker with ignoreCase = true
     *
     * @return IChecker
     */
    public IChecker ignoreCase() {
        settings.ignoreCase = true;
        return this;
    }

    /**
     * Returns checker with defined timeout
     *
     * @param timeout timeout to define
     * @return IChecker
     */
    public IChecker wait(int timeout) {
        settings.timeout = timeout;
        return this;
    }

    /**
     * Restore timeout to 0
     */
    public void restoreTimeout() {
        settings.timeout = WAIT_TIMEOUT;
    }

    /**
     * Converts string to UTF8 and to lower case if current checker has ignoreCase = true
     *
     * @param text string to set
     * @return String   converted string
     */
    private String setString(String text) {
        return toUtf8((settings.ignoreCase ? text.toLowerCase() : text));
    }

    /**
     * Converts a {@link String} to UTF8 by decoding it to array of bytes
     * using the specified {@linkplain java.nio.charset.Charset charset}.
     *
     * @param text text to convert
     * @return String   converted text
     */
    private String toUtf8(String text) {
        try {
            return new String(text.getBytes(), "UTF-8");
        } catch (Exception ignore) {
            return text;
        }
    }

    /**
     * Throws error with message formatted by specified format string and arguments.
     *
     * @param failMessage specified format string
     * @param args        arguments used in message
     */
    protected void assertException(String failMessage, Object... args) {
        throw new AssertionError(format(failMessage, args));
    }

    //todo javadoc
    private void assertAction(JFunc<Boolean> result, String message) {
        if (settings.timeout > 0) {
            if (!new Timer(settings.timeout).wait(result))
                assertException(message);
        } else if (!result.execute())
            assertException(message);
    }

    //todo javadoc
    private void assertAction(JFunc<Boolean> check, JFunc<String> message) {
        if (settings.timeout > 0) {
            if (!new Timer(settings.timeout).wait(check))
                assertException(message.execute());
        } else if (!check.execute())
            assertException(message.execute());
    }

    /**
     * Throws AssertionError if {@link boolean} is false
     *
     * @param result      the condition, false state calls assertException()
     * @param failMessage the assertion error message
     */
    private void assertAction(boolean result, String failMessage) {
        if (!result)
            assertException(failMessage);
    }

    /**
     * Composes the result message using {@link T} actual  and {@link T} expected params
     *
     * @param actual   actual result of checking
     * @param action   correlation between actual and expected (equals, contains, matches...")
     * @param expected expected result of checking
     * @param <T>
     * @return A formatted generified string
     */
    private static <T> String defaultBiCheckMsg(T actual, String action, T expected) {
        return format("Check that '%s' %s '%s'", actual, action, expected);
    }

    //todo javadoc
    private static <T> String defaultCheckMsg(String suffix) {
        return format("Check that result %s", suffix);
    }

    /**
     * Fails a test with the given message
     *
     * @param failMessage given message
     */
    public void fail(String failMessage) {
        assertException(failMessage);
    }

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
    public <T> void areEquals(T actual, T expected, String failMessage) {
        assertAction(actual.equals(expected), failMessage);
    }

    /**
     * Checks that two objects are equal. If they are not, an AssertionError is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     * @param <T>
     */
    public <T> void areEquals(T actual, T expected) {
        areEquals(actual, expected, defaultBiCheckMsg(actual, "equals", expected));
    }

    /**
     * Checks that two strings are equal. If they are not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      the actual string
     * @param expected    the expected string
     * @param failMessage the assertion error message
     */
    public void areEquals(String actual, String expected, String failMessage) {
        assertAction(setString(actual).equals(setString(expected)),
                defaultBiCheckMsg(actual, "equals", expected));
    }

    /**
     * Checks that two strings are equal. If they are not, an AssertionError is thrown.
     *
     * @param actual   the actual string
     * @param expected the expected string
     */
    public void areEquals(String actual, String expected) {
        areEquals(actual, expected, defaultBiCheckMsg(actual, "equals", expected));
    }

    /**
     * Checks that two objects are equal. If they are not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      the actual value
     * @param expected    the expected value
     * @param failMessage the assertion error message
     * @param <T>
     */
    public <T> void areEquals(JFunc<T> actual, T expected, JFunc1<T, String> failMessage) {
        assertAction(() -> actual.invoke().equals(expected), () -> failMessage.invoke(actual.invoke()));
    }

    /**
     * Checks that two objects are equal. If they are not, an AssertionError is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     * @param <T>
     */
    public <T> void areEquals(JFunc<T> actual, T expected) {
        areEquals(actual, expected, r -> defaultBiCheckMsg(r, "equals to", expected));
    }

    /**
     * Checks that two objects are equal. If they are not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      the actual value
     * @param expected    the expected value
     * @param failMessage the assertion error message
     */
    public void areEquals(JFunc<String> actual, String expected, JFunc1<String, String> failMessage) {
        assertAction(() -> setString(actual.invoke()).equals(setString(expected)),
                () -> failMessage.invoke(actual.invoke()));
    }

    /**
     * Checks that two objects are equal. If they are not, an AssertionError is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     */
    public void areEquals(JFunc<String> actual, String expected) {
        areEquals(actual, expected, r -> defaultBiCheckMsg(r, "equals to", expected));
    }
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
    public void contains(String actual, String expected, String failMessage) {
        assertAction(setString(actual).contains(setString(expected)), failMessage);
    }

    /**
     * Checks that actual sting contains expected. If not, an AssertionError is thrown.
     *
     * @param actual   the actual string
     * @param expected the expected string
     */
    public void contains(String actual, String expected) {
        contains(actual, expected, defaultBiCheckMsg(actual, "contains", expected));
    }

    /**
     * Checks that actual string contains all strings from list Expected. If not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      the actual string
     * @param expected    the expected string
     * @param failMessage the assertion error message
     */
    public void contains(String actual, List<String> expected, String failMessage) {
        for (String value : expected)
            contains(actual, value, failMessage);
    }

    /**
     * Checks that actual string contains all strings from list Expected.
     * If not, an AssertionError is thrown.
     *
     * @param actual   the actual string
     * @param expected the expected string
     */
    public void contains(String actual, List<String> expected) {
        for (String value : expected)
            contains(actual, value);
    }

    /**
     * Checks that actual string contains expected. If not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      the actual value
     * @param expected    the expected value
     * @param failMessage the assertion error message
     */
    public void contains(JFunc<String> actual, String expected, JFunc1<String, String> failMessage) {
        assertAction(() -> setString(actual.invoke()).contains(setString(expected)),
                () -> failMessage.invoke(actual.invoke()));
    }

    /**
     * Checks that actual string contains expected. If not, an AssertionError is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     */
    public void contains(JFunc<String> actual, String expected) {
        contains(actual, expected, r -> defaultBiCheckMsg(r, "contains", expected));
    }

    /**
     * Checks that actual string contains all strings from list Expected. If not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      the actual string
     * @param expected    the expected string
     * @param failMessage the assertion error message
     */
    public void contains(JFunc<String> actual, List<String> expected, JFunc1<String, String> failMessage) {
        assertAction(() -> {
            String actualValue = actual.invoke();
            for (String value : expected)
                if (!setString(actualValue).contains(setString(value)))
                    return false;
            return true;
        }, () -> failMessage.invoke(actual.invoke()));
    }

    /**
     * Checks that actual string contains all strings from list Expected.
     * If not, an AssertionError is thrown.
     *
     * @param actual   the actual string
     * @param expected the expected string
     */
    public void contains(JFunc<String> actual, List<String> expected) {
        contains(actual, expected, r -> defaultBiCheckMsg(r,
                "contains all of", print(expected)));
    }
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
    public void matches(String actual, String regEx, String failMessage) {
        assertAction(setString(actual).matches(setString(regEx)), failMessage);
    }

    /**
     * Checks whether or not this string matches the given. If not, an AssertionError is thrown.
     *
     * @param actual      the actual string
     * @param regEx       the regular expression to which string is to be matched
     */
    public void matches(String actual, String regEx) {
        contains(actual, regEx, defaultBiCheckMsg(actual, "matches", regEx));
    }

    /**
     * Checks whether or not this string matches the given. If not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      the actual string
     * @param regEx       the regular expression to which string is to be matched
     * @param failMessage the assertion error message
     */
    public void matches(JFunc<String> actual, String regEx, JFunc1<String, String> failMessage) {
        assertAction(() -> setString(actual.invoke()).matches(setString(regEx)),
                () -> failMessage.invoke(actual.invoke()));
    }

    /**
     * Checks whether or not this string matches the given. If not, an AssertionError is thrown.
     *
     * @param actual      the actual string
     * @param regEx       the regular expression to which string is to be matched
     */
    public void matches(JFunc<String> actual, String regEx) {
        contains(actual, regEx, r -> defaultBiCheckMsg(r, "matches", regEx));
    }
    // endregion

    // region isTrue

    /**
     * Checks that a condition is true. If it isn't, an AssertionError, with the given message, is
     * thrown.
     *
     * @param condition   the condition to evaluate
     * @param failMessage the assertion error message
     */
    public void isTrue(Boolean condition, String failMessage) {
        assertAction(condition, failMessage);
    }

    /**
     * Checks that a condition is true. If it isn't, an AssertionError is thrown.
     *
     * @param condition the condition to evaluate
     */
    public void isTrue(Boolean condition) {
        isTrue(condition, defaultCheckMsg("is true but false"));
    }

    /**
     * Checks that a condition is true. If it isn't, an AssertionError, with the given message, is
     *
     * @param condition   the condition to evaluate
     * @param failMessage the assertion error message
     */
    public void isTrue(JFunc<Boolean> condition, String failMessage) {
        assertAction(condition, failMessage);
    }

    /**
     * Checks that a condition is true. If it isn't, an AssertionError is thrown.
     *
     * @param condition the condition to evaluate
     */
    public void isTrue(JFunc<Boolean> condition) {
        isTrue(condition, defaultCheckMsg("is true but false"));
    }
    // endregion

    // region isFalse

    /**
     * Checks that a condition is false. If it isn't, an AssertionError, with the given message, is
     * thrown.
     *
     * @param condition   the condition to evaluate
     * @param failMessage the assertion error message
     */
    public void isFalse(Boolean condition, String failMessage) {
        assertAction(!condition, failMessage);
    }

    /**
     * Checks that a condition is false. If it isn't, an AssertionError is thrown.
     *
     * @param condition the condition to evaluate
     */
    public void isFalse(Boolean condition) {
        isFalse(condition, defaultCheckMsg("is false but true"));
    }

    /**
     * Checks that a condition is false. If it isn't, an AssertionError, with the given message, is
     * thrown.
     *
     * @param condition   the condition to evaluate
     * @param failMessage the assertion error message
     */
    public void isFalse(JFunc<Boolean> condition, String failMessage) {
        assertAction(() -> !condition.invoke(), failMessage);
    }

    /**
     * Checks that a condition is false. If it isn't, an AssertionError is thrown.
     *
     * @param condition the condition to evaluate
     */
    public void isFalse(JFunc<Boolean> condition) {
        isFalse(condition, defaultCheckMsg("is false but true"));
    }
    // endregion

    // region Exceptions
    /**
     * Utility method is used when actionName is not defined.
     * Returns checkMessage or "Action".
     *
     * @return String
     */
    private String getPrefix() {
        return isNotBlank(checkMessage) ? checkMessage : "Action";
    }

    //todo javadoc
    public <E extends Exception> void throwException(String actionName, JAction action, Class<E> exceptionClass, String exceptionText) {
        try {
            action.invoke();
        } catch (Exception | Error ex) {
            if (exceptionClass != null)
                areEquals(ex.getClass(), exceptionClass);
            if (exceptionText != null)
                areEquals(ex.getMessage(), exceptionText);
            return;
        }
        assertException("Action '%s' throws no exceptions. Expected (%s%s)",
                actionName,
                exceptionClass == null ? "" : exceptionClass.getName() + ", ",
                exceptionText);
    }

    //todo javadoc
    public <E extends Exception> void throwException(JAction action, Class<E> exceptionClass, String exceptionText) {
        throwException(getPrefix(), action, exceptionClass, exceptionText);
    }

    /**
     * Checks that required action has no exceptions
     *
     * @param actionName the name of requested action
     * @param action     the requested action
     */
    public void hasNoExceptions(String actionName, JAction action) {
        try {
            action.invoke();
        } catch (Exception | Error ex) {
            assertException(actionName + " throws exception: " + ex.getMessage());
        }
    }

    /**
     * Checks that required action has no exceptions
     *
     * @param action the requested action
     */
    public void hasNoExceptions(JAction action) {
        hasNoExceptions(getPrefix(), action);
    }
    // endregion

    // region Objects

    /**
     * Checks whether the object is empty or not.
     *
     * @param obj object to verify
     * @return boolean
     */
    private boolean isObjEmpty(Object obj) {
        if (obj == null)
            return true;
        if (obj instanceof String)
            return obj.toString().equals("");
        if (isInterface(obj.getClass(), Collection.class))
            return ((Collection) obj).isEmpty();
        return obj.getClass().isArray() && getLength(obj) == 0;
    }

    /**
     * Checks whether the object is empty. If NOT, an AssertionError,
     * with the given message, is thrown.
     *
     * @param obj         object to verify
     * @param failMessage the assertion error message
     */
    public void isEmpty(Object obj, String failMessage) {
        assertAction(isObjEmpty(obj), failMessage);
    }

    /**
     * Checks whether the object is empty. If NOT, an AssertionError is thrown.
     *
     * @param obj object to verify
     */
    public void isEmpty(Object obj) {
        isEmpty(obj, "Check that Object is empty");
    }

    /**
     * Checks whether the object is NOT empty. If empty, an AssertionError,
     * with the given message, is thrown.
     *
     * @param obj         object to verify
     * @param failMessage the assertion error message
     */
    public void isNotEmpty(Object obj, String failMessage) {
        assertAction(!isObjEmpty(obj), failMessage);
    }

    /**
     * Checks whether the object is NOT empty. If empty, an AssertionError is thrown.
     *
     * @param obj object to verify
     */
    public void isNotEmpty(Object obj) {
        isNotEmpty(obj, "Check that Object is NOT empty");
    }

    /**
     * Checks whether the object is empty. If NOT, an AssertionError,
     * with the given message, is thrown.
     *
     * @param obj         object to verify
     * @param failMessage the assertion error message
     */
    public void isEmpty(JFunc<Object> obj, String failMessage) {
        assertAction(() -> isObjEmpty(obj.invoke()), failMessage);
    }

    /**
     * Checks whether the object is empty. If NOT, an AssertionError is thrown.
     *
     * @param obj object to verify
     */
    public void isEmpty(JFunc<Object> obj) {
        isEmpty(obj, "Check that Object is empty");
    }

    /**
     * Checks whether the object is NOT empty. If empty, an AssertionError,
     * with the given message, is thrown.
     *
     * @param obj         object to verify
     * @param failMessage the assertion error message
     */
    public void isNotEmpty(JFunc<Object> obj, String failMessage) {
        assertAction(() -> !isObjEmpty(obj.invoke()), failMessage);
    }

    /**
     * Checks whether the object is NOT empty. If empty, an AssertionError is thrown.
     *
     * @param obj object to verify
     */
    public void isNotEmpty(JFunc<Object> obj) {
        isNotEmpty(obj, "Check that Object is NOT empty");
    }
    // endregion

    // region Lists

    /**
     * Checks if two collections of {@link T} are equals. If not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      actual collection of T
     * @param expected    expected collection of T
     * @param failMessage the assertion error message
     * @param <T>
     */
    public <T> void listEquals(Collection<T> actual, Collection<T> expected, String failMessage) {
        if (actual != null && expected != null && actual.size() != expected.size()) {
            assertException(failMessage);
            return;
        }
        listContains(actual, expected, failMessage);
    }

    /**
     * Checks if two collections of {@link T} are equals. If not, an AssertionError is thrown.
     *
     * @param actual   actual collection of T
     * @param expected expected collection of T
     * @param <T>
     */
    public <T> void listEquals(Collection<T> actual, Collection<T> expected) {
        if (actual != null && expected != null && actual.size() != expected.size()) {
            assertException("Collections are not equals because has different sizes. " +
                            "%s!=%s (Actual{%s}; Expected{%s})", actual.size(), expected.size(),
                    print(map(actual, Object::toString)), print(map(expected, Object::toString)));
            return;
        }
        listContains(actual, expected);
    }

    /**
     * Checks that actual collection contains expected. If not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      actual collection of T
     * @param expected    expected collection of T
     * @param failMessage the assertion error message
     * @param <T>
     */
    public <T> void listContains(Collection<T> actual, Collection<T> expected, String failMessage) {
        if (actual == null || expected == null || actual.isEmpty() || expected.isEmpty()) {
            assertException(failMessage);
            return;
        }
        assertAction(any(expected, el -> !actual.contains(el)), failMessage);
    }

    /**
     * Checks that actual collection contains expected. If not, an AssertionError is thrown.
     *
     * @param actual      actual collection of T
     * @param expected    expected collection of T
     * @param <T>
     */
    public <T> void listContains(Collection<T> actual, Collection<T> expected) {
        if (actual == null || expected == null || actual.isEmpty() || expected.isEmpty()) {
            assertException("Actual or expected list is empty");
            return;
        }
        List<String> notEquals = new ArrayList<>();
        for (T el : expected)
            if (!actual.contains(el))
                notEquals.add(el.toString());
        assertAction(notEquals.isEmpty(),
                format("Not all Expected elements are in Actual: {%s}", print(notEquals)));
    }

    /**
     * Checks that two arrays are equals. If not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param actual      actual array
     * @param expected    expected array
     * @param failMessage the assertion error message
     * @param <T>
     */
    public <T> void arrayEquals(T[] actual, T[] expected, String failMessage) {
        if (actual == null || expected == null || actual.length == 0 || expected.length == 0) {
            assertException(failMessage);
            return;
        }
        if (actual.length != expected.length) {
            assertException(failMessage);
            return;
        }
        for (int i = 0; i < expected.length; i++)
            if (!actual[i].equals(expected[i])) {
                assertException(failMessage);
                return;
            }
    }

    /**
     * Checks that two arrays are equals. If not, an AssertionError is thrown.
     *
     * @param actual   actual array
     * @param expected expected array
     * @param <T>
     */
    public <T> void arrayEquals(T[] actual, T[] expected) {
        if (actual == null || expected == null || actual.length == 0 || expected.length == 0) {
            assertException("Actual or expected array is empty");
            return;
        }
        if (actual.length != expected.length) {
            assertException("Arrays are not equals because has different sizes. " +
                            "%s!=%s (Actual{%s}; Expected{%s})", actual.length, expected.length,
                    print(map(actual, Object::toString)), print(map(expected, Object::toString)));
            return;
        }
        List<String> notEquals = new ArrayList<>();
        for (int i = 0; i < expected.length; i++)
            if (!actual[i].equals(expected[i]))
                notEquals.add(format("%s: Actual(%s) != Expected(%s)",
                        i, actual[i], expected[i]));
        assertAction(notEquals.isEmpty(),
                format("Following elements are not equal: {%s}", print(notEquals)));
    }
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
    public void isSortedByAsc(int[] array, String failMessage) {
        if (array == null || array.length == 0) {
            assertException(failMessage);
            return;
        }
        for (int i = 1; i < array.length; i++)
            if (array[i - 1] > array[i])
                assertException(failMessage);
    }

    /**
     * Check that array is sorted ascending. If not, an AssertionError is thrown.
     *
     * @param array array to check sorting order
     */
    public void isSortedByAsc(int[] array) {
        if (array == null || array.length == 0) {
            assertException("Array have no elements or null");
            return;
        }
        for (int i = 1; i < array.length; i++)
            if (array[i - 1] > array[i])
                assertException("Array not sorted by ascending. a[%s](%s) > a[%s](%s)",
                        i - 1, array[i - 1], i, array[i]);
    }

    /**
     * Check that array is sorted descending. If not, an AssertionError,
     * with the given message, is thrown.
     *
     * @param array       array to check sorting order
     * @param failMessage the assertion error message
     */
    public void isSortedByDesc(int[] array, String failMessage) {
        if (array == null || array.length == 0) {
            assertException(failMessage);
            return;
        }
        for (int i = 1; i < array.length; i++)
            if (array[i - 1] < array[i])
                assertException(failMessage);
    }

    /**
     * Check that array is sorted descending. If not, an AssertionError is thrown.
     *
     * @param array array to check sorting order
     */
    public void isSortedByDesc(int[] array) {
        if (array == null || array.length == 0) {
            assertException("Array have no elements or null");
            return;
        }
        for (int i = 1; i < array.length; i++)
            if (array[i - 1] < array[i])
                assertException("Array not sorted by descending. a[%s](%s) < a[%s](%s)",
                        i - 1, array[i - 1], i, array[i]);
    }
    // endregion

    // ListProcessor
    public <T> Check.ListChecker each(Collection<T> list) {
        return new Check.ListChecker<>(list);
    }

    public <T> Check.ListChecker each(T[] array) {
        return each(asList(array));
    }

    public final class ListChecker<T> {
        Collection<T> list;

        private ListChecker(Collection<T> list) {
            if (list == null || list.isEmpty())
                assertException("List %s is empty", print(select(list, Object::toString)));
            this.list = list;
        }

        public void areEquals(T expected, String failMessage) {
            assertAction(all(list, el -> el.equals(expected)), failMessage);
        }

        public void areEquals(T expected) {
            List<String> notEquals = new ArrayList<>();
            for (Object el : list)
                if (!el.equals(expected))
                    notEquals.add(el.toString());
            assertAction(notEquals.isEmpty(), format("Elements {%s} are not equal to %s",
                    print(notEquals), expected));
        }

        public void areContains(String expected, String failMessage) {
            assertAction(all(list, el -> el.toString().contains(expected)), failMessage);
        }

        public void areContains(String expected) {
            List<String> notContains = new ArrayList<>();
            for (Object el : list)
                if (!el.toString().contains(expected))
                    notContains.add(el.toString());
            assertAction(notContains.isEmpty(), format("Elements {%s} are not contains %s",
                    print(notContains), expected));
        }

        public void areMatches(String regEx, String failMessage) {
            assertAction(all(list, el -> el.toString().matches(regEx)), failMessage);
        }

        public void areMatches(String regEx) {
            List<String> notMatch = new ArrayList<>();
            for (Object el : list)
                if (!el.toString().matches(regEx))
                    notMatch.add(el.toString());
            assertAction(notMatch.isEmpty(), format("Elements {%s} are not match to %s",
                    print(notMatch), regEx));
        }

    }

}