package com.epam.jdi.uitests.web.settings;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.settings.TimeoutSettings;

import static com.epam.web.matcher.base.BaseMatcher.setDefaultTimeout;

public class WebTimeoutSettings extends TimeoutSettings {

    @Override
    public void setCurrentTimeoutSec(int timeoutSec) {
        super.setCurrentTimeoutSec(timeoutSec);
        setDefaultTimeout(timeoutSec);
    }
}
