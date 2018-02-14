package com.epam.jdi.uitests.core.settings;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.logger.ILogger;
import com.epam.jdi.tools.logger.LogLevels;
import com.epam.jdi.uitests.core.interfaces.IAsserter;

import java.io.IOException;

import static com.epam.jdi.tools.PropertyReader.fillAction;
import static com.epam.jdi.uitests.core.settings.JDIPropertiesReader.getProperties;
import static java.lang.Integer.parseInt;

public abstract class JDISettings {
    public static ILogger logger;
    public static IAsserter asserter;
    public static TimeoutSettings timeouts = new TimeoutSettings();
    public static boolean shortLogMessagesFormat = true;
    public static String jdiSettingsPath = "test.properties";
    public static boolean useCache = false;

    protected JDISettings() {
    }

    public static void toLog(String message, LogLevels level) {
        switch (level) {
            case INFO:
                logger.info(message);
                break;
            case ERROR:
                logger.error(message);
                break;
            default:
                logger.debug(message);
        }
    }

    public static synchronized void initFromProperties() {
        try {
            getProperties(jdiSettingsPath);
        } catch (Exception ex) { throw new RuntimeException(ex); }
        fillAction(p -> shortLogMessagesFormat = p.toLowerCase().equals("short"), "log.message.format");
        fillAction(p -> useCache =
                p.toLowerCase().equals("true") || p.toLowerCase().equals("1"), "cache");
        fillAction(p -> timeouts.setDefaultTimeoutSec(parseInt(p)), "timeout.wait.element");
        // fillAction(p -> timeouts.waitPageLoadSec = parseInt(p), "timeout.wait.pageLoad");
    }

    public static void initFromProperties(String propertyPath) throws IOException {
        jdiSettingsPath = propertyPath;
        initFromProperties();
    }

    public static RuntimeException exception(String msg, Object... args) {
        return asserter.exception(msg, args);
    }
}