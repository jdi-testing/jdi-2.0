package com.epam.jdi.uitests.core.settings;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.exceptions.JDIUIException;
import com.epam.jdi.uitests.core.interfaces.IAsserter;
import com.epam.jdi.uitests.core.logger.ILogger;
import com.epam.jdi.uitests.core.utils.common.JDIAllureLogger;

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

    /**
     * Checks if properties can be read and throws JDIUIException, if they can't.
     * Adds a logger named JDI if it's not present in loggers map.
     * Sets static fields
     *      boolean shortLogMessagesFormat,
     *      boolean useCache,
     *      TimeoutSettings timeouts
     * according to values from the file set by jdiSettingsPath.
     */
    public static synchronized void initFromProperties() {
        try {
            getProperties(jdiSettingsPath);
        } catch (Exception ex) { throw new JDIUIException(ex); }
        logger = JDIAllureLogger.instance("JDI");
        fillAction(p -> shortLogMessagesFormat = p.toLowerCase().equals("short"), "log.message.format");
        fillAction(p -> useCache =
                p.toLowerCase().equals("true") || p.toLowerCase().equals("1"), "cache");
        fillAction(p -> timeouts.setDefaultTimeoutSec(parseInt(p)), "timeout.wait.element");
    }

    /**
     * Sets a path to the settings file and assigns properties from it to static fields
     * shortLogMessagesFormat, useCache, timeouts
     * @param propertyPath path to a jdi settings file
     */
    public static void initFromProperties(String propertyPath) {
        jdiSettingsPath = propertyPath;
        initFromProperties();
    }

    /**
     * @param msg a format string
     * @param args arguments referenced by the format specifiers in the format string
     * @return AssertionError with a message formatted from method parameters by the asserter
     */
    public static AssertionError exception(String msg, Object... args) {
        return asserter.exception(msg, args);
    }
}