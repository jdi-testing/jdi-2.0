package com.epam.jdi.uitests.web.selenium.exceptions;

public class LocatorException extends RuntimeException {
    public LocatorException() {}
    public LocatorException(String msg) { super(msg);}
    public LocatorException(Throwable ex) { super(ex.getMessage()); }

}
