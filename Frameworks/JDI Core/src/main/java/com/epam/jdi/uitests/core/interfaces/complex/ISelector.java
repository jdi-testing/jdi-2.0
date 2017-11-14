package com.epam.jdi.uitests.core.interfaces.complex;
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

import static com.epam.jdi.tools.EnumUtils.getEnumValue;
import static com.epam.jdi.uitests.core.actions.base.ElementActions.wait;
import static com.epam.jdi.uitests.core.actions.complex.SelectActions.*;

public interface ISelector<TEnum extends Enum> extends IBaseSelector {
    /**
     * @param name Specify name using string
     *             Select Element with name (use text) from list
     */
    @JDIAction
    default void select(String name) { select.execute(this, name);}

    /**
     * @param name Specify name using enum
     *             Select Element with name (use enum) from list
     */
    @JDIAction
    default void select(TEnum name) { select(getEnumValue(name));}

    /**
     * @param index Specify digit to select
     *              Select Element with name (use index) from list
     */
    @JDIAction
    default void select(int index) { selectByIndex.execute(this, index); }

    /**
     * @return Get name of the selected Element
     */
    @JDIAction
    default String getSelected() { return getSelected.execute(this); }

    /**
     * @return Get index of the selected Element
     */
    @JDIAction
    default int getSelectedIndex() { return getSelectedIndex.execute(this); }

    /**
     * @param name Specify name using string
     * Wait while option (from text) is selected. Return false if this not happens
     */
    @JDIAction
    default void waitSelected(String name) {
        wait.execute(this, () -> isSelected(name));
    }

    /**
     * @param name Specify name using enum
     * Wait while option (from enum) is selected. Return false if this not happens
     */
    @JDIAction
    default void waitSelected(TEnum name) { wait.execute(this, () -> isSelected(name));};

    @Override
    default String getValue() {
        return getSelected();
    }

}