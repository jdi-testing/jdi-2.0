package com.epam.jdi.uitests.core.utils.common;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.logger.JDILogger;
import ru.yandex.qatools.allure.events.StepFinishedEvent;
import ru.yandex.qatools.allure.events.StepStartedEvent;

import static com.epam.jdi.tools.StringUtils.format;
import static com.epam.jdi.uitests.core.settings.JDISettings.exception;
import static ru.yandex.qatools.allure.Allure.LIFECYCLE;

/**
 * Logger class for Allure, extends JDILogger
 */
public class JDIAllureLogger extends JDILogger {

    /**
     * Allure logger constructor from name, extended from JDILogger
     * @param name String, logger name
     */
    public JDIAllureLogger(String name) {
        super(name);
    }

    /**
     * Logs test step
     * @param s - record to be logged
     * @param args - args
     */
    @Override
    public void step(String s, Object... args) {
        try {
            LIFECYCLE.fire(new StepStartedEvent(format(s, args).replace(".", ",")));
            super.step(s, args);
            LIFECYCLE.fire(new StepFinishedEvent());
        } catch (Exception ex) { throw exception("Log Step with Allure issue: " + ex.getMessage()); }
    }
}
