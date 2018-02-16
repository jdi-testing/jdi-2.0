package com.epam.jdi.uitests.web.testng.testRunner;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.logger.ILogger;
import com.epam.jdi.uitests.core.interfaces.IAsserter;
import com.epam.web.matcher.testng.Check;

public class TestNGCheck extends Check implements IAsserter {
    public IAsserter setUpLogger(ILogger logger) { super.setLogger(logger); return this; }

}
