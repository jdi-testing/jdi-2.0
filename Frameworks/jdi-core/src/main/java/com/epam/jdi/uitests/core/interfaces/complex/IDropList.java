package com.epam.jdi.uitests.core.interfaces.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.common.IText;

import java.util.List;

import static com.epam.jdi.tools.LinqUtils.*;
import static com.epam.jdi.uitests.core.actions.complex.SelectActions.isSelected;
import static com.epam.jdi.uitests.core.actions.complex.SelectActions.isSelectedByIndex;

public interface IDropList<TEnum extends Enum> extends IDropDown<TEnum>, IListSelector<TEnum>, IText {
    @Override
    default String getValue() {
        return getText();
    }

    /**
     * Expands DropDown
     */
    @Override
    @JDIAction
    default void expand() {
        if (!isExpanded())
            click();
    }

    /**
     * Marks dropdown value as selected
     * @param value a value to set
     */
    @Override
    default void setValue(String value) {
        check(value.split(getSeparator()));
    }

    /**
     * Expands dropdown and clicks on multiple values whether they are already selected or not
     * @param names values to select
     */
    @Override
    @JDIAction
    default void select(String... names) {
        assertLinked("list", "select");
        expand();
        ((IListSelector) linked().get("list")).select(names);
    }

    /**
     * Expands dropdown and clicks on multiple values by their indices whether they already selected or not
     * @param indexes indexes of values to select
     */
    @Override
    @JDIAction
    default void select(Integer... indexes) {
        assertLinked("list", "select");
        expand();
        ((IListSelector) linked().get("list")).select(indexes);
    }

    /**
     * Expands dropdown and selects multiple values, that are not already selected
     * @param names values to mark as selected
     */
    @Override
    @JDIAction
    default void check(String... names) {
        assertLinked("list", "select");
        expand();
        List<String> deselected = where(names, name -> !isSelected.execute(linked().get("list"), name));
        select(toStringArray(deselected));
    }

    /**
     * Expands dropdown and selects multiple values, that are not already selected
     * @param  indexes indexes of values to select
     */
    @Override
    @JDIAction
    default void check(Integer... indexes) {
        assertLinked("list", "select");
        expand();
        List<Integer> deselected = where(indexes, index -> !isSelectedByIndex.execute(linked().get("list"), index));
        select(toIntegerArray(deselected));
    }

    /**
     * Expands dropdown and deselects multiple values, that are selected
     * @param  names values to deselect
     */
    @Override
    @JDIAction
    default void uncheck(String... names) {
        assertLinked("list", "select");
        expand();
        List<String> selected = where(names, name -> isSelected.execute(linked().get("list"), name));
        select(toStringArray(selected));
    }

    /**
     * Expands dropdown and deselects multiple values, that are selected
     * @param  indexes indexes of values to deselect
     */
    @Override
    @JDIAction
    default void uncheck(Integer... indexes) {
        assertLinked("list", "select");
        expand();
        List<Integer> selected = where(indexes,
                index -> isSelectedByIndex.execute(linked().get("list"), index));
        select(toIntegerArray(selected));
    }

    /**
     * @return list with names of selected options
     */
    @Override
    @JDIAction
    default List<String> areSelected() {
        assertLinked("list", "select");
        expand();
        return where((List<String>) getNames(),
                name -> isSelected.execute(linked().get("list"), name));
    }
}