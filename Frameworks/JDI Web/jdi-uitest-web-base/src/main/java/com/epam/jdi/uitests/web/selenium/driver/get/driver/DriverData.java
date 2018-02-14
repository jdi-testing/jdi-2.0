package com.epam.jdi.uitests.web.selenium.driver.get.driver;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import java.io.File;

public class DriverData {
    //TODO GET LATEST DRIVER USING OPEN SOURCE SOLUTION https://github.com/epam/JDI/issues/82
    public static final String FOLDER_PATH = new File("").getAbsolutePath() + "\\src\\main\\resources\\driver\\";
    static final String TEMP_FOLDER = FOLDER_PATH + "temp\\";
    public static final String getChromeDriverPath (String folderPath) {
        return checkOS().equals("win") ? folderPath + "\\chromedriver.exe" : folderPath + "\\chromedriver";
    }
    public static final String getIEDriverPath (String folderPath) {
        return folderPath + "\\IEDriverServer.exe";
    }
    public static final String getFirefoxDriverPath (String folderPath) {
        return checkOS().equals("win") ? folderPath + "\\geckodriver.exe" : folderPath + "\\geckodriver";
    }

    static final String CHROME_STORAGE = "http://chromedriver.storage.googleapis.com/";
    static final String CHROME_MAC_DRIVER = "chromedriver_mac64.zip";
    static final String CHROME_NIX_DRIVER = "chromedriver_linux64.zip";
    static final String CHROME_WIN_DRIVER = "chromedriver_win32.zip";
    static final String FIREFOX_STORAGE = "https://github.com/mozilla/geckodriver/releases/download/v{0}/geckodriver-v{0}-";
    static final String FIREFOX_ZIP = "geckodriver-v{0}-";
    static final String FIREFOX_MAC_DRIVER = "macos.tar.gz";
    static final String FIREFOX_NIX64_DRIVER = "linux64.tar.gz";
    static final String FIREFOX_WIN64_DRIVER = "win64.zip";//
    //
    public static String DRIVER_VERSION = "";
    static final String IE_WIN_DRIVER_URL = "http://selenium-release.storage.googleapis.com/{0}/IEDriverServer_x64_{0}.1.zip";

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

}
