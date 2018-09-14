package com.epam.jdi.uitests.core.exceptions;

/**
 * {@code JDIUIException} is a wrapper or {@code java.lang.RuntimeException}.
 * Using for
 */
public class JDIUIException extends RuntimeException {
    public JDIUIException() {}
    public JDIUIException(String msg) { super(msg);}
    public JDIUIException(Throwable ex) { super(ex.getMessage()); }

}
