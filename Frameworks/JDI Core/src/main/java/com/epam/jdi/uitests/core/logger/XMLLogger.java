package com.epam.jdi.uitests.core.logger;

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
import com.epam.jdi.tools.func.JAction;
import com.epam.jdi.tools.func.JFunc;
import org.slf4j.Logger;
import org.slf4j.Marker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.epam.jdi.uitests.core.logger.LogLevels.*;
import static java.lang.Thread.currentThread;
import static org.slf4j.LoggerFactory.getLogger;

public class XMLLogger implements ILogger {
    public XMLLogger() {
        logger = getLogger("XML Logger");
        this.name = "XML Logger";
    }

    public XMLLogger(String name) {
        logger = getLogger(name);
        this.name = name;
    }

    public XMLLogger(Class clazz) {
        logger = getLogger(clazz);
        this.name = clazz.getSimpleName();
    }

    private LogLevels settingslogLevel = INFO;
    public LogLevels logLevel = settingslogLevel;

    public void logOff(JAction action) {
        logOff(() -> {
            action.invoke();
            return null;
        });
    }

    public <T> T logOff(JFunc<T> func) {
        if (logLevel == OFF) {
            try {
                return func.invoke();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        logLevel = OFF;
        T result;
        try {
            result = func.invoke();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        logLevel = settingslogLevel;
        return result;
    }

    public void setLogLevel(LogLevels logLevel) {
        this.logLevel = logLevel;
        this.settingslogLevel = logLevel;
    }

    private String name;
    private Logger logger;
    public String timeFormat = "";
    private List<Long> multiThread = new ArrayList<>();

    private String getRecord(String record, LogLevels level) {
        String result = "<" + level.toString().toLowerCase();
        if (!timeFormat.equals(""))
            result += " t=" + new SimpleDateFormat(timeFormat).format(new Date());
        if (!multiThread.contains(currentThread().getId()))
            multiThread.add(currentThread().getId());
        if (multiThread.size() > 1)
            result += " id=" + currentThread().getId();
        return result + ">" + record + "</" + level.toString().toLowerCase() + ">";
    }

    public String getName() {
        return name;
    }

    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    public void trace(String s) {
        if (logLevel.equalOrLessThan(TRACE))
            logger.trace(getRecord(s, TRACE));
    }

    public void trace(String s, Object o) {
        if (logLevel.equalOrLessThan(TRACE))
            logger.trace(getRecord(s, TRACE), o);
    }

    public void trace(String s, Object o, Object o1) {
        if (logLevel.equalOrLessThan(TRACE))
            logger.trace(getRecord(s, TRACE), o, o1);
    }

    public void trace(String s, Object... objects) {
        if (logLevel.equalOrLessThan(TRACE))
            logger.trace(getRecord(s, TRACE), objects);
    }

    public void trace(String s, Throwable throwable) {
        if (logLevel.equalOrLessThan(TRACE))
            logger.trace(getRecord(s, TRACE), throwable);
    }

    public boolean isTraceEnabled(Marker marker) {
        return logger.isTraceEnabled(marker);
    }

    public void trace(Marker marker, String s) {
        if (logLevel.equalOrLessThan(TRACE))
            logger.trace(marker, getRecord(s, TRACE));

    }

    public void trace(Marker marker, String s, Object o) {
        if (logLevel.equalOrLessThan(TRACE))
            logger.trace(marker, getRecord(s, TRACE), o);

    }

    public void trace(Marker marker, String s, Object o, Object o1) {
        if (logLevel.equalOrLessThan(TRACE))
            logger.trace(marker, getRecord(s, TRACE), o, o1);

    }

    public void trace(Marker marker, String s, Object... objects) {
        if (logLevel.equalOrLessThan(TRACE))
            logger.trace(marker, getRecord(s, TRACE), objects);

    }

    public void trace(Marker marker, String s, Throwable throwable) {
        if (logLevel.equalOrLessThan(TRACE))
            logger.trace(marker, getRecord(s, TRACE), throwable);
    }

    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    public void debug(String s) {
        if (logLevel.equalOrLessThan(DEBUG))
            logger.debug(getRecord(s, DEBUG));
    }

    public void debug(String s, Object o) {
        if (logLevel.equalOrLessThan(DEBUG))
            logger.debug(getRecord(s, DEBUG), o);
    }

    public void debug(String s, Object o, Object o1) {
        if (logLevel.equalOrLessThan(DEBUG))
            logger.debug(getRecord(s, DEBUG), o, o1);

    }

    public void debug(String s, Object... objects) {
        if (logLevel.equalOrLessThan(DEBUG))
            logger.debug(getRecord(s, DEBUG), objects);

    }

    public void debug(String s, Throwable throwable) {
        if (logLevel.equalOrLessThan(DEBUG))
            logger.debug(getRecord(s, DEBUG), throwable);
    }

    public boolean isDebugEnabled(Marker marker) {
        return logger.isDebugEnabled(marker);
    }

    public void debug(Marker marker, String s) {
        if (logLevel.equalOrLessThan(DEBUG))
            logger.debug(marker, getRecord(s, DEBUG));

    }

    public void debug(Marker marker, String s, Object o) {
        if (logLevel.equalOrLessThan(DEBUG))
            logger.debug(marker, getRecord(s, DEBUG), o);

    }

    public void debug(Marker marker, String s, Object o, Object o1) {
        if (logLevel.equalOrLessThan(DEBUG))
            logger.debug(marker, getRecord(s, DEBUG), o, o1);

    }

    public void debug(Marker marker, String s, Object... objects) {
        if (logLevel.equalOrLessThan(DEBUG))
            logger.debug(marker, getRecord(s, DEBUG), objects);

    }

    public void debug(Marker marker, String s, Throwable throwable) {
        if (logLevel.equalOrLessThan(DEBUG))
            logger.debug(marker, getRecord(s, DEBUG), throwable);
    }

    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    public void step(String s) {
        if (logLevel.equalOrLessThan(STEP))
            logger.info(getRecord(s, STEP));
    }
    public void info(String s) {
        if (logLevel.equalOrLessThan(INFO))
            logger.info(getRecord(s, INFO));
    }

    public void info(String s, Object o) {
        if (logLevel.equalOrLessThan(INFO))
            logger.info(getRecord(s, INFO), o);
    }

    public void info(String s, Object o, Object o1) {
        if (logLevel.equalOrLessThan(INFO))
            logger.info(getRecord(s, INFO), o, o1);

    }

    public void info(String s, Object... objects) {
        logger.info(getRecord(s, INFO), objects);

    }

    public void info(String s, Throwable throwable) {
        if (logLevel.equalOrLessThan(INFO))
            logger.info(getRecord(s, INFO), throwable);
    }

    public boolean isInfoEnabled(Marker marker) {
        return logger.isInfoEnabled(marker);
    }

    public void info(Marker marker, String s) {
        if (logLevel.equalOrLessThan(INFO))
            logger.info(marker, getRecord(s, INFO));

    }

    public void info(Marker marker, String s, Object o) {
        if (logLevel.equalOrLessThan(INFO))
            logger.info(marker, getRecord(s, INFO), o);

    }

    public void info(Marker marker, String s, Object o, Object o1) {
        if (logLevel.equalOrLessThan(INFO))
            logger.info(marker, getRecord(s, INFO), o, o1);

    }

    public void info(Marker marker, String s, Object... objects) {
        if (logLevel.equalOrLessThan(INFO))
            logger.info(marker, getRecord(s, INFO), objects);

    }

    public void info(Marker marker, String s, Throwable throwable) {
        if (logLevel.equalOrLessThan(INFO))
            logger.info(marker, getRecord(s, INFO), throwable);
    }

    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    public void warn(String s) {
        if (logLevel.equalOrLessThan(WARNING))
            logger.warn(getRecord(s, WARNING));
    }

    public void warn(String s, Object o) {
        if (logLevel.equalOrLessThan(WARNING))
            logger.warn(getRecord(s, WARNING), o);
    }

    public void warn(String s, Object o, Object o1) {
        if (logLevel.equalOrLessThan(WARNING))
            logger.warn(getRecord(s, WARNING), o, o1);

    }

    public void warn(String s, Object... objects) {
        if (logLevel.equalOrLessThan(WARNING))
            logger.warn(getRecord(s, WARNING), objects);

    }

    public void warn(String s, Throwable throwable) {
        if (logLevel.equalOrLessThan(WARNING))
            logger.warn(getRecord(s, WARNING), throwable);
    }

    public boolean isWarnEnabled(Marker marker) {
        return logger.isWarnEnabled(marker);
    }

    public void warn(Marker marker, String s) {
        if (logLevel.equalOrLessThan(WARNING))
            logger.warn(marker, getRecord(s, WARNING));

    }

    public void warn(Marker marker, String s, Object o) {
        if (logLevel.equalOrLessThan(WARNING))
            logger.warn(marker, getRecord(s, WARNING), o);

    }

    public void warn(Marker marker, String s, Object o, Object o1) {
        if (logLevel.equalOrLessThan(WARNING))
            logger.warn(marker, getRecord(s, WARNING), o, o1);

    }

    public void warn(Marker marker, String s, Object... objects) {
        if (logLevel.equalOrLessThan(WARNING))
            logger.warn(marker, getRecord(s, WARNING), objects);

    }

    public void warn(Marker marker, String s, Throwable throwable) {
        if (logLevel.equalOrLessThan(WARNING))
            logger.warn(marker, getRecord(s, WARNING), throwable);
    }

    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    public void error(String s) {
        if (logLevel.equalOrLessThan(ERROR))
            logger.error(getRecord(s, ERROR));
    }

    public void error(String s, Object o) {
        if (logLevel.equalOrLessThan(ERROR))
            logger.error(getRecord(s, ERROR), o);
    }

    public void error(String s, Object o, Object o1) {
        if (logLevel.equalOrLessThan(ERROR))
            logger.error(getRecord(s, ERROR), o, o1);

    }

    public void error(String s, Object... objects) {
        if (logLevel.equalOrLessThan(ERROR))
            logger.error(getRecord(s, ERROR), objects);

    }

    public void error(String s, Throwable throwable) {
        if (logLevel.equalOrLessThan(ERROR))
            logger.error(getRecord(s, ERROR), throwable);
    }

    public boolean isErrorEnabled(Marker marker) {
        return logger.isErrorEnabled(marker);
    }

    public void error(Marker marker, String s) {
        if (logLevel.equalOrLessThan(ERROR))
            logger.error(marker, getRecord(s, ERROR));
    }

    public void error(Marker marker, String s, Object o) {
        if (logLevel.equalOrLessThan(ERROR))
            logger.error(marker, getRecord(s, ERROR), o);
    }

    public void error(Marker marker, String s, Object o, Object o1) {
        if (logLevel.equalOrLessThan(ERROR))
            logger.error(marker, getRecord(s, ERROR), o, o1);
    }

    public void error(Marker marker, String s, Object... objects) {
        if (logLevel.equalOrLessThan(ERROR))
            logger.error(marker, getRecord(s, ERROR), objects);
    }

    public void error(Marker marker, String s, Throwable throwable) {
        if (logLevel.equalOrLessThan(ERROR))
            logger.error(marker, getRecord(s, ERROR), throwable);
    }

    public void step(String message, JAction action) {
        step("<info>" + message);
        action.execute();
        step("</info>");
    }
    /**
     * Adds logs with "info" level into logging file and console and executes another internal method.
     *
     * @param message Message to be shown in logging file and console.
     * @param action  action expression without arguments which will be executed.
     */
    public void info(String message, JAction action) {
        info("<info>" + message);
        action.execute();
        info("</info>");
    }
    /**
     * Adds logs with "warn" level into logging file and console and executes another internal method.
     * @param message Message to be shown in logging file and console.
     * @param action action expression without arguments which will be executed.
     */
    public void warn(String message, JAction action) {
        warn("<warn>" + message);
        action.execute();
        warn("</warn>");
    }

    /**
     * Adds logs with "error" level into logging file and console and executes another internal method.
     * @param message Message to be shown in logging file and console.
     * @param action action expression without arguments which will be executed.
     */
    public void error(String message, JAction action) {
        error("<error>" + message);
        action.execute();
        error("</error>");
    }

    /**
     * Adds logs with "debug" level into logging file and console and executes another internal method.
     * @param message Message to be shown in logging file and console.
     * @param action action expression without arguments which will be executed.
     */
    public void debug(String message, JAction action) {
        debug("<debug>" + message);
        action.execute();
        debug("</debug>");
    }

    /**
     * Adds logs with "trace" level into logging file and executes another internal method and console.
     * @param message Message to be shown in logging file and console.
     * @param action action expression without arguments which will be executed.
     */
    public void trace(String message, JAction action) {
        trace("<trace>" + message);
        action.execute();
        trace("</trace>");
    }

}