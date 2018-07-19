package com.epam.jdi.uitests.core.interfaces;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JAction;
import com.epam.jdi.tools.func.JFunc;

import java.util.function.BooleanSupplier;

public interface IAsserter {
    AssertionError exception(String message, Object... args);
    <TResult> TResult silent(JFunc<TResult> func);
    void ignore(JAction action);
    void isTrue(Boolean actual);
    void isTrue(Boolean actual, String msg);
    void isTrue(BooleanSupplier actual);
    void checkMessage(String checkMessage);
    void doScreenshot(String doScreenshot);
}