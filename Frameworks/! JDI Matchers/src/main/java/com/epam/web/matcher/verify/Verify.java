package com.epam.web.matcher.verify;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.web.matcher.base.BaseMatcher;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class Verify extends BaseMatcher {
    private static List<String> fails = new LinkedList<>();

    public Verify() {
    }

    public Verify(String checkMessage) {
        super(checkMessage);
    }

    public static List<String> getFails() {
        List<String> result = new LinkedList<>(fails);
        fails.clear();
        return result;
    }

    protected String doScreenshotGetMessage() {
        return "";
    }

    @Override
    protected Consumer<String> throwFail() {
        return fails::add;
    }
}