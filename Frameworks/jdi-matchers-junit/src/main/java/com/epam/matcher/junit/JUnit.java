package com.epam.matcher.junit;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.matcher.base.Check;
import org.junit.jupiter.api.Assertions;

import static java.lang.String.format;

public class JUnit extends Check {
    public JUnit() {
        super();
    }

    public JUnit(String checkMessage) {
        super(checkMessage);
    }

    @Override
    protected void assertException(String failMessage, Object... args) {
        Assertions.fail(format(failMessage, args));
    }
}