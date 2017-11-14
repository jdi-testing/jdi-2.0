package com.epam.jdi.uitests.core.interfaces.base;
/* The MIT License
 *
 * Copyright 2004-2017 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:

 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

 */

/**
 * Created by Roman Iovlev on 10.03.2017
 */
import com.epam.jdi.uitests.core.annotations.JDIAction;

import static com.epam.jdi.uitests.core.actions.base.ElementActions.*;
import static com.epam.jdi.uitests.core.logger.LogLevels.DEBUG;

public interface IElement extends IBaseElement {
    /**
     * Get element attribute
     *
     * @param name Specify name for attribute
     * @return Returns chosen attribute
     */
    @JDIAction(value = "Get attribute {0}")
    default String getAttribute(String name) {
        return getAttribute.execute(getElement(), name);
    }
    /**
     * @param name  Specify attribute name
     * @param value Specify attribute value
     * Waits while attribute gets expected value. Return false if this not happens
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
     *  Get specified application element
     */
    @JDIAction(level = DEBUG)
    default <T> T getElement(Object... args) {
        return (T) getElement.execute(this, args);
    }

    /**
     *  Focus on this element
     */
    @JDIAction("Focus on element")
    default void focus() { focus.execute(this); }

}