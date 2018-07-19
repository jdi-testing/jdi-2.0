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
import static org.apache.logging.log4j.util.Strings.isBlank;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

public class Check implements IChecker {
    private String checkMessage = "";

    public Check() {
    }

    public Check(String checkMessage) {
        this.checkMessage = getCheckMessage(checkMessage);
    }
    private String getCheckMessage(String checkMessage) {
        if (isBlank(checkMessage))
            return "";
        String firstWord = checkMessage.split(" ")[0];
        return (!firstWord.equalsIgnoreCase("check") || firstWord.equalsIgnoreCase("verify"))
                ? "Check that " + checkMessage
                : checkMessage;
    }
    private MatcherSettings settings = new MatcherSettings();
    public IChecker ignoreCase() {
        settings.ignoreCase = true;
        return this;
    }
    public IChecker wait(int timeout) {
        settings.timeout = timeout;
        return this;
    }

    public static int WAIT_TIMEOUT = 0;
    public void restoreTimeout() { settings.timeout = WAIT_TIMEOUT; }

    private String setString(String text) {
        return toUtf8((settings.ignoreCase ? text.toLowerCase() : text));
    }

    private String toUtf8(String text) {
        try {
            return new String(text.getBytes(), "UTF-8");
        } catch (Exception ignore) { return text; }
    }

    protected void assertException(String failMessage, Object... args) {
        throw new AssertionError(format(failMessage, args));
    }
    private void assertAction(JFunc<Boolean> result, String message) {
        if (settings.timeout > 0) {
            if (!new Timer(settings.timeout).wait(result))
                assertException(message);
        } else if (!result.execute())
            assertException(message);
    }
    private void assertAction(JFunc<Boolean> check, JFunc<String> message) {
        if (settings.timeout > 0) {
            if (!new Timer(settings.timeout).wait(check))
                assertException(message.execute());
        } else if (!check.execute())
            assertException(message.execute());
    }
    private void assertAction(boolean result, String failMessage) {
        if (!result)
            assertException(failMessage);
    }
    private static <T> String defaultBiCheckMsg(T actual, String action, T expected) {
        return format("Check that '%s' %s '%s'", actual, action, expected);
    }
    private static <T> String defaultCheckMsg(String suffix) {
        return format("Check that result %s", suffix);
    }

    public void fail(String failMessage) {
        assertException(failMessage);
    }
    // region areEquals
    public <T> void areEquals(T actual, T expected, String failMessage) {
        assertAction(actual.equals(expected), failMessage);
    }

    public <T> void areEquals(T actual, T expected) {
        areEquals(actual, expected, defaultBiCheckMsg(actual, "equals", expected));
    }
    public void areEquals(String actual, String expected, String failMessage) {
        assertAction(setString(actual).equals(setString(expected)),
                defaultBiCheckMsg(actual, "equals", expected));
    }
    public void areEquals(String actual, String expected) {
        areEquals(actual, expected, defaultBiCheckMsg(actual, "equals", expected));
    }
    public <T> void areEquals(JFunc<T> actual, T expected, JFunc1<T, String> failMessage) {
        assertAction(() -> actual.invoke().equals(expected), () -> failMessage.invoke(actual.invoke()));
    }
    public <T> void areEquals(JFunc<T> actual, T expected) {
        areEquals(actual, expected, r -> defaultBiCheckMsg(r, "equals to", expected));
    }
    public void areEquals(JFunc<String> actual, String expected, JFunc1<String, String> failMessage) {
        assertAction(() -> setString(actual.invoke()).equals(setString(expected)),
                () -> failMessage.invoke(actual.invoke()));
    }
    public void areEquals(JFunc<String> actual, String expected) {
        areEquals(actual, expected, r -> defaultBiCheckMsg(r, "equals to", expected));
    }
    // endregion

    // region contains
    public void contains(String actual, String expected, String failMessage) {
        assertAction(setString(actual).contains(setString(expected)), failMessage);
    }
    public void contains(String actual, String expected) {
        contains(actual, expected, defaultBiCheckMsg(actual, "contains", expected));
    }

    public void contains(String actual, List<String> expected, String failMessage) {
        for (String value : expected)
            contains(actual, value, failMessage);
    }
    public void contains(String actual, List<String> expected) {
        for (String value : expected)
            contains(actual, value);
    }
    public void contains(JFunc<String> actual, String expected, JFunc1<String, String> failMessage) {
        assertAction(() -> setString(actual.invoke()).contains(setString(expected)),
                () -> failMessage.invoke(actual.invoke()));
    }
    public void contains(JFunc<String> actual, String expected) {
        contains(actual, expected, r -> defaultBiCheckMsg(r, "contains", expected));
    }

    public void contains(JFunc<String> actual, List<String> expected, JFunc1<String, String> failMessage) {
        assertAction(() -> {
            String actualValue = actual.invoke();
            for (String value : expected)
                if (!setString(actualValue).contains(setString(value)))
                    return false;
            return true;
        }, () -> failMessage.invoke(actual.invoke()));
    }
    public void contains(JFunc<String> actual, List<String> expected) {
        contains(actual, expected, r -> defaultBiCheckMsg(r,
                "contains all of", print(expected)));
    }
    //endregion

    // region matches
    public void matches(String actual, String regEx, String failMessage) {
        assertAction(setString(actual).matches(setString(regEx)), failMessage);
    }
    public void matches(String actual, String regEx) {
        contains(actual, regEx, defaultBiCheckMsg(actual, "matches", regEx));
    }
    public void matches(JFunc<String> actual, String regEx, JFunc1<String, String> failMessage) {
        assertAction(() -> setString(actual.invoke()).matches(setString(regEx)),
                () -> failMessage.invoke(actual.invoke()));
    }
    public void matches(JFunc<String> actual, String regEx) {
        contains(actual, regEx, r -> defaultBiCheckMsg(r, "matches", regEx));
    }
    // endregion

    // region isTrue
    public void isTrue(Boolean condition, String failMessage) {
        assertAction(condition, failMessage);
    }
    public void isTrue(Boolean condition) {
        isTrue(condition, defaultCheckMsg("is true but false"));
    }
    public void isTrue(JFunc<Boolean> condition, String failMessage) {
        assertAction(condition, failMessage);
    }
    public void isTrue(JFunc<Boolean> condition) {
        isTrue(condition, defaultCheckMsg( "is true but false"));
    }
    // endregion

    // region isFalse
    public void isFalse(Boolean condition, String failMessage) {
        assertAction(!condition, failMessage);
    }
    public void isFalse(Boolean condition) {
        isFalse(condition, defaultCheckMsg("is false but true"));
    }
    public void isFalse(JFunc<Boolean> condition, String failMessage) {
        assertAction(() -> !condition.invoke(), failMessage);
    }
    public void isFalse(JFunc<Boolean> condition) {
        isFalse(condition, defaultCheckMsg( "is false but true"));
    }
    // endregion

    // region Exceptions
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
    private String getPrefix() {
        return isNotBlank(checkMessage) ? checkMessage : "Action";
    }
    public <E extends Exception> void throwException(JAction action, Class<E> exceptionClass, String exceptionText) {
        throwException(getPrefix(), action, exceptionClass, exceptionText);
    }
    public void hasNoExceptions(String actionName, JAction action) {
        try {
            action.invoke();
        } catch (Exception | Error ex) {
            assertException(actionName + " throws exception: " + ex.getMessage());
        }
    }

    public void hasNoExceptions(JAction action) {
        hasNoExceptions(getPrefix(), action);
    }
    // endregion

    // region Objects
    private boolean isObjEmpty(Object obj) {
        if (obj == null)
            return true;
        if (obj instanceof String)
            return obj.toString().equals("");
        if (isInterface(obj.getClass(), Collection.class))
            return ((Collection) obj).isEmpty();
        return obj.getClass().isArray() && getLength(obj) == 0;
    }

    public void isEmpty(Object obj, String failMessage) {
        assertAction(isObjEmpty(obj), failMessage);
    }
    public void isEmpty(Object obj) {
        isEmpty(obj, "Check that Object is empty");
    }
    public void isNotEmpty(Object obj, String failMessage) {
        assertAction(!isObjEmpty(obj), failMessage);
    }
    public void isNotEmpty(Object obj) {
        isNotEmpty(obj, "Check that Object is NOT empty");
    }

    public void isEmpty(JFunc<Object> obj, String failMessage) {
        assertAction(() -> isObjEmpty(obj.invoke()), failMessage);
    }
    public void isEmpty(JFunc<Object> obj) {
        isEmpty(obj, "Check that Object is empty");
    }
    public void isNotEmpty(JFunc<Object> obj, String failMessage) {
        assertAction(() -> !isObjEmpty(obj.invoke()), failMessage);
    }
    public void isNotEmpty(JFunc<Object> obj) {
        isNotEmpty(obj, "Check that Object is NOT empty");
    }
    // endregion

    // region Lists
    public <T> void listEquals(Collection<T> actual, Collection<T> expected, String failMessage) {
        if (actual != null && expected != null && actual.size() != expected.size()) {
            assertException(failMessage);
            return;
        }
        listContains(actual, expected, failMessage);
    }
    public <T> void listEquals(Collection<T> actual, Collection<T> expected) {
        if (actual != null && expected != null && actual.size() != expected.size()) {
            assertException("Collections are not equals because has different sizes. " +
                            "%s!=%s (Actual{%s}; Expected{%s})", actual.size(), expected.size(),
                    print(map(actual, Object::toString)), print(map(expected, Object::toString)));
            return;
        }
        listContains(actual, expected);
    }
    public <T> void listContains(Collection<T> actual, Collection<T> expected, String failMessage) {
        if (actual == null || expected == null || actual.size() == 0 || expected.size() == 0) {
            assertException(failMessage);
            return;
        }
        assertAction(any(expected, el -> !actual.contains(el)), failMessage);
    }
    public <T> void listContains(Collection<T> actual, Collection<T> expected) {
        if (actual == null || expected == null || actual.size() == 0 || expected.size() == 0) {
            assertException("Actual or expected list is empty");
            return;
        }
        List<String> notEquals = new ArrayList<>();
        for (T el : expected)
            if (!actual.contains(el))
                notEquals.add(el.toString());
        assertAction(notEquals.size() == 0,
                format("Not all Expected elements are in Actual: {%s}", print(notEquals)));
    }

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
        assertAction(notEquals.size() == 0,
                format("Following elements are not equal: {%s}",print(notEquals)));
    }
    // endregion

    // TODO entityIncludeMapArray

    // region Sort Array Integer
    public void isSortedByAsc(int[] array, String failMessage) {
        if (array == null || array.length == 0) {
            assertException(failMessage);
            return;
        }
        for (int i = 1; i < array.length; i++)
            if (array[i-1] > array[i])
                assertException(failMessage);
    }
    public void isSortedByAsc(int[] array) {
        if (array == null || array.length == 0) {
            assertException("Array have no elements or null");
            return;
        }
        for (int i = 1; i < array.length; i++)
            if (array[i-1] > array[i])
                assertException("Array not sorted by ascending. a[%s](%s) > a[%s](%s)",
                        i-1, array[i-1], i, array[i]);
    }
    public void isSortedByDesc(int[] array, String failMessage) {
        if (array == null || array.length == 0) {
            assertException(failMessage);
            return;
        }
        for (int i = 1; i < array.length; i++)
            if (array[i-1] < array[i])
                assertException(failMessage);
    }
    public void isSortedByDesc(int[] array) {
        if (array == null || array.length == 0) {
            assertException("Array have no elements or null");
            return;
        }
        for (int i = 1; i < array.length; i++)
            if (array[i-1] < array[i])
                assertException("Array not sorted by descending. a[%s](%s) < a[%s](%s)",
                        i-1, array[i-1], i, array[i]);
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
            if (list == null || list.size() == 0)
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
            assertAction(notEquals.size() == 0, format("Elements {%s} are not equal to %s",
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
            assertAction(notContains.size() == 0, format("Elements {%s} are not contains %s",
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
            assertAction(notMatch.size() == 0, format("Elements {%s} are not match to %s",
                    print(notMatch), regEx));
        }

    }

}