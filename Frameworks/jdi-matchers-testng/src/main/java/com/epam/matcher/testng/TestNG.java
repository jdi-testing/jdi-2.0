package com.epam.matcher.testng;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.matcher.base.Check;
import org.testng.Assert;

import static java.lang.String.format;

public class TestNG extends Check {
    public TestNG() {
        super();
    }

    public TestNG(String checkMessage) {
        super(checkMessage);
    }

    @Override
    protected void assertException(String failMessage, Object... args) {
        Assert.fail(format(failMessage, args));
    }
}