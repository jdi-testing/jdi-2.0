package com.epam.jdi.tools.func;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

@FunctionalInterface
public interface JFunc1<T, R> {
    R invoke(T val) throws Exception;
    default R execute(T val) {
        try {
            return invoke(val);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}