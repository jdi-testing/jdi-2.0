package com.epam.jdi.uitests.core.interfaces.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;

import static com.epam.jdi.uitests.core.actions.base.ElementActions.*;
import static com.epam.jdi.uitests.core.logger.LogLevels.DEBUG;

/**
 * Actions common for most elements
 */
public interface IElement extends IBaseElement {
    /**
     * Get element attribute
     *
     * @param name Specify name for attribute
     * @return Returns chosen attribute
     */
    @JDIAction(value = "Get attribute {0}")
    default String getAttribute(String name) {
        return getAttribute.execute(this, name);
    }

    /**
     * @param name  Specify attribute name
     * @param value Specify attribute value
     *              Waits while attribute gets expected value. Return false if this not happens
     */
    @JDIAction("Wait attribute {0} with value {1}")
    default void waitAttribute(String name, String value) {
        wait.execute(this, () -> getAttribute(name).equals(value));
    }

    /**
     * @param attributeName Specify attribute name
     * @param value         Specify attribute value
     *                      Sets attribute value for Element
     */
    @JDIAction(value = "Set attribute {0} = {1}", level = DEBUG)
    default void setAttribute(String attributeName, String value) {
        setAttribute.execute(this, attributeName, value);
    }

    /**
     * Get specified application element
     */
    @JDIAction(level = DEBUG)
    default <T> T getElement(Object... args) {
        return (T) getElement.execute(this, args);
    }

    /**
     * Focus on this element
     */
    @JDIAction("Focus on element")
    default void focus() {
        focus.execute(this);
    }

}