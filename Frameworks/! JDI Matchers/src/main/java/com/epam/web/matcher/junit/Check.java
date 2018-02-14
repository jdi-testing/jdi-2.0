package com.epam.web.matcher.junit;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.web.matcher.base.BaseMatcher;
import org.junit.Assert;

import java.util.function.Consumer;

public class Check extends BaseMatcher {
    public Check() {
        super();
    }

    public Check(String checkMessage) {
        super(checkMessage);
    }

    @Override
    protected Consumer<String> throwFail() {
        return Assert::fail;
    }
}