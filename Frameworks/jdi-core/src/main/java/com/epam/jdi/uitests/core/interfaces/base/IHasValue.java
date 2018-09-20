package com.epam.jdi.uitests.core.interfaces.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import static com.epam.jdi.uitests.core.actions.common.TextActions.getText;

/**
 * Interface for element with value
 */
public interface IHasValue {
    /**
     * Get value of Element
     *
     * @return Value of Element
     */
    default String getValue() {
        return getText.execute(this);
    }
}