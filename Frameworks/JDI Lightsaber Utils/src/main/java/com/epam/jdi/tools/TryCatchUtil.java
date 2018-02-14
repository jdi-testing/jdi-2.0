package com.epam.jdi.tools;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JFunc;

public final class TryCatchUtil {
    private TryCatchUtil() { }
    public static <T> T tryGetResult(JFunc<T> waitCase) {
        try {
            return waitCase.invoke();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static void throwRuntimeException(Throwable ignore) {
        // if (exceptionThrown)
        throw (ignore instanceof RuntimeException) ? (RuntimeException) ignore : new RuntimeException(ignore);
    }
}