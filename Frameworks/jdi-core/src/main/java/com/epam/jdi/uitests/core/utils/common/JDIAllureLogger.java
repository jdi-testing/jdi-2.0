package com.epam.jdi.uitests.core.utils.common;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.logger.JDILogger;
import ru.yandex.qatools.allure.events.StepFinishedEvent;
import ru.yandex.qatools.allure.events.StepStartedEvent;

import static com.epam.jdi.uitests.core.settings.JDISettings.exception;
import static com.epam.jdi.tools.StringUtils.format;
import static ru.yandex.qatools.allure.Allure.LIFECYCLE;

public class JDIAllureLogger extends JDILogger {
    public JDIAllureLogger(String name) {
        super(name);
    }
    @Override
    public void step(String s, Object... args) {
        try {
            LIFECYCLE.fire(new StepStartedEvent(format(s, args).replace(".", ",")));
            super.step(s, args);
            LIFECYCLE.fire(new StepFinishedEvent());
        } catch (Exception ex) { throw exception("Log Step with Allure issue: " + ex.getMessage()); }
    }
}
