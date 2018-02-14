package com.epam.jdi.tools.func;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

@FunctionalInterface
public interface JAction1<T> {
    void invoke(T val) throws Exception;

    default void execute(T val) {
        try {
            invoke(val);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }
}