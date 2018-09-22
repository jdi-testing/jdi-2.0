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

public class JDITestNGSettings extends WebSettings {
    public static synchronized void init() {
        logger = TestNGLogger.instance("JDI");
        asserter = new TestNGCheck().setUpLogger(logger);
        //setMatcher((BaseMatcher) asserter);
        asserter.doScreenshot("no_screen");
        //screenshotAction = ScreenshotMaker::doScreenshotGetMessage;
        timeouts = new WebTimeoutSettings();
    }
    public static boolean initialized = false;

    public static synchronized void initFromProperties() {
        WebSettings.initFromProperties();
        init();
        fillAction(p -> asserter.doScreenshot(p), "screenshot.strategy");
        initialized = true;
    }
    @Test
    public void testMethod() {}
}