package com.epam.jdi.tools;
/* The MIT License
 *
 * Copyright 2004-2017 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:

 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

 */

/**
 * Created by Roman Iovlev on 10.27.2017
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