package com.epam.jdi.uitests.web.settings;
/*
 * Copyright 2004-2016 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * JDI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JDI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JDI. If not, see <http://www.gnu.org/licenses/>.
 */


import com.epam.jdi.uitests.web.selenium.driver.ScreenshotMaker;
import com.epam.jdi.uitests.web.selenium.settings.WebSettings;
import com.epam.jdi.uitests.web.testng.testRunner.TestNGCheck;
import com.epam.jdi.uitests.web.testng.testRunner.TestNGLogger;
import com.epam.web.matcher.base.BaseMatcher;

import static com.epam.jdi.tools.PropertyReader.fillAction;
import static com.epam.web.matcher.base.BaseMatcher.screenshotAction;
import static com.epam.web.matcher.testng.Assert.setMatcher;

/**
 * Created by Roman_Iovlev on 11/13/2015.
 */
public class JDITestNGSettings extends WebSettings {

    public static synchronized void init() {
        logger = new TestNGLogger("JDI Logger");
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