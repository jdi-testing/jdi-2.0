package com.epam.jdi.uitests.core.exceptions;

public class JDIUIException extends RuntimeException {
    public JDIUIException() {}
    public JDIUIException(String msg) { super(msg);}
    public JDIUIException(Throwable ex) { super(ex.getMessage()); }

}
