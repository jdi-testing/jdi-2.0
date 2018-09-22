package com.epam.jdi.uitests.web.selenium.driver;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import static com.epam.jdi.uitests.core.settings.JDISettings.asserter;
import static org.openqa.selenium.os.WindowsUtils.killByName;

public final class WebDriverUtils {

    private WebDriverUtils() {
    }

    //TODO Add OS type and current user check.

    /**
     * Kills all webdrivers
     */
    public static void killAllRunWebBrowsers() {
        asserter.ignore(() -> {
            killByName("chromedriver.exe");
            killByName("geckodriver.exe");
            killByName("IEDriverServer.exe");
            killByName("MicrosoftWebDriver.exe");
        });
    }
}