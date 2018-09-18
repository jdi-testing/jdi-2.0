package com.epam.jdi.uitests.web.testng.testRunner;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JFunc;
import com.epam.jdi.uitests.core.interfaces.IAsserter;
import com.epam.jdi.uitests.core.logger.ILogger;
import com.epam.matcher.testng.TestNG;
import org.testng.Assert;

import java.util.function.BooleanSupplier;

import static java.lang.String.format;


/**
 * Class for TestNG Checks, extends TestNG and implements IAsserter
 */
public class TestNGCheck extends TestNG implements IAsserter {
    /**
     * Set ups Logger from params (see implementation for details)
     * @param logger logger to set up (not used currently)
     * @return this
     */
    public IAsserter setUpLogger(ILogger logger) { return this;/*super.setLogger(logger); return this;*/ }

    /**
     * Provides assertion error with comment message
     * @param message message to pass with exception
     * @param args format args
     * @return AssertionError with message formatted
     */
    public AssertionError exception(String message, Object... args) {
        return new AssertionError(format(message, args));
    }


    /**
     *
     * @param func
     * @param <TResult>
     * @return null
     */
    public <TResult> TResult silent(JFunc<TResult> func) {
        return null;
    }

    /**
     * Asserts if passed boolean value is true
     * @param actual value to check
     */
    public void isTrue(BooleanSupplier actual) {
        Assert.assertTrue(actual.getAsBoolean());
    }

    /**
     * Not implemented
     * @param doScreenshot unused in implementation
     */
    public void doScreenshot(String doScreenshot) {

    }
}
