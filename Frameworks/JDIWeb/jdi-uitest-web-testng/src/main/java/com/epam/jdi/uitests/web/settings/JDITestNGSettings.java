package com.epam.jdi.uitests.web.settings;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.web.selenium.driver.ScreenshotMaker;
import com.epam.jdi.uitests.web.selenium.settings.WebSettings;
import com.epam.jdi.uitests.web.testng.testRunner.TestNGCheck;
import com.epam.jdi.uitests.web.testng.testRunner.TestNGLogger;
import com.epam.web.matcher.base.BaseMatcher;

import static com.epam.jdi.tools.PropertyReader.fillAction;
import static com.epam.web.matcher.base.BaseMatcher.screenshotAction;
import static com.epam.web.matcher.testng.Assert.setMatcher;

public class JDITestNGSettings extends WebSettings {

    public static synchronized void init() {
        logger = TestNGLogger.instance("JDI Logger");
        asserter = new TestNGCheck().setUpLogger(logger);
        setMatcher((BaseMatcher) asserter);
        asserter.doScreenshot("screen_on_fail");
        screenshotAction = ScreenshotMaker::doScreenshotGetMessage;
        timeouts = new WebTimeoutSettings();
    }
    public static boolean initialized = false;

    public static synchronized void initFromProperties() {
        WebSettings.initFromProperties();
        init();
        fillAction(p -> asserter.doScreenshot(p), "screenshot.strategy");
        initialized = true;
    }
}