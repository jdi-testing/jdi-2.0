package com.epam.jdi.uitests.core.interfaces.common;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.base.ISelect;
import com.epam.jdi.uitests.core.interfaces.base.ISetValue;

import static com.epam.jdi.uitests.core.actions.common.CheckboxActions.*;

/**
 * Interface for Checkboxes
 */
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
     * Is checkbox checked
     *
     * @return Verify is checkbox checked
     */
    @JDIAction
    default boolean isChecked() {
        return isChecked.execute(this);
    }

    /**
     * Set value to Element
     *
     * @param value Specify element value
     */
    @Override
    default void setValue(String value) {
        select.execute(this, value);
    }

    /**
     * Synonym to {@code isChecked()} but returns boolean as String
     *
     * @return Is checkbox checked or not as String
     */
    @Override
    default String getValue() {
        return String.valueOf(isChecked());
    }
}