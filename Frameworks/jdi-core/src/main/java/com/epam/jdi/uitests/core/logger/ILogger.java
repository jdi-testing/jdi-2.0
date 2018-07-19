package com.epam.jdi.uitests.core.logger;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JAction;
import com.epam.jdi.tools.func.JFunc;

public interface ILogger {
    <T> T logOff(JFunc<T> action);
    void logOff(JAction action);
    void logOff();
    void logOn();
    void debug(String msg, Object... args);
    void info(String msg, Object... args);
    void step(String msg, Object... args);
    void error(String msg, Object... args);
    void toLog(String msg, LogLevels level);
    void setLogLevel(LogLevels levels);
    LogLevels getLogLevel();
}