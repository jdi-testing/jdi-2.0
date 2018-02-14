package com.epam.jdi.tools.func;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

@FunctionalInterface
public interface JFunc<R> {
    R invoke() throws Exception;

    default R execute() {
        try {
            return invoke();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}