package com.epam.jdi.uitests.core.interfaces.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import static com.epam.jdi.uitests.core.actions.complex.SelectActions.select;

/**
 * Interface for elements with value, and the value available for setting
 */
public interface ISetValue extends IHasValue {
    /**
     * Set value to Element
     *
     * @param value Specify element value
     */
    default void setValue(String value) {
        select.execute(this, value);
    }
}