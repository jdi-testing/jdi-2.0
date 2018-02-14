package com.epam.jdi.uitests.core.interfaces.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import static com.epam.jdi.uitests.core.actions.complex.SelectActions.select;

public interface ISetValue extends IHasValue {
    /**
     * @param value Specify element value
     *              Set value to Element
     */
    default void setValue(String value) {
        select.execute(this, value);
    }
}