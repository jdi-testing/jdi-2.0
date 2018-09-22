package com.epam.jdi.uitests.web.selenium.driver;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JFunc;
import com.epam.jdi.tools.map.MapArray;
import com.epam.jdi.tools.pairs.Pair;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.epam.jdi.uitests.core.settings.JDISettings.exception;
import static com.epam.jdi.uitests.web.selenium.driver.get.DownloadDriverManager.downloadDriver;
import static com.epam.jdi.uitests.web.selenium.driver.get.DriverData.*;
import static com.epam.jdi.tools.StringUtils.LINE_BREAK;
import static java.lang.String.format;
import static java.lang.System.setProperty;
import static java.lang.Thread.currentThread;
import static org.openqa.selenium.remote.BrowserType.*;

public class WebDriverFactory {
    public static MapArray<String, JFunc<WebDriver>> drivers = new MapArray<>();
    private static ThreadLocal<MapArray<String, WebDriver>> runDrivers = new ThreadLocal<>();

    private WebDriverFactory() { }
    public static boolean hasRunDrivers() {
        return runDrivers.get() != null && runDrivers.get().any();
    }
    public static boolean hasDrivers() {
        return drivers.any();
    }

    /**
     * Registers driver
     * @param driver - driver
     * @return String - drivers name
     */
    public static String useDriver(JFunc<WebDriver> driver) {
        return useDriver("Driver" + (drivers.size() + 1), driver);
    }

    /**
     * Registers driver
     * @param driver - drivers name
     * @return String - drivers name
     */
    public static String useDriver(String driver) {
        return useDriver(driver, () -> initDriver(driver));
    }

    /**
     * Inits driver
     * @param type - drivers type
     * @return WebDriver
     */
    private static WebDriver initDriver(String type) {
        WebDriver driver;
        switch (type) {
            case CHROME:
                if (DRIVER_VERSION.equals(""))
                    setProperty("webdriver.chrome.driver", chromeDriverPath());
                else
                    downloadDriver(CHROME, PLATFORM, DRIVER_VERSION);
                driver = new ChromeDriver(CHROME_OPTIONS.execute());
                break;
            case FIREFOX:
                if (DRIVER_VERSION.equals(""))
                    setProperty("webdriver.gecko.driver", firefoxDriverPath());
                else
                    downloadDriver(FIREFOX, PLATFORM, DRIVER_VERSION);
                driver = new FirefoxDriver(FIREFOX_OPTIONS.execute());
                break;
            case IE:
                if (DRIVER_VERSION.equals(""))
                    setProperty("webdriver.ie.driver", ieDriverPath());
                else
                    downloadDriver(IE, PLATFORM, DRIVER_VERSION);
                driver = new InternetExplorerDriver(IE_OPTIONS.execute());
                break;
            case EDGE:
                // TODO
                driver = new EdgeDriver(EDGE_OPTIONS.execute());
                break;
            case PHANTOMJS:
                // TODO
                driver = new PhantomJSDriver(PHANTOM_JS_OPTIONS.execute(), new DesiredCapabilities());
                break;
            case OPERA:
                // TODO
                driver = new OperaDriver(OPERA_OPTIONS.execute());
                break;
            default:
                throw exception("Unknown driver: " + type);
        }
        return DRIVER_SETTINGS.execute(driver);
    }

    /**
     * Registers driver
     * @param driverName - drivers name
     * @param driver
     * @return String - drivers name
     */
    public static String useDriver(String driverName, JFunc<WebDriver> driver) {
        if (!drivers.keys().contains(driverName))
            drivers.add(driverName, driver);
        else
            throw exception("Can't register WebDriver '%s'. Driver with same name already registered", driverName);
        DRIVER_NAME = driverName;
        return driverName;
    }

    /**
     * Returns WebDriver
     * @return WebDriver
     */
    public static WebDriver getDriver() {
        try {
            if (!DRIVER_NAME.equals(""))
                return getDriver(DRIVER_NAME);
            useDriver(CHROME);
            return getDriver(CHROME);
        } catch (Exception ex) {
            throw exception("Can't get WebDriver. " + LINE_BREAK + ex.getMessage());
        }
    }

    /**
     * Gets driver by it's name
     * @param driverName - drivers name
     * @return WebDriver
     */
    public static WebDriver getDriver(String driverName) {
        if (!drivers.keys().contains(driverName))
            if (drivers.count() == 0)
                useDriver(driverName);
            else throw exception("Can't find driver with name '%s'", driverName);
        try {
            Lock lock = new ReentrantLock();
            lock.lock();
            if (runDrivers.get() == null || !runDrivers.get().keys().contains(driverName)) {
                MapArray<String, WebDriver> rDrivers = runDrivers.get();
                if (rDrivers == null)
                    rDrivers = new MapArray<>();
                WebDriver resultDriver = drivers.get(driverName).invoke();
                if (resultDriver == null)
                    throw exception("Can't get WebDriver '%s'. This Driver name not registered", driverName);
                rDrivers.add(driverName, resultDriver);
                runDrivers.set(rDrivers);
            }
            WebDriver result = runDrivers.get().get(driverName);
            if (result.toString().contains("(null)")) {
                result = drivers.get(driverName).invoke();
                runDrivers.get().update(driverName, result);
            }
            lock.unlock();
            return result;
        } catch (Exception ex) {
            throw exception("Can't get driver; Thread: " + currentThread().getId() + LINE_BREAK +
                    format("Drivers: %s; Run: %s", drivers, runDrivers) +
                    "Exception: " + ex.getMessage());
        }
    }

    /**
     * Executes js
     * @param js - js to execute
     * @param args - args
     */
    public static void jsExecute(String js, Object... args) {
        getJSExecutor().executeScript(js, args);
    }

    /**
     * Gets JavascriptExecutor
     * @return JavascriptExecutor
     */
    public static JavascriptExecutor getJSExecutor() {
        if (getDriver() instanceof JavascriptExecutor)
            return (JavascriptExecutor) getDriver();
        else
            throw new ClassCastException("JavaScript Executor doesn't support");
    }

    /**
     * Reopens driver
     */
    public static void reopenDriver() {
        reopenDriver(DRIVER_NAME);
    }

    /**
     * Reopens driver
     * @param driverName - drivers name
     */
    public static void reopenDriver(String driverName) {
        MapArray<String, WebDriver> rDriver = runDrivers.get();
        if (rDriver.keys().contains(driverName)) {
            rDriver.get(driverName).close();
            rDriver.removeByKey(driverName);
            runDrivers.set(rDriver);
        }
        if (drivers.keys().contains(driverName))
            getDriver();
    }

    /**
     * Switches to driver
     * @param driverName - drivers name
     */
    public static void switchToDriver(String driverName) {
        if (drivers.keys().contains(driverName))
            DRIVER_NAME = driverName;
        else
            throw exception("Can't switch to Webdriver '%s'. This Driver name not registered", driverName);
    }

    /**
     * Closes driver
     */
    public static void close() {
        for (Pair<String, WebDriver> driver : runDrivers.get())
            driver.value.quit();
        runDrivers.get().clear();
    }

    /**
     * Quites driver
     */
    public static void quit() {
        close();
    }
}