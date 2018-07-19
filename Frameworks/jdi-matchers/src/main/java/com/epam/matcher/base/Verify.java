package com.epam.matcher.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import java.util.LinkedList;
import java.util.List;

import static java.lang.String.format;

public class Verify extends Check {
    private static List<String> fails = new LinkedList<>();

    public Verify() { }

    public Verify(String checkMessage) {
        super(checkMessage);
    }

    public static List<String> getFails() {
        List<String> result = new LinkedList<>(fails);
        fails.clear();
        return result;
    }

    @Override
    protected void assertException(String failMessage, Object... args) {
        fails.add(format(failMessage, args));
    }
}