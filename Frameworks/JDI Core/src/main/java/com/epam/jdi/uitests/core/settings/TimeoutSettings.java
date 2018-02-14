package com.epam.jdi.uitests.core.settings;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

public class TimeoutSettings {
    private int waitElementSec = 20;
    private int defaultWaitTimeout = 20;
    private int waitPageLoadSec = 20;
    private int retryMSec = 100;

    public TimeoutSettings() {
        setCurrentTimeoutSec(waitElementSec);
    }

    public void setDefaultTimeoutSec(int timeoutSec) {
        defaultWaitTimeout = timeoutSec;
    }
    public void setCurrentTimeoutSec(int timeoutSec) {
        waitElementSec = timeoutSec;
    }
    public int getDefaultTimeoutSec() {
        return defaultWaitTimeout;
    }
    public int getCurrentTimeoutSec() {
        return waitElementSec;
    }

    public void dropTimeouts() {
        setCurrentTimeoutSec(defaultWaitTimeout);
    }
}