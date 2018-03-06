package com.epam.jdi.uitests.core.interfaces.common;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.base.ISelect;
import com.epam.jdi.uitests.core.interfaces.base.ISetValue;

import static com.epam.jdi.uitests.core.actions.common.CheckboxActions.*;

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
        select.execute(this, value);
    }

    @Override
    default String getValue() {
        return isChecked() ? "true" : "false";
    }
}