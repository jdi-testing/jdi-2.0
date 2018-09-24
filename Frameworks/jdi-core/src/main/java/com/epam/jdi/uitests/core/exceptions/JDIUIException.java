package com.epam.jdi.uitests.core.exceptions;

/**
 * {@code JDIUIException} thrown to wrap exceptions
 * caused by UI connection problems
 */
public class JDIUIException extends RuntimeException {
    public JDIUIException() {
    }

    public JDIUIException(String msg) {
        super(msg);
    }

    public JDIUIException(Throwable ex) {
        super(ex.getMessage());
    }

}
