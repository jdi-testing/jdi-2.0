package com.epam.jdi.tools;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JAction;
import com.epam.jdi.tools.func.JFunc;
import com.epam.jdi.tools.func.JFunc1;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.System.currentTimeMillis;

public class Timer {
    private Long start = currentTimeMillis();
    private long timeoutInMSec = 5 * 1000L;
    private long retryTimeoutInMSec = 100;

    public Timer() {
    }

    public Timer(long timeoutInMSec, long retryTimeoutInMSec) {
        this();
        this.timeoutInMSec = timeoutInMSec;
        this.retryTimeoutInMSec = retryTimeoutInMSec;
    }

    public Timer(long timeoutInMSec) {
        this();
        this.timeoutInMSec = timeoutInMSec;
    }

    public void restart() { start = currentTimeMillis(); }

    public static String nowTime() {
        return nowTime("HH:mm:ss.SSS");
    }

    public static String nowTimeShort() {
        return nowTime("mm:ss.SSS");
    }

    public static String nowDate() {
        return nowTime("yyyy-MM-dd HH:mm:ss");
    }

    public static String nowTime(String timeFormat) {
        return new SimpleDateFormat(timeFormat).format(new Date());
    }

    public static String nowMSecs() {
        return Long.toString(currentTimeMillis());
    }

    public static void sleep(long mSec) {
        try {
            Thread.sleep(mSec);
        } catch (InterruptedException ignore) {
        }
    }

    public static <T> T getByCondition(JFunc<T> getFunc, JFunc1<T, Boolean> conditionFunc) {
        return new Timer().getResultByCondition(getFunc, conditionFunc);
    }

    public static <T> T getResultAction(JFunc<T> getFunc) {
        return new Timer().getResultByCondition(getFunc, result -> true);
    }

    public static boolean alwaysDoneAction(JAction action) {
        return new Timer().wait(() -> {
            action.invoke();
            return true;
        });
    }

    public static boolean waitCondition(JFunc<Boolean> condition) {
        return new Timer().wait(condition);
    }

    public Timer setTimeout(long timeoutInMSec) {
        this.timeoutInMSec = timeoutInMSec;
        return this;
    }

    public Timer setRetryTimeout(long retryTimeoutInMSec) {
        this.retryTimeoutInMSec = retryTimeoutInMSec;
        return this;
    }

    public Long timePassedInMSec() {
        Long now = currentTimeMillis();
        return now - start;
    }

    public boolean timeoutPassed() {
        return timePassedInMSec() > timeoutInMSec;
    }

    public boolean wait(JFunc<Boolean> waitCase) {
        Throwable exception = null;
        while (!timeoutPassed())
            try {
                if (waitCase.invoke())
                    return true;
                sleep(retryTimeoutInMSec);
            } catch (Exception | Error ex) { exception = ex; }
        if (exception != null)
            throw new RuntimeException(exception);
        return false;
    }

    public <T> T getResult(JFunc<T> getFunc) {
        return getResultByCondition(getFunc, result -> true);
    }

    public <T> T getResultByCondition(JFunc<T> getFunc, JFunc1<T, Boolean> conditionFunc) {
        Throwable exception = null;
         do {
            try {
                T result = getFunc.invoke();
                if (result != null && conditionFunc.invoke(result))
                    return result;
            } catch (Exception | Error ex) { exception = ex; }
            sleep(retryTimeoutInMSec);
        } while (!timeoutPassed());
        if (exception != null)
            throw new RuntimeException(exception);
        return null;
    }

    private static int i = 1;
    private static int getNum() { return i = i++;}
    public static void logTime() {
        System.out.println(i++ + ": " + Timer.nowTime());
    }
}