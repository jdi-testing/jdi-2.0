package com.epam.jdi.uitests.testing;

import com.epam.jdi.site.epam.EpamSite;
import com.epam.jdi.uitests.web.testng.testRunner.TestNGBase;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;

import static com.epam.jdi.uitests.core.logger.LogLevels.INFO;
import static com.epam.jdi.uitests.core.settings.JDISettings.logger;
import static com.epam.jdi.uitests.web.selenium.driver.WebDriverUtils.killAllRunWebBrowsers;
import static com.epam.jdi.uitests.web.selenium.elements.composite.WebSite.init;

/**
 * Created by Roman_Iovlev on 7/13/2015.
 */
public abstract class TestsBase extends TestNGBase {
    @BeforeSuite(alwaysRun = true)
    public static void setUp() {
        logger.setLogLevel(INFO);
        init(EpamSite.class);
        EpamSite.open();
        logger.step("Run Tests");
    }

    @AfterSuite(alwaysRun = true)
    public static void tearDown() {
        killAllRunWebBrowsers();
    }
}
