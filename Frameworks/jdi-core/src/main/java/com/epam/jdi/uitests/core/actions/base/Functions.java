package com.epam.jdi.uitests.core.actions.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

public enum Functions {
    NONE(""),
    OK_BUTTON("ok"),
    CLOSE_BUTTON("close"),
    CANCEL_BUTTON("cancel");

    public String name;

    Functions(String name) {
        this.name = name;
    }
}