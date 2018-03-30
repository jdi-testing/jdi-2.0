package com.epam.jdi.uitests.web.selenium.driver;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JFunc;
import com.epam.jdi.tools.func.JFunc1;
import com.epam.jdi.tools.map.MapArray;
import com.epam.jdi.tools.pairs.Pair;
import com.epam.jdi.uitests.web.selenium.driver.get.driver.DriverTypes;
import com.epam.jdi.uitests.web.selenium.driver.get.driver.RunTypes;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

import static com.epam.jdi.tools.LinqUtils.any;
import static com.epam.jdi.tools.StringUtils.LINE_BREAK;
import static com.epam.jdi.uitests.core.settings.JDISettings.exception;
import static com.epam.jdi.uitests.web.selenium.driver.get.driver.DriverTypes.*;
import static com.epam.jdi.uitests.web.selenium.driver.get.driver.RunTypes.*;
import static java.io.File.separator;
import static java.lang.String.format;
import static java.lang.System.setProperty;
import static java.lang.Thread.currentThread;
import static java.util.Arrays.asList;
import static org.openqa.selenium.ie.InternetExplorerDriver.*;
import static org.openqa.selenium.ie.InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING;
import static org.openqa.selenium.remote.CapabilityType.*;

public class WebDriverFactory {
    public static JFunc1<WebElement, Boolean> elementSearchCriteria = WebElement::isDisplayed;
    public static boolean onlyOneElementAllowedInSearch = true;
    static final String FOLDER_PATH = Paths.get("src","main","resources","driver").toAbsolutePath().toString();
    public Boolean getLatestDriver = false;
    public static String currentDriverName = "CHROME";
    public boolean isDemoMode = false;
    public String pageLoadStrategy = "normal";
    private String driversPath = FOLDER_PATH;
    private MapArray<String, JFunc<WebDriver>> drivers = new MapArray<>();
    private ThreadLocal<MapArray<String, WebDriver>> runDrivers = new ThreadLocal<>();
    private RunTypes runType = LOCAL;
    private String hubUrl;
    static final String DOWNLOADS_DIR = Paths.get("src","test","resources","downloads").toAbsolutePath().toString();
    private Path downloadsDir = Paths.get(DOWNLOADS_DIR);

    public WebDriverFactory() {
        elementSearchCriteria = WebElement::isDisplayed;
    }

    public String getDriverPath() {
        return driversPath;
    }

    public void setDriverPath(String driverPath) {
        this.driversPath = driverPath;
    }

    static final String getDriverPath(String name, String folderPath) {
        String path = folderPath + separator + name;
        if (checkOS().equals("win"))
            path += ".exe";
        return path;
    }

    static final String getIEDriverPath(String folderPath) {
        return folderPath + "\\IEDriverServer.exe";
    }

    static String checkOS() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("mac")) {
            return "mac";
        } else if (osName.contains("win") || osName.contains("ms")) {
            return "win";
        } else {
            return "nix";
        }
    }

    public String currentDriverName() {
        return currentDriverName;
    }

    public void setCurrentDriver(String driverName) {
        currentDriverName = driverName;
    }

    public boolean hasDrivers() {
        return drivers.any();
    }

    public boolean hasRunDrivers() {
        return runDrivers.get() != null && runDrivers.get().any();
    }

    // REGISTER DRIVER

    public String registerDriver(JFunc<WebDriver> driver) {
        return registerDriver("Driver" + (drivers.size() + 1), driver);
    }

    public String registerDriver(String driverName) {
        switch (driverName.toLowerCase()) {
            case "chrome":
                return registerDriver(CHROME);
            case "firefox":
                return registerDriver(FIREFOX);
            case "ie":
            case "internetexplorer":
                return registerDriver(IE);
            default:
                throw exception("Unknown driver: " + driverName);
        }
    }

    public void setRunType(String runType) {
        switch (runType.toLowerCase()) {
            case "local":
                this.runType = LOCAL;
                break;
            case "remote":
                this.runType = REMOTE;
                break;
        }
    }
    public void setHubUrl(String hubUrl) {
        if (hubUrl == null || hubUrl.isEmpty()) hubUrl = "http://127.0.0.1:4444/wd/hub";
            this.hubUrl = hubUrl;
    }
    public void setDownloadsDir(String downloadsDir){
        this.downloadsDir = Paths.get(downloadsDir);
    }

    private WebDriver initRemoteChromeDriver() {
        setProperty("webdriver.chrome.driver", getDriverPath("chromedriver", driversPath));
        if (getLatestDriver) {
            ChromeDriverManager.getInstance().setup();
        }
        try {
            return webDriverSettings.apply(new RemoteWebDriver(new URL(hubUrl), defaultChromeOptions()));
        } catch (MalformedURLException e) {
            throw exception("Incorrect hub url: " + e);
        }
    }

    private WebDriver initChromeDriver() {
        setProperty("webdriver.chrome.driver", getDriverPath("chromedriver", driversPath));
        return webDriverSettings.apply(getLatestDriver
                ? initChrome()
                : new ChromeDriver(defaultChromeOptions()));
    }

    private WebDriver initFirefoxDriver() {
        setProperty("webdriver.gecko.driver", getDriverPath("geckodriver", driversPath));
        return webDriverSettings.apply(getLatestDriver
                ? initFirefox()
                : new FirefoxDriver(defaultFirefoxOptions()));
    }

    private WebDriver initRemoteFirefoxDriver() {
        setProperty("webdriver.gecko.driver", getDriverPath("geckodriver", driversPath));
        if (getLatestDriver) {
            FirefoxDriverManager.getInstance().arch32().setup();
        }
        try {
            return webDriverSettings.apply(new RemoteWebDriver(new URL(hubUrl), defaultFirefoxOptions()));
        } catch (MalformedURLException e) {
            throw exception("Incorrect hub url: " + e);
        }
    }

    private WebDriver initIEDriver() {
        setProperty("webdriver.ie.driver", getIEDriverPath(driversPath));
        return webDriverSettings.apply(getLatestDriver
                ? initIE()
                : new InternetExplorerDriver(defaultIEOptions()));
    }

    private WebDriver initRemoteIEDriver() {
        setProperty("webdriver.ie.driver", getIEDriverPath(driversPath));
        if (getLatestDriver) {
            InternetExplorerDriverManager.getInstance().setup();
        }
        try {
            return webDriverSettings.apply(new RemoteWebDriver(new URL(hubUrl), defaultIEOptions()));
        } catch (MalformedURLException e) {
            throw exception("Incorrect hub url: " + e);
        }
    }

    public String registerLocalDriver(DriverTypes driverType) {
        switch (driverType) {
            case CHROME:
                return registerDriver(driverType, this::initChromeDriver);
            case FIREFOX:
                return registerDriver(driverType, this::initFirefoxDriver);
            case IE:
                return registerDriver(driverType, this::initIEDriver);
        }
        throw exception("Unknown driver: " + driverType);
    }

    public String registerRemoteDriver(DriverTypes driverType) {
        switch (driverType) {
            case CHROME:
                return registerDriver(driverType, this::initRemoteChromeDriver);
            case FIREFOX:
                return registerDriver(driverType, this::initRemoteFirefoxDriver);
            case IE:
                return registerDriver(driverType, this::initRemoteIEDriver);
        }
        throw exception("Unknown driver: " + driverType);
    }

    public String registerDriver(DriverTypes driverType) {
        switch (runType) {
            case LOCAL:
                return registerLocalDriver(driverType);
            case REMOTE:
                return registerRemoteDriver(driverType);
        }
        throw exception("Unknown driver: " + driverType);
    }

    // GET DRIVER
    private FirefoxOptions defaultFirefoxOptions() {
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setAssumeUntrustedCertificateIssuer(false);
        firefoxProfile.setPreference("browser.download.folderList", 2);
        firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
        firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
        firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/xls;text/csv;text/plain");
        firefoxProfile.setPreference("browser.download.dir", downloadsDir.toAbsolutePath().toString());
        firefoxProfile.setPreference("print.always_print_silent", "true");
        firefoxProfile.setPreference("print.show_print_progress", "false");
        firefoxProfile.setPreference("browser.startup.homepage", "about:blank");
        firefoxProfile.setPreference("startup.homepage_welcome_url", "about:blank");
        firefoxProfile.setPreference("startup.homepage_welcome_url.additional", "about:blank");
        firefoxProfile.setPreference("network.http.phishy-userpass-length", 255);
        FirefoxOptions cap = new FirefoxOptions();
        cap.setCapability(PAGE_LOAD_STRATEGY, pageLoadStrategy);
        cap.setCapability(ACCEPT_SSL_CERTS, true);
        cap.setProfile(firefoxProfile);
        return cap;
    }

    private ChromeOptions defaultChromeOptions() {
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("credentials_enable_service", false);
        chromePrefs.put("download.default_directory", downloadsDir.toAbsolutePath().toString());
        chromePrefs.put("profile.default_content_setting_values.notifications", 0);
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("profile.password_manager_enabled", false);
        ChromeOptions cap = new ChromeOptions();
        cap.addArguments("--disable-web-security", "--disable-extensions", "test-type");
        cap.setCapability(PAGE_LOAD_STRATEGY, pageLoadStrategy);
        cap.setCapability(ACCEPT_SSL_CERTS, true);
        cap.setExperimentalOption("prefs", chromePrefs);
        return cap;
    }

    private InternetExplorerOptions defaultIEOptions() {
        InternetExplorerOptions cap = new InternetExplorerOptions();
        cap.introduceFlakinessByIgnoringSecurityDomains();
        cap.setCapability("ignoreZoomSetting", true);
        //cap.setCapability("requireWindowFocus", true);
        cap.setCapability(PAGE_LOAD_STRATEGY, pageLoadStrategy);
        cap.takeFullPageScreenshot();
        cap.setCapability(ACCEPT_SSL_CERTS, true);
        cap.setCapability(IE_ENSURE_CLEAN_SESSION, true);
        cap.setCapability(UNEXPECTED_ALERT_BEHAVIOR, true);
        cap.setCapability(SUPPORTS_JAVASCRIPT, true);
        cap.setCapability(NATIVE_EVENTS, false);
        cap.setCapability(ENABLE_PERSISTENT_HOVERING, false);
        return cap;
    }

    public String registerDriver(DriverTypes driverType, JFunc<WebDriver> driver) {
        int numerator = 2;
        String driverName = driverType.toString();
        // TODO correct constant 100
        while (drivers.keys().contains(driverName)) {
            driverName = driverType.toString() + numerator++;
            if (numerator == 100)
                throw exception("Can't register driver " + driverType);
        }
        drivers.add(driverName, driver);
        currentDriverName = driverName;
        return driverName;
    }

    public String registerDriver(String driverName, JFunc<WebDriver> driver) {
        if (drivers.keys().contains(driverName))
            drivers.add(driverName, driver);
        else
            throw exception("Can't register WebDriver '%s'. Driver with same name already registered", driverName);
        currentDriverName = driverName;
        return driverName;
    }

    public WebDriver getDriver() {
        try {
            if (!currentDriverName.equals(""))
                return getDriver(currentDriverName);
            registerDriver(CHROME);
            return getDriver(CHROME.toString());
        } catch (Exception ex) {
            throw exception("Can't get WebDriver. " + LINE_BREAK + ex.getMessage());
        }
    }

    private WebDriver initFirefox() {
        FirefoxDriverManager.getInstance().arch32().setup();
        return new FirefoxDriver(defaultFirefoxOptions());
    }

    private WebDriver initChrome() {
        ChromeDriverManager.getInstance().setup();
        return new ChromeDriver(defaultChromeOptions());
    }

    private WebDriver initIE() {
        InternetExplorerDriverManager.getInstance().setup();
        return new InternetExplorerDriver(defaultIEOptions());
    }

    public static Dimension browserSizes;

    public static Function<WebDriver, WebDriver> webDriverSettings = driver -> {
        if (browserSizes == null) {
            if (any(asList("chrome", "internetexplorer"),
                    el -> driver.toString().toLowerCase().contains(el)))
                driver.manage().window().maximize();
        } else
            driver.manage().window().setSize(browserSizes);
        return driver;
    };

    public WebDriver getDriver(String driverName) {
        if (!drivers.keys().contains(driverName))
            if (drivers.count() == 0)
                registerDriver(driverName);
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

    public void reopenDriver() {
        reopenDriver(currentDriverName);
    }

    public void reopenDriver(String driverName) {
        MapArray<String, WebDriver> rDriver = runDrivers.get();
        if (rDriver.keys().contains(driverName)) {
            rDriver.get(driverName).close();
            rDriver.removeByKey(driverName);
            runDrivers.set(rDriver);
        }
        if (drivers.keys().contains(driverName))
            getDriver();
    }

    public void switchToDriver(String driverName) {
        if (drivers.keys().contains(driverName))
            currentDriverName = driverName;
        else
            throw exception("Can't switch to Webdriver '%s'. This Driver name not registered", driverName);
    }

    public void runApplication() {

    }

    public void closeApplication() {
    }

    public void get(String s) {

    }

    public String getCurrentUrl() {
        return null;
    }

    public String getTitle() {
        return null;
    }

    public List<WebElement> findElements(By by) {
        return null;
    }

    public WebElement findElement(By by) {
        return null;
    }

    public String getPageSource() {
        return null;
    }

    public void close() {
        for (Pair<String, WebDriver> driver : runDrivers.get())
            driver.value.quit();
        runDrivers.get().clear();
    }

    public void quit() {
        close();
    }

    public Set<String> getWindowHandles() {
        return null;
    }

    public String getWindowHandle() {
        return null;
    }
}