package com.epam.jdi.uitests.core.interfaces.common;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.base.ISelect;
import com.epam.jdi.uitests.core.interfaces.base.ISetValue;

import static com.epam.jdi.uitests.core.actions.common.CheckboxActions.*;
import static com.epam.jdi.uitests.core.settings.JDISettings.exception;

public interface ICheckBox extends ISelect, ISetValue {
    /**
     * Set checkbox checked
     */
    @JDIAction
    default void check() {
        check.execute(this);
    }
    /**
     * Set checkbox unchecked
     */
    @JDIAction
    default void uncheck() {
        uncheck.execute(this);
    }

    /**
     * @return Verify is checkbox checked
     */
    @JDIAction
    default boolean isChecked() {
        return isChecked.execute(this);
    }
    /**
     * @param value Specify element value
     *              Set value to Element
     */
    @Override
    default void setValue(String value) {
        switch (value.toLowerCase()) {
            case "true":
            case "1":
            case "check":
                check();
                break;
            case "false":
            case "0":
            case "uncheck":
                uncheck();
                break;
        }
        throw exception("Can't set value '%s' for Checkbox . Supported values: 'true', '1', 'check', 'false', '0', 'uncheck'");
    }
}