package com.epam.jdi.tools.func;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

@FunctionalInterface
public interface JAction7<T1, T2, T3, T4, T5, T6, T7> {
    void invoke(T1 val1, T2 val2, T3 val3, T4 val4, T5 val5, T6 val6, T7 val7) throws Exception;

    default void execute(T1 val1, T2 val2, T3 val3, T4 val4, T5 val5,
                         T6 val6, T7 val7) {
        try {
            invoke(val1, val2, val3, val4, val5, val6, val7);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }
}