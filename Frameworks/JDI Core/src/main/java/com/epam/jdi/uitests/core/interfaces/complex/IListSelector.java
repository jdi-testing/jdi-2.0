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
import com.epam.jdi.tools.EnumUtils;
import com.epam.jdi.tools.LinqUtils;
import com.epam.jdi.uitests.core.annotations.JDIAction;

import java.util.List;

import static com.epam.jdi.tools.LinqUtils.*;
import static com.epam.jdi.tools.PrintUtils.print;
import static com.epam.jdi.uitests.core.actions.base.ElementActions.wait;
import static com.epam.jdi.uitests.core.actions.complex.ListSelectActions.select;
import static com.epam.jdi.uitests.core.actions.complex.ListSelectActions.selectByIndex;
import static com.epam.jdi.uitests.core.actions.complex.SelectActions.isSelected;
import static com.epam.jdi.uitests.core.actions.complex.SelectActions.isSelectedByIndex;

public interface IListSelector<TEnum extends Enum> extends IBaseSelector {
    default void expand() {}
    /**
     * @param names Specify names
     *              Select options with name (use text) from list (change their state selected/deselected)
     */
    @JDIAction
    default void select(String... names) {
        expand(); select.execute(this, names);
    }

    /**
     * @param names Specify names
     *              Select options with name (use enum) from list (change their state selected/deselected)
     */
    @JDIAction
    default void select(TEnum... names) { select(toStringArray(LinqUtils.select(names, EnumUtils::getEnumValue)));}

    /**
     * @param indexes Specify indexes
     *                Select options with name (use index) from list (change their state selected/deselected)
     */
    @JDIAction
    default void select(Integer... indexes) { expand(); selectByIndex.execute(this, indexes);}

    /**
     * @param names Specify names
     *              Check only specified options (use text) from list (all other options unchecked)
     */
    @JDIAction
    default void check(String... names) {
        expand();
        List<String> deselected = where(names, name -> !isSelected.execute(this, name));
        select(toStringArray(deselected));
    }

    /**
     * @param names Specify names
     *              Check only specified options (use enum) from list (all other options unchecked)
     */
    @JDIAction
    default void check(TEnum... names) {
        check(toStringArray(LinqUtils.select(names, EnumUtils::getEnumValue)));
    }

    /**
     * @param indexes Specify indexes
     *                Check only specified options (use index) from list (all other options unchecked)
     */
    @JDIAction
    default void check(Integer... indexes) {
        expand();
        List<Integer> deselected = where(indexes, index -> !isSelectedByIndex.execute(this, index));
        select(toIntegerArray(deselected));
    }

    /**
     * @param names Specify names
     *              Uncheck only specified options (use text) from list (all other options checked)
     */
    @JDIAction
    default void uncheck(String... names) {
        expand();
        List<String> selected = where(names, name -> isSelected.execute(this, name));
        select(toStringArray(selected));
    }

    /**
     * @param names Specify names
     *              Uncheck only specified options (use enum) from list (all other options checked)
     */
    @JDIAction
    default void uncheck(TEnum... names) {
        uncheck(toStringArray(LinqUtils.select(names, EnumUtils::getEnumValue)));
    }

    /**
     * @param indexes Specify indexes
     *                Uncheck only specified options (use index) from list (all other options checked)
     */
    @JDIAction
    default void uncheck(Integer... indexes) {
        expand();
        List<Integer> selected = where(indexes,
                index -> isSelectedByIndex.execute(this, index));
        select(toIntegerArray(selected));
    }

    /**
     * @return Get names of checked options
     */
    @JDIAction
    default List<String> areSelected() {
        expand();
        return where((List<String>) getNames(),
                name -> isSelected.execute(this, name));
    }

    /**
     * @param names Specify names
     * Wait while all options with names (use text) selected. Return false if this not happens
     */
    @JDIAction
    default void waitSelected(String... names) {
        wait.execute(this,
                () -> listEquals(areSelected(), names));
    }

    /**
     * @param names Specify names
     * Wait while all options with names (use enum) selected. Return false if this not happens
     */
    @JDIAction
    default void waitSelected(TEnum... names) {
        waitSelected(toStringArray(LinqUtils.select(names, EnumUtils::getEnumValue)));
    }

    /**
     * @return Get names of unchecked options
     */
    @JDIAction
    default List<String> areDeselected() {
        expand();
        return where((List<String>) getNames(),
                name -> !isSelected.execute(this, name));
    }

    /**
     * @param names Specify names
     * Wait while all options with names (use text) deselected. Return false if this not happens
     */
    @JDIAction
    default void waitDeselected(String... names) {
        wait.execute(this, () -> listEquals(areDeselected(), names));
    }

    /**
     * @param names Specify names
     * Wait while all options with names (use enum) deselected. Return false if this not happens
     */
    @JDIAction
    default void waitDeselected(TEnum... names) {
        waitDeselected(toStringArray(LinqUtils.select(names, EnumUtils::getEnumValue)));
    }

    /**
     * Set all options checked
     */
    @JDIAction
    default void checkAll() { check(toStringArray(getOptions())); }

    /**
     * Set all options checked
     */
    @JDIAction
    default void selectAll() {
        checkAll();
    }

    /**
     * Set all options unchecked
     */
    @JDIAction
    default void clear() { uncheckAll(); }

    /**
     * Set all options unchecked
     */
    @JDIAction
    default void uncheckAll() {
        uncheck(toStringArray(getOptions()));
    }
    @Override
    default String getValue() {
        return print(areSelected());
    }
    @Override
    default void setValue(String value) {
        select(value.split(getSeparator()));
    }
    String getSeparator();
}