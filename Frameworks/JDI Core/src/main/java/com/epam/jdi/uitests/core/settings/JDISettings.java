package com.epam.jdi.uitests.core.settings;
/* The MIT License
 *
 * Copyright 2004-2017 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:

 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

 */

/**
 * Created by Roman Iovlev on 10.03.2017
 */
import com.epam.jdi.uitests.core.interfaces.IAsserter;
import com.epam.jdi.uitests.core.logger.ILogger;
import com.epam.jdi.uitests.core.logger.LogLevels;

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