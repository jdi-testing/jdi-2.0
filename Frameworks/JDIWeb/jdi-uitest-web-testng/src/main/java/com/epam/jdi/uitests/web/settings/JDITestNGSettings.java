package com.epam.jdi.uitests.web.settings;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.web.selenium.driver.ScreenshotMaker;
import com.epam.jdi.uitests.web.selenium.settings.WebSettings;
import com.epam.jdi.uitests.web.testng.testRunner.TestNGCheck;
import com.epam.jdi.uitests.web.testng.testRunner.TestNGLogger;
import org.testng.annotations.Test;

import static com.epam.jdi.tools.PropertyReader.fillAction;

/**
 * Setting class for TestNG; extending WebSettings class
 */
public class JDITestNGSettings extends WebSettings {
    /**
     * Initialisation of JDI TestNG settings. Includes setting up logger, timeouts and screenshot checking
     */
    public static synchronized void init() {
        logger = TestNGLogger.instance("JDI");
        asserter = new TestNGCheck().setUpLogger(logger);
        //setMatcher((BaseMatcher) asserter);
        asserter.doScreenshot("no_screen");
        //screenshotAction = ScreenshotMaker::doScreenshotGetMessage;
        timeouts = new WebTimeoutSettings();
    }

    /**
     * Flag of initialization, set True only by initializing from Properties
     */
    public static boolean initialized = false;

    /**
     * Initialisation of JDI from properties. Use WebSettings, base initialization of JDI TestNG Settings
     * override screenshot action and sets "initialized" flag to True.
     */
    public static synchronized void initFromProperties() {
        WebSettings.initFromProperties();
        init();
        fillAction(p -> asserter.doScreenshot(p), "screenshot.strategy");
        initialized = true;
    }
    /**
     * Empty method, does nothing
     */
    @Test
    public void testMethod() {}
}