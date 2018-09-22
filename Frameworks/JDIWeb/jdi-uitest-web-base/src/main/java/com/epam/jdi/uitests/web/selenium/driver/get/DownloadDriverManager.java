package com.epam.jdi.uitests.web.selenium.driver.get;

import io.github.bonigarcia.wdm.WebDriverManager;

import static com.epam.jdi.uitests.core.settings.JDISettings.exception;
import static com.epam.jdi.uitests.core.settings.JDISettings.logger;
import static com.epam.jdi.uitests.web.selenium.driver.get.DriverData.getOs;
import static com.epam.jdi.uitests.web.selenium.driver.get.OsTypes.WIN;
import static org.openqa.selenium.remote.BrowserType.*;


/**
 * Created by Roman_Iovlev on 11/28/2017.
 */
public class DownloadDriverManager {
    public static boolean DOWNLOAD_DRIVER = true;

    /**
     * Checks if version correct
     * @param version
     * @return boolean
     */
    private static boolean hasVersion(String version) {
        char c = version.charAt(0);
        return (c >= '0' && c <= '9');
    }

    /**
     * Downloads driver
     * @param driverType - drivers type
     * @param platform - platform for tests
     * @param version - drivers version
     */
    public static void downloadDriver(String driverType,
          Platform platform, String version) {
        WebDriverManager wdm;
        try {
            String driverName = driverType;
            switch (driverType) {
                case CHROME:
                    wdm = WebDriverManager.chromedriver(); break;
                case FIREFOX:
                    wdm = WebDriverManager.firefoxdriver(); break;
                case IE:
                    wdm = WebDriverManager.iedriver(); break;
                case EDGE:
                    wdm = WebDriverManager.edgedriver(); break;
                case PHANTOMJS:
                    wdm = WebDriverManager.phantomjs(); break;
                case OPERA:
                    wdm = WebDriverManager.operadriver(); break;
                default:
                    throw exception("Unknown driver: " + driverType);
            }
            if (getOs() == WIN) {
                switch (platform) {
                    case X32:
                        wdm = wdm.arch32();
                        break;
                    case X64:
                        wdm = wdm.arch64();
                        break;
                }
                driverName += " " + platform;
            }
            if (hasVersion(version)) {
                wdm = wdm.version(version);
                driverName += " " + version;
            }
            wdm.setup();
            logger.info("Download driver: " +  driverName);
        } catch (Exception ex) {
            throw exception("Can't download latest driver for " + driverType
                    + ". Exception " + ex.getMessage());
        }
    }
}
