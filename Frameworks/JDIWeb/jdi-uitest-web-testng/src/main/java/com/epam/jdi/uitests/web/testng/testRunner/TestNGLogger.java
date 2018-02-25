package com.epam.jdi.uitests.web.testng.testRunner;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.map.MapArray;
import com.epam.jdi.uitests.core.utils.common.JDIAllureLogger;

import static org.testng.Reporter.log;

public class TestNGLogger extends JDIAllureLogger {
    private static MapArray<String, TestNGLogger> loggers = new MapArray<>();
    public static TestNGLogger instance(String name) {
        if (!loggers.keys().contains(name))
            loggers.add(name, new TestNGLogger(name));
        return loggers.get(name);
    }

    public TestNGLogger(String name) {
        super(name);
    }

    @Override
    public void trace(String s, Object... objects) {
        super.trace(s, objects);
        log(String.format("%s %s", 0, s));
    }

    @Override
    public void debug(String s, Object... objects) {
        super.debug(s, objects);
        log(String.format("%s %s", 0, s));
    }

    @Override
    public void info(String s, Object... objects) {
        super.info(s, objects);
        log(String.format("%s %s", 1, s));
    }
    @Override
    public void step(String s, Object... objects) {
        super.step(s, objects);
        log(String.format("%s %s", 1, s));
    }

    @Override
    public void error(String s, Object... objects) {
        super.error(s, objects);
        log(String.format("%s %s", 1, s));
    }
}
