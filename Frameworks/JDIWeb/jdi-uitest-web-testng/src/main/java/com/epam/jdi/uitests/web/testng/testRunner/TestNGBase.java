package com.epam.jdi.uitests.web.testng.testRunner;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.Timer;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static com.epam.jdi.tools.StringUtils.LINE_BREAK;
import static com.epam.jdi.uitests.core.settings.JDISettings.logger;
import static com.epam.jdi.uitests.web.selenium.driver.WebDriverFactory.*;
import static com.epam.jdi.uitests.web.selenium.driver.WebDriverUtils.killAllRunWebBrowsers;
import static com.epam.jdi.uitests.web.selenium.settings.WebSettings.*;
import static com.epam.jdi.uitests.web.settings.JDITestNGSettings.initFromProperties;
import static org.openqa.selenium.remote.BrowserType.CHROME;

/**
 * Base class for TestNG runner. Includes Timer instance, method for getting test run time, Setup and Teardown.
 */
public class TestNGBase {
    protected static Timer timer = new Timer();

    /**
     * Returns the run time of the test using Timer
     *
     * @return the run time of the test.
     */
    public static long getTestRunTime() {
        return timer.timePassedInMSec();
    }

    /**
     * Sets up JDI parameters, including initialisation from Properties. Kills browsers, if requested in "killBrowser" ;
     * restart timer and if no driver specified set Chrome as default.
     *
     * @throws IOException if an input/output exception occured.
     */
    @BeforeSuite(alwaysRun = true)
    public static void jdiSetUp() throws IOException {
        initFromProperties();
        logger.info("Init test run");
        if (killBrowser.toLowerCase().contains("before"))
            killAllRunWebBrowsers();
        if (!hasDrivers())
            useDriver(CHROME);
        timer.restart();
    }

    /**
     * Tears down JDI settings, logs end datetime of test, kills browsers if requested in "killBrowser"
     *
     * @throws IOException if an input/output exception occured.
     */
    @AfterSuite(alwaysRun = true)
    public static void jdiTearDown() throws IOException {
        LocalDateTime date = Instant.ofEpochMilli(21 * 3600000 + getTestRunTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        String formattedTime = DateTimeFormatter.ofPattern("HH:mm:ss.S").format(date);

        logger.info("Test run finished. " + LINE_BREAK + "Total test run time: " + formattedTime);

        if (killBrowser.toLowerCase().contains("after"))
            killAllRunWebBrowsers();
    }
}