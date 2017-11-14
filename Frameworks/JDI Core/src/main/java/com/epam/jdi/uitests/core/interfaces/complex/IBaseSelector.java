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
import com.epam.jdi.uitests.core.interfaces.base.IBaseElement;
import com.epam.jdi.uitests.core.interfaces.base.ISetValue;

import java.util.List;

import static com.epam.jdi.tools.EnumUtils.getEnumValue;
import static com.epam.jdi.tools.LinqUtils.*;
import static com.epam.jdi.tools.PrintUtils.print;
import static com.epam.jdi.uitests.core.actions.base.ElementActions.*;
import static com.epam.jdi.uitests.core.actions.complex.SelectActions.getOptions;
import static com.epam.jdi.uitests.core.actions.complex.SelectActions.isSelected;
import static com.epam.jdi.uitests.core.actions.complex.SelectActions.isSelectedByIndex;
import static com.epam.jdi.uitests.core.logger.LogLevels.DEBUG;
import static com.epam.jdi.uitests.core.settings.JDISettings.exception;

public interface IBaseSelector<TEnum extends Enum> extends IBaseElement, ISetValue {
    /**
     * @return Get labels of all options (same as getValues, getLabels and getNames)
     */
    @JDIAction
    default List<String> getOptions() { return getOptions.execute(this); }

    /**
     * @return Get all options names (same as getOptions, getLabels and getValues)
     */
    @JDIAction
    default List<String> getNames() {
        return getOptions();
    }

    /**
     * @return Get all values (same as getOptions, getLabels and getNames)
     */
    @JDIAction
    default List<String> getValues() {
        return getOptions();
    }

    /**
     * @return Get all labels (same as getOptions, getValues and getNames)
     */
    @JDIAction
    default List<String> getLabels() {
        return getOptions();
    }

    /**
     * @return Get all options labels in one string separated with “; ”
     */
    @JDIAction
    default String getOptionsAsText() {
        return print(getOptions());
    }

    /**
     *  Get specified application elements
     */
    @JDIAction(level = DEBUG)
    default <T> List<T> getElements(Object... args) {
        return map(getElements.execute(this, args), el -> (T)el); }
    /**
     *  Get specified application element
     */
    @JDIAction(level = DEBUG)
    default <T> T getElement(String name) {
        T el = first(getElements(),
            e -> eGetText.execute(e).equals(name));
        if (el == null)
            throw exception("Can't find element '%s'", name);
        return el;
    }
    /**
     * @param name Specify name using string
     * @return Is option selected?
     */
    @JDIAction
    default boolean isSelected(String name) {
        return isSelected.execute(this,name);
    }

    /**
     * @param name Specify name using enum
     * @return Is option selected?
     */
    @JDIAction
    default boolean isSelected(TEnum name) {
        return isSelected(getEnumValue(name));
    }

    /**
     * @param index Specify name using enum
     * @return Is option selected?
     */
    @JDIAction
    default boolean isSelected(int index) {
        return isSelectedByIndex.execute(this, index);
    }

    @JDIAction
    default boolean displayed(String name) {
        return eDisplayed.execute(getElement(name));
    }
    @JDIAction
    default boolean displayed(TEnum name) {
        return displayed(getEnumValue(name));
    }
    @JDIAction
    default boolean displayed(int index) {
        if (index <= 0)
            throw exception("displayed index should be 1 or more, but used '%s'", index);
        List<Object> els = getElements();
        if (index > els.size())
            throw exception("Can't check displayed for '%s' because found only %s elements", index, els.size());
        return eDisplayed.execute(els.get(index));
    }
    /**
     * @return Check is Element visible
     */
    @Override @JDIAction
    default boolean displayedNow() {
        return any(getElements(), el -> eDisplayed.execute(el));
    }

}
