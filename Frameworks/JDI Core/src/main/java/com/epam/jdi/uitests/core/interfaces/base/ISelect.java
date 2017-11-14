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
import com.epam.jdi.uitests.core.interfaces.common.IText;

import static com.epam.jdi.uitests.core.actions.common.CheckboxActions.isChecked;
import static com.epam.jdi.uitests.core.settings.JDISettings.exception;

public interface ISelect extends IClickable, IText, ISetValue {
    /**
     * Selects Element. Similar to click()
     */
    @JDIAction
    default void select() { click(); }

    /**
     * @return Checks is Element selected
     */
    @JDIAction
    default boolean isSelected() {
        return isChecked.execute(this);
    }

    /**
     * @param value Specify element value
     *              Set value to Element
     */
    @JDIAction
    default void setValue(String value) {
        switch (value.toLowerCase()) {
            case "true":
            case "1":
            case "check":
                if (!isSelected()) select();
                break;
            case "false":
            case "0":
            case "uncheck":
                if (isSelected()) select();
                break;
        }
        throw exception("Can't set value '%s'. Supported values: 'true', '1', 'check', 'false', '0', 'uncheck'");
    }
    @JDIAction
    @Override
    default String getText() {
        return isSelected() + "";
    }
}