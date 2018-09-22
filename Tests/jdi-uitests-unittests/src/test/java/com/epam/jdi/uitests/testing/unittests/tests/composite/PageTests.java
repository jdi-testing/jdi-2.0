package com.epam.jdi.uitests.testing.unittests.tests.composite;

import com.epam.jdi.uitests.testing.unittests.InitTests;
import org.openqa.selenium.Cookie;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.epam.jdi.uitests.core.preconditions.PreconditionsState.isInState;
import static com.epam.jdi.uitests.testing.unittests.enums.Preconditions.CONTACT_PAGE;
import static com.epam.jdi.uitests.testing.unittests.pageobjects.EpamJDISite.contactFormPage;
import static com.epam.jdi.uitests.testing.unittests.pageobjects.EpamJDISite.homePage;
import static com.epam.jdi.uitests.web.selenium.driver.WebDriverFactory.getDriver;
import static com.epam.matcher.testng.Assert.*;


/**
 * Created by Dmitry_Lebedev1 on 22/12/2015.
 */
public class PageTests extends InitTests {
    @BeforeMethod
    public void before(final Method method) {
        isInState(CONTACT_PAGE, method);
    }

    @Test
    public void refreshTest(){
        contactFormPage.contactSubmit.click();
        areEquals(contactFormPage.result::getText, "Summary: 3");
        contactFormPage.refresh();
        areEquals(contactFormPage.result::getText, "");
        contactFormPage.checkOpened();
    }

    @Test
    public void backTest(){
        homePage.open();
        homePage.checkOpened();
        homePage.back();
        contactFormPage.checkOpened();
    }

    @Test
    public void forwardTest(){
        homePage.open();
        getDriver().navigate().back();
        contactFormPage.checkOpened();
        contactFormPage.forward();
        homePage.checkOpened();
    }

    @Test
    public void addCookieTest() throws Exception{
        getDriver().manage().deleteAllCookies();
        assertTrue(() -> getDriver().manage().getCookies().isEmpty());
        Cookie cookie = new Cookie("key", "value");
        contactFormPage.addCookie(cookie);
        assertEquals(() -> getDriver().manage().getCookieNamed(cookie.getName()).getValue(), cookie.getValue());
    }

    @Test
    public void clearCacheTest()throws Exception{
        Cookie cookie = new Cookie("key", "value");
        getDriver().manage().addCookie(cookie);
        assertFalse(() -> getDriver().manage().getCookies().isEmpty());
        contactFormPage.clearCache();
        assertTrue(() -> getDriver().manage().getCookies().isEmpty());
    }

    @Test
    public void checkOpenedTest(){
         contactFormPage.checkOpened();
    }

    @AfterMethod
    public void restoreLoginCookies(){
        getDriver().manage().deleteAllCookies();
        Cookie loginCookie = new Cookie("authUser", "true", "epam.github.io", "/", null, false);
        getDriver().manage().addCookie(loginCookie);
    }

}
