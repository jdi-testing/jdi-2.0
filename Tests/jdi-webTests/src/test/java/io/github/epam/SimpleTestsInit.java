package io.github.epam;

import com.epam.jdi.tools.logger.LogLevels;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebSite;
import com.epam.jdi.uitests.web.testng.testRunner.TestNGBase;
import org.testng.annotations.BeforeSuite;

import static com.epam.jdi.uitests.core.settings.JDISettings.logger;

public class SimpleTestsInit extends TestNGBase {
    @BeforeSuite(alwaysRun = true)
    public static void setUp() {
        logger.setLogLevel(LogLevels.STEP);
        WebSite.init(EpamGithubSite.class);
        EpamGithubSite.open();
        logger.info("Run Tests");
    }


}
