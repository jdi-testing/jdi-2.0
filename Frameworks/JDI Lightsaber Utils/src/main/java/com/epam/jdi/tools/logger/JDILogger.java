package com.epam.jdi.tools.logger;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JAction;
import com.epam.jdi.tools.func.JFunc;
import com.epam.jdi.tools.map.MapArray;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.epam.jdi.tools.logger.LogLevels.*;
import static java.lang.String.format;
import static java.lang.Thread.currentThread;
import static org.apache.logging.log4j.LogManager.getLogger;
import static org.apache.logging.log4j.core.config.Configurator.setRootLevel;

public class JDILogger implements ILogger {
    private static MapArray<String, JDILogger> loggers = new MapArray<>();

    public static JDILogger instance(String name) {
        if (!loggers.keys().contains(name))
            loggers.add(name, new JDILogger(name));
        return loggers.get(name);
    }

    public JDILogger() {
        this("JDI Logger");
    }
    public JDILogger(String name) {
        logger = getLogger(name);
        this.name = name;
    }
    public JDILogger(Class clazz) {
        this(clazz.getSimpleName());
    }

    private LogLevels logLevel = INFO;

    public LogLevels getLogLevel() {
        return logLevel;
    }
    public void setLogLevel(LogLevels logLevel) {
        this.logLevel = logLevel;
        setRootLevel(getLog4j2Level(logLevel));
    }
    private LogLevels currentLevel = INFO;
    private int logOffDeepness = 0;
    public void logOff() {
        if (logOffDeepness == 0) {
            currentLevel = logLevel;
            logLevel = OFF;
        }
        logOffDeepness ++;
    }
    public void logOn() {
        logOffDeepness --;
        if (logOffDeepness > 0) return;
        if (logOffDeepness == 0)
            logLevel = currentLevel;
        if (logOffDeepness <0)
            throw new RuntimeException("Log Off Deepness to high. Please check that each logOff has appropriate logOn");
    }
    public void logOff(JAction action) {
        logOff(() -> { action.invoke(); return null; });
    }
    public <T> T logOff(JFunc<T> func) {
        LogLevels currentLevel = logLevel;
        if (logLevel == OFF) {
            try { return func.invoke();
            } catch (Exception ex) { throw new RuntimeException(ex); }
        }
        logLevel = OFF;
        T result;
        try{ result = func.invoke(); }
        catch (Exception ex) {throw new RuntimeException(ex); }
        logLevel = currentLevel;
        return result;
    }
    private String name;
    private Logger logger;
    private List<Long> multiThread = new ArrayList<>();
    private String getRecord(String record) {
        if (!multiThread.contains(currentThread().getId()))
            multiThread.add(currentThread().getId());
        return multiThread.size() > 1
                ? "[JDI: " + currentThread().getId() + "]" + record
                : "[JDI] " + record;
    }

    public String getName() {
        return name;
    }

    public void step(String s, Object... args) {
        if (logLevel.equalOrLessThan(STEP))
            logger.log(Level.forName("STEP", 350), getRecord(format(s, args)));
    }
    public void trace(String s, Object... args) {
        if (logLevel.equalOrLessThan(TRACE))
            logger.trace(getRecord(format(s, args)));
    }
    public void debug(String s, Object... args) {
        if (logLevel.equalOrLessThan(DEBUG))
            logger.debug(getRecord(format(s, args)));
    }
    public void info(String s, Object... args) {
        if (logLevel.equalOrLessThan(INFO))
            logger.info(getRecord(format(s, args)));
    }
    public void error(String s, Object... args) {
        if (logLevel.equalOrLessThan(ERROR))
            logger.error(getRecord(format(s, args)));
    }

    public void toLog(String msg, LogLevels level) {
        if (logLevel.equalOrLessThan(level))
            switch (level) {
                case ERROR: error(msg); break;
                case STEP: step(msg); break;
                case INFO: info(msg); break;
                case DEBUG: debug(msg); break;
                default: throw new RuntimeException("Unknown log level: " + level);
            }
    }
}
