package com.epam.jdi.uitests.testing.unittests;

import com.epam.jdi.uitests.testing.unittests.pageobjects.EpamJDISite;
import com.epam.jdi.uitests.testing.unittests.pageobjects.w3cSite.W3cSite;
import com.epam.jdi.uitests.web.testng.testRunner.TestNGBase;
import com.epam.matcher.base.Verify;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import static com.epam.jdi.uitests.testing.unittests.pageobjects.w3cSite.W3cSite.framePage;
import static com.epam.jdi.uitests.web.selenium.driver.WebDriverFactory.useDriver;
import static com.epam.jdi.uitests.web.selenium.elements.composite.WebSite.init;
import static com.epam.jdi.uitests.web.selenium.settings.WebSettings.*;
import static org.openqa.selenium.remote.BrowserType.CHROME;


/**
 * Created by Roman_Iovlev on 7/13/2015.
 */
public class W3CInit extends TestNGBase {

    @BeforeSuite(alwaysRun = true)
    public static void setUp() throws Exception {
        domain = "http://www.w3schools.com";
        init(W3cSite.class, useDriver(CHROME));
        init(EpamJDISite.class, useDriver(CHROME));
        framePage.open();
        Verify.getFails();
        logger.info("Run Tests");
    }

    @AfterMethod
    public void tearDown() {
        Verify.getFails();
    }
}
