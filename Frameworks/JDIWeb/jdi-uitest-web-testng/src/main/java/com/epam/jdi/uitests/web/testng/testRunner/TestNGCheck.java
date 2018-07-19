package com.epam.jdi.uitests.web.testng.testRunner;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JAction;
import com.epam.jdi.tools.func.JFunc;
import com.epam.jdi.uitests.core.interfaces.IAsserter;
import com.epam.jdi.uitests.core.logger.ILogger;
import com.epam.matcher.testng.TestNG;
import org.testng.Assert;

import java.util.function.BooleanSupplier;

import static java.lang.String.format;

public class TestNGCheck extends TestNG implements IAsserter {
    public IAsserter setUpLogger(ILogger logger) { return this;/*super.setLogger(logger); return this;*/ }

    public AssertionError exception(String message, Object... args) {
        return new AssertionError(format(message, args));
    }

    public <TResult> TResult silent(JFunc<TResult> func) {
        return null;
    }

    public void ignore(JAction action) {

    }

    public void isTrue(BooleanSupplier actual) {

    }

    public void checkMessage(String checkMessage) {

    }

    public void doScreenshot(String doScreenshot) {

    }
}
