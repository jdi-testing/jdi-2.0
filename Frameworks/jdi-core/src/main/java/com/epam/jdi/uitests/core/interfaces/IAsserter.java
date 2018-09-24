package com.epam.jdi.uitests.core.interfaces;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JAction;
import com.epam.jdi.tools.func.JFunc;

import java.util.function.BooleanSupplier;

/**
 * Interface {@code IAsserter} is a facade for any assertion mechanism
 */
public interface IAsserter {
    /**
     * @param message a format string
     * @param args arguments referenced by the format specifiers in the format string
     * @return AssertionError with a message formatted from method parameters
     */
    AssertionError exception(String message, Object... args);

    <TResult> TResult silent(JFunc<TResult> func);

    /**
     * Invokes an action and ignores any occurred exceptions
     * @param action JAction
     */
    default void ignore(JAction action) {
        try {
            action.invoke();
        } catch (Exception ignored) {
        }
    }

    void isTrue(Boolean actual);

    void isTrue(Boolean actual, String msg);

    void isTrue(BooleanSupplier actual);

    void doScreenshot(String doScreenshot);
}