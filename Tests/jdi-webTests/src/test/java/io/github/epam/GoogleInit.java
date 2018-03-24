package io.github.epam;

import com.epam.jdi.uitests.web.selenium.elements.composite.WebSite;
import com.epam.jdi.uitests.web.testng.testRunner.TestNGBase;
import com.google.GoogleSite;
import org.testng.annotations.BeforeSuite;

import static com.epam.jdi.tools.logger.LogLevels.INFO;
import static com.epam.jdi.uitests.core.settings.JDISettings.logger;

public class GoogleInit extends TestNGBase {
    @BeforeSuite(alwaysRun = true)
    public static void setUp() {
        logger.setLogLevel(INFO);
        WebSite.init(GoogleSite.class);
        GoogleSite.open();
        logger.info("Run Tests");
    }


}
