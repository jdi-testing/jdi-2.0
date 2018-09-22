package com.epam.jdi.uitests.core.logger;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JAction;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.apache.logging.log4j.LogManager.getLogger;

public class XMLLogger {
    private Logger log;
    private SimpleDateFormat dateFormat;

    public XMLLogger() {
        this.log = getLogger("JDI");
    }

    public XMLLogger(String name) {
        this.log = getLogger(name);
    }

    public XMLLogger(Class clazz) {
        this.log = getLogger(clazz);
    }

    public XMLLogger(SimpleDateFormat timePatternLayout) {
        this.log = getLogger("JDI");
        this.dateFormat = timePatternLayout;
    }

    public XMLLogger(String name, SimpleDateFormat timePatternLayout) {
        this.log = getLogger(name);
        this.dateFormat = timePatternLayout;
    }

    public XMLLogger(Class clazz, SimpleDateFormat timePatternLayout) {
        this.log = getLogger(clazz);
        this.dateFormat = timePatternLayout;
    }

    /**
     * Adds logs with "trace" level into logging file and console and executes another internal method.
     * @param message Message to be shown in logging file and console.
     * @param lambda Lambda expression without arguments which will be executed.
     */
    public void trace(String message, JAction lambda) {
        log.trace("<trace" + getFormattedDate() + ">" + message);
        lambda.execute();
        log.trace("</trace>");
    }

    /**
     * Adds logs with "trace" level into logging file and console.
     * @param message Message to be shown in logging file and console.
     */
    public void trace(String message) {
        log.trace("<trace" + getFormattedDate() + ">"
                + message + "</trace>");
    }

    /**
     * Adds logs with "debug" level into logging file and console and executes another internal method.
     * @param message Message to be shown in logging file and console.
     * @param lambda Lambda expression without arguments which will be executed.
     */
    public void debug(String message, JAction lambda) {
        log.debug("<debug" + getFormattedDate() + ">" + message);
        lambda.execute();
        log.debug("</debug>");
    }

    /**
     * Adds logs with "debug" level into logging file and console.
     * @param message Message to be shown in logging file and console.
     */
    public void debug(String message) {
        log.debug("<debug" + getFormattedDate() + ">"
                + message + "</debug>");
    }

    /**
     * Adds logs with "info" level into logging file and console and executes another internal method.
     * @param message Message to be shown in logging file and console.
     * @param lambda Lambda expression without arguments which will be executed.
     */
    public void info(String message, JAction lambda) {
        log.info("<info" + getFormattedDate() + ">" + message);
        lambda.execute();
        log.info("</info>");
    }

    /**
     * Adds logs with "info" level into logging file and console.
     * @param message Message to be shown in logging file and console.
     */
    public void info(String message) {
        log.info("<info" + getFormattedDate() + ">"
                + message + "</info>");
    }

    /**
     * Adds logs with "warn" level into logging file and console and executes another internal method.
     * @param message Message to be shown in logging file and console.
     * @param lambda Lambda expression without arguments which will be executed.
     */
    public void warn(String message, JAction lambda) {
        log.warn("<warn" + getFormattedDate() + ">" + message);
        lambda.execute();
        log.warn("</warn>");
    }

    /**
     * Adds logs with "warn" level into logging file and console.
     * @param message Message to be shown in logging file and console.
     */
    public void warn(String message) {
        log.warn("<warn" + getFormattedDate() + ">"
                + message + "</warn>");
    }

    /**
     * Adds logs with "error" level into logging file and console and executes another internal method.
     * @param message Message to be shown in logging file and console.
     * @param lambda Lambda expression without arguments which will be executed.
     */
    public void error(String message, JAction lambda) {
        log.error("<error" + getFormattedDate() + ">" + message);
        lambda.execute();
        log.error("</error>");
    }

    /**
     * Adds logs with "error" level into logging file and console.
     * @param message Message to be shown in logging file and console.
     */
    public void error(String message) {
        log.error("<error" + getFormattedDate() + ">"
                + message + "</error>");
    }

    /**
     * Adds logs with "fatal" level into logging file and console and executes another internal method.
     * @param message Message to be shown in logging file and console.
     * @param lambda Lambda expression without arguments which will be executed.
     */
    public void fatal(String message, JAction lambda) {
        log.fatal("<fatal" + getFormattedDate() + ">" + message);
        lambda.execute();
        log.fatal("</fatal>");
    }

    /**
     * Adds logs with "fatal" level into logging file and console.
     * @param message Message to be shown in logging file and console.
     */
    public void fatal(String message) {
        log.fatal("<fatal" + getFormattedDate() + ">"
                + message + "</fatal>");
    }

    /**
     * Adds log with one of the possible levels into logging file and console and executes lambda expression.
     * @param logLevel Provides the information about the log level.
     * @param message Message to be shown in logging file.
     * @param lambda Lambda expression without arguments which will be executed.
     */
    public void log (LogLevels logLevel, String message, JAction lambda){
        switch(logLevel) {
            case ALL:
                trace(message, lambda);
                break;
            case TRACE:
                trace(message, lambda);
                break;
            case DEBUG:
                debug(message, lambda);
                break;
            case INFO:
                info(message, lambda);
                break;
            case WARNING:
                warn(message, lambda);
                break;
            case ERROR:
                error(message, lambda);
                break;
            case FATAL:
                fatal(message, lambda);
                break;
            case OFF :
                break;
        }
    }

    /**
     * Adds log with one of the possible levels into logging file and console
     * @param logLevel Provides the information about the log level.
     * @param message Message to be shown in logging file.
     */
    public void log(LogLevels logLevel, String message) {
        switch(logLevel) {
            case ALL:
            case TRACE:
                trace(message);
                break;
            case DEBUG:
                debug(message);
                break;
            case INFO:
                info(message);
                break;
            case WARNING:
                warn(message);
                break;
            case ERROR:
                error(message);
                break;
            case FATAL:
                fatal(message);
                break;
            case OFF :
                break;
        }
    }

    /**
     * Returns current formatted date
     * @return String - formatted date
     */
    private String getFormattedDate(){
        return dateFormat != null ? " t=\"" + dateFormat.format(new Date()) + "\"" : "";
    }
}