package com.epam.jdi.uitests.web.selenium.exceptions;

/**
 * LocatorException
 */
public class LocatorException extends RuntimeException {
    /**
     * Constructs default LocatorException
     */
    public LocatorException() {}

    /**
     * Constructs LocatorException with message
     * @param msg message
     */
    public LocatorException(String msg) { super(msg);}

    /**
     * Constructs LocatorException with exception
     * @param ex exception
     */
    public LocatorException(Throwable ex) { super(ex.getMessage()); }

}
