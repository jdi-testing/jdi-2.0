package com.epam.jdi.uitests.testing.simple.examples;

import com.epam.jdi.selenium.pageobject.SeleniumPage;
import com.epam.jdi.uitests.web.selenium.settings.WebSettings;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.jdi.uitests.web.selenium.driver.WebDriverFactory.getDriver;
import static com.epam.jdi.uitests.web.selenium.elements.WebCascadeInit.initPageObject;
import static java.lang.System.setProperty;

/**
 * Created by Roman_Iovlev on 5/23/2017.
 */
public class SeleniumTest {
    SeleniumPage page;
    WebDriver chromeDriver;
    private WebDriver initDriver() {
        String driverPath = "C:/Selenium/chromedriver.exe";
        System.out.println("PLACE CHROME DRIVER HERE: " + driverPath);
        setProperty("webdriver.chrome.driver", driverPath);
        chromeDriver = new ChromeDriver();
        chromeDriver.manage().window().maximize();
        return chromeDriver;
    }

    @BeforeMethod
    public void before(Method method)  {
        page = initPageObject(SeleniumPage.class, this::initDriver);
    }

    @Test
    public void seleniumTest() {
        getDriver().navigate().to("https://www.epam.com/");
        page.logo.click();
        page.menu.get(3).click();
    }
}
