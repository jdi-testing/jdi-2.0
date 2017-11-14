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
import com.epam.jdi.uitests.core.interfaces.base.IClickable;
import com.epam.jdi.uitests.core.interfaces.common.IText;

import java.util.List;


/**
 * Dropdown element using three locators: list, expander, value
 *
 */
public interface IDropDown<TEnum extends Enum> extends ISelector<TEnum>, IText, IClickable {
    default boolean isExpanded() {
        return linked().get("list").displayed();
    }
    /**
     * Expanding DropDown
     */
    @JDIAction
    default void expand() {
        if (!isExpanded())
            click();
    }
    /**
     * Closing DropDown
     */
    @JDIAction
    default void close() {
        if (isExpanded())
            click();
    }
    @Override
    default String getValue() { return getText(); }

    @Override
    default void select(String name) {
        expand();
        ((ISelector)linked().get("list")).select(name);
    }

    @Override
    default void select(int index) {
        expand();
        ((ISelector)linked().get("list")).select(index);
    }

    @Override
    default boolean displayed(String name) {
        return isExpanded() &&
        ((ISelector)linked().get("list")).getLabels().contains(name);
    }

    @Override
    default String getSelected() {
        return getText();
    }

    /**
     * Waits while Element becomes visible
     */
    @Override
    default boolean displayed() {
        return linked().get("value").disabled();
    }

    /**
     * Waits while Element becomes invisible
     */
    @Override
    default boolean vanished() {
        return linked().get("value").vanished();
    }

    @Override
    default List<String> getOptions() {
        expand();
        return ((ISelector)linked().get("list")).getOptions();
    }
    @Override
    default void click() {
        ((IClickable)linked().get("expander")).click();
    }
    @Override
    default String getText() {
        return ((IText) linked().get("value")).getText();
    }

}