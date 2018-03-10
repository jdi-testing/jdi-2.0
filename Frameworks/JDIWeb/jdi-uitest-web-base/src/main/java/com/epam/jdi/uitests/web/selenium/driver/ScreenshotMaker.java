package com.epam.jdi.uitests.web.selenium.driver;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.Timer;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

import static com.epam.jdi.tools.StringUtils.LINE_BREAK;
import static com.epam.jdi.uitests.core.settings.JDIData.testName;
import static com.epam.jdi.uitests.web.selenium.settings.WebSettings.getDriver;
import static com.epam.jdi.uitests.web.selenium.settings.WebSettings.getDriverFactory;
import static org.apache.commons.io.FileUtils.copyFile;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.openqa.selenium.OutputType.FILE;

public class ScreenshotMaker {
    public static String screensPath = "src/test/java/.logs/images/";
    public String pathSuffix = screensPath;

    public ScreenshotMaker() {
    }

    public ScreenshotMaker(String pathSuffix) {
        this.pathSuffix = pathSuffix;
    }

    public static String takeScreen() throws IOException {
        return new ScreenshotMaker().takeScreenshot();
    }

    public static String getValidUrl(String logPath) {
        if (isBlank((logPath)))
            return "";
        String result = logPath.replace("/", "\\");
        if (result.charAt(1) != ':')
            if (result.substring(0, 3).equals("..\\"))
                result = result.substring(2);
        if (result.charAt(0) != '\\')
            result = "\\" + result;
        return (result.charAt(result.length() - 1) == '\\')
                ? result
                : result + "\\";
    }

    public static String doScreenshotGetMessage() {
        String screenshotPath;
        try {
            screenshotPath = takeScreen();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return (screenshotPath.equals(""))
                ? "Failed to do Screenshot"
                : LINE_BREAK + "Add screenshot to: " + screenshotPath;
    }

    public String takeScreenshot() throws IOException {
        if (!getDriverFactory().hasRunDrivers())
            return "Can't do Screenshot. No Drivers run";
        String path = new File(".").getCanonicalPath() + getValidUrl(pathSuffix);
        String screensFilePath = getFileName(path + (testName != null ? testName : "screen") + Timer.nowDate().replace(":", "-"));
        new File(screensFilePath).getParentFile().mkdirs();
        File screensFile = ((TakesScreenshot) getDriver()).getScreenshotAs(FILE);
        copyFile(screensFile, new File(screensFilePath));
        return screensFilePath;
    }

    private String getFileName(String fileName) {
        int num = 1;
        String newName = fileName;
        while (new File(newName + ".jpg").exists())
            newName = fileName + "_" + num++;
        return newName + ".jpg";
    }
}