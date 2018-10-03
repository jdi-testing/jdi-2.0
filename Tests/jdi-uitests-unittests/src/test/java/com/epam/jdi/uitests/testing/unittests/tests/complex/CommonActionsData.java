package com.epam.jdi.uitests.testing.unittests.tests.complex;

import com.epam.jdi.tools.Timer;
import com.epam.jdi.tools.func.JAction;
import org.openqa.selenium.By;

import java.io.File;
import java.util.function.Supplier;

import static com.epam.jdi.uitests.core.settings.JDISettings.exception;
import static com.epam.jdi.uitests.testing.unittests.pageobjects.EpamJDISite.*;
import static com.epam.jdi.uitests.web.selenium.driver.WebDriverFactory.getDriver;
import static com.epam.matcher.testng.Assert.areEquals;
import static com.epam.matcher.testng.Assert.assertContains;

/**
 * Created by Roman_Iovlev on 9/18/2015.
 */
public class CommonActionsData {
    static final String noElementsMessage = "No elements selected. Override getSelectedAction or place locator to <select> tag";
    public static final long waitTimeOut = 1000;
    public static Timer timer;
    private static String _name = null;
    private static String _path = null;

    static void checkActionThrowError(JAction action, String msg) {
        try {
            action.invoke();
        } catch (Exception | AssertionError ex) {
            assertContains(ex.getMessage(), msg);
            return;
        }
        throw exception("Exception not thrown");
    }

    static long getTimePassed() {
        return timer.timePassedInMSec();
    }

    public static void runParallel(final JAction action) {
        new Thread() {
            @Override
            public void run() {
                timer = new Timer();
                Timer.sleep(waitTimeOut);
                action.execute();
            }
        }.run();
    }

    private static void createFile() {
        try {
            File temp = File.createTempFile("tmp", ".tmp");
            _name = temp.getName();
            _path = temp.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getFPath() {
        if (_path == null) {
            createFile();
        }
        return _path;
    }

    public static String fileName() {
        if (_name == null) {
            createFile();
        }
        return _name;
    }

    public static void checkAction(String text) {
        assertContains(actionsLog::first, text);
    }

    public static void looseFocus() {
        getDriver().findElement(By.className("footer-content")).click();
    }

    public static void checkText(Supplier<String> func, String expected) {
        areEquals(func.get(), expected);
    }

    public static void checkCalculate(String text) {
       assertContains(metalsColorsPage.calculateText::getText, text);
    }

    public static void checkResult(String text) {
        assertContains(contactFormPage.result::getText, text);
    }

}
