package com.epam.jdi.uitests.web.settings;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.settings.TimeoutSettings;

import static com.epam.matcher.base.Check.*;

/**
 * Setting class for WebTimeoutSettings, extends TimeoutSettings class. Includes override of setting
 * current timeout in seconds
 */
public class WebTimeoutSettings extends TimeoutSettings {

    @Override
    /**
     * Sets Timeout in seconds from TimeoutSettings class, and pass that timeout to WAIT_TIMEOUT of Check class
     *
     * @param timeoutSec the timeout walue in seconds
     */
    public void setCurrentTimeoutSec(int timeoutSec) {
        super.setCurrentTimeoutSec(timeoutSec);
        WAIT_TIMEOUT = timeoutSec;
    }
}
