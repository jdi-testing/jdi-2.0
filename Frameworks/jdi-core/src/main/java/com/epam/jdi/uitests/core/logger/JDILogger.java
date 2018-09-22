package com.epam.jdi.uitests.core.logger;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JAction;
import com.epam.jdi.tools.func.JFunc;
import com.epam.jdi.tools.map.MapArray;
import org.apache.logging.log4j.*;

import java.util.ArrayList;
import java.util.List;

import static com.epam.jdi.tools.StringUtils.format;
import static com.epam.jdi.uitests.core.logger.LogLevels.*;
import static java.lang.Thread.currentThread;
import static org.apache.logging.log4j.LogManager.getLogger;
import static org.apache.logging.log4j.core.config.Configurator.setRootLevel;

public class JDILogger implements ILogger {
    private static MapArray<String, JDILogger> loggers = new MapArray<>();
    private static Marker jdiMarker = MarkerManager.getMarker("JDI");
    private LogLevels logLevel = INFO;
    private LogLevels currentLevel = INFO;
    private int logOffDeepness = 0;
    private String name;
    private Logger logger;
    private List<Long> multiThread = new ArrayList<>();


    /**
     * Checks if logger in loggers map and add it if not
     * @param name - logger name
     * @return JDILogger - logger by it's name
     */
    public static JDILogger instance(String name) {
        if (!loggers.keys().contains(name))
            loggers.add(name, new JDILogger(name));
        return loggers.get(name);
    }

    /**
     * Creates default logger
     */
    public JDILogger() {
        this("JDI");
    }

    /**
     * Creates logger with specified name
     * @param name - logger name
     */
    public JDILogger(String name) {
        logger = getLogger(name);
        this.name = name;
    }

    /**
     * Creates logger with specified name
     * @param clazz - logger name
     */
    public JDILogger(Class clazz) {
        this(clazz.getSimpleName());
    }

    /**
     * Returns logger level
     * @return LogLevels
     */
    public LogLevels getLogLevel() {
        return logLevel;
    }

    /**
     * Sets log level
     * @param logLevel
     */
    public void setLogLevel(LogLevels logLevel) {
        this.logLevel = logLevel;
        setRootLevel(getLog4j2Level(logLevel));
    }

    /**
     * Switches lof off
     */
    public void logOff() {
        if (logOffDeepness == 0) {
            currentLevel = logLevel;
            logLevel = OFF;
        }
        logOffDeepness ++;
    }

    /**
     * Switches log on with default log level
     */
    public void logOn() {
        logOffDeepness --;
        if (logOffDeepness > 0) return;
        if (logOffDeepness == 0)
            logLevel = currentLevel;
        if (logOffDeepness <0)
            throw new LoggingException("Log Off Deepness to high. Please check that each logOff has appropriate logOn");
    }

    /**
     * Switches action off
     * @param action
     */
    public void logOff(JAction action) {
        logOff(() -> { action.invoke(); return null; });
    }

    /**
     * Switches function off
     * @param func
     * @param <T>
     * @return T
     */
    public <T> T logOff(JFunc<T> func) {
        LogLevels currentLevel = logLevel;
        if (logLevel == OFF) {
            try { return func.invoke();
            } catch (Exception ex) { throw new LoggingException(ex); }
        }
        logLevel = OFF;
        T result;
        try{ result = func.invoke(); }
        catch (Exception ex) {throw new LoggingException(ex); }
        logLevel = currentLevel;
        return result;
    }

    /**
     * Returns formatted record if tests are run in several threads other vise returns default record
     * @param record
     * @return String - record
     */
    private String getRecord(String record) {
        if (!multiThread.contains(currentThread().getId()))
            multiThread.add(currentThread().getId());
        return multiThread.size() > 1
                ? "[" + currentThread().getId() + "]" + record
                : record;
    }

    /**
     * Returns logger name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Logs test step
     * @param s - record to be logged
     * @param args - args
     */
    public void step(String s, Object... args) {
        if (logLevel.equalOrLessThan(STEP))
            logger.log(Level.forName("STEP", 350), jdiMarker, getRecord(format(s, args)));
    }

    /**
     * Logs test trace
     * @param s - record to be logged
     * @param args - args
     */
    public void trace(String s, Object... args) {
        if (logLevel.equalOrLessThan(TRACE))
            logger.trace(jdiMarker, getRecord(format(s, args)));
    }

    /**
     * Logs debug level
     * @param s - record to be logged
     * @param args - args
     */
    public void debug(String s, Object... args) {
        if (logLevel.equalOrLessThan(DEBUG))
            logger.debug(jdiMarker, getRecord(format(s, args)));
    }

    /**
     * Logs info level
     * @param s - record to be logged
     * @param args - args
     */
    public void info(String s, Object... args) {
        if (logLevel.equalOrLessThan(INFO))
            logger.info(jdiMarker, getRecord(format(s, args)));
    }

    /**
     * Logs error leel
     * @param s - record to be logged
     * @param args - args
     */
    public void error(String s, Object... args) {
        if (logLevel.equalOrLessThan(ERROR))
            logger.error(jdiMarker, getRecord(format(s, args)));
    }

    /**
     * Add message to log
     * @param msg - message to be added
     * @param level - log level
     */
    public void toLog(String msg, LogLevels level) {
        if (logLevel.equalOrLessThan(level))
            switch (level) {
                case ERROR: error(msg); break;
                case STEP: step(msg); break;
                case INFO: info(msg); break;
                case DEBUG: debug(msg); break;
                default: throw new LoggingException("Unknown log level: " + level);
            }
    }
}