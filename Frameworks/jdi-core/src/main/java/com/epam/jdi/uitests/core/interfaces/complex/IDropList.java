package com.epam.jdi.uitests.core.interfaces.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.EnumUtils;
import com.epam.jdi.tools.LinqUtils;
import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.common.IText;

import java.util.List;

import static com.epam.jdi.tools.LinqUtils.*;
import static com.epam.jdi.tools.LinqUtils.toStringArray;
import static com.epam.jdi.tools.PrintUtils.print;
import static com.epam.jdi.uitests.core.actions.base.ElementActions.wait;
import static com.epam.jdi.uitests.core.actions.complex.ListSelectActions.select;
import static com.epam.jdi.uitests.core.actions.complex.ListSelectActions.selectByIndex;
import static com.epam.jdi.uitests.core.actions.complex.SelectActions.isSelected;
import static com.epam.jdi.uitests.core.actions.complex.SelectActions.isSelectedByIndex;

public interface IDropList<TEnum extends Enum>
        extends IDropDown<TEnum>, IListSelector<TEnum>, IText {
    @Override
    default String getValue() {
        return getText();
    }
    /**
     * Expanding DropDown
     */
    @Override @JDIAction
    default void expand() {
        if (!isExpanded())
            click();
    }
    @Override
    default void setValue(String value) {
        select(value.split(getSeparator()));
    }

    @Override @JDIAction
    default void select(String... names) {
        assertLinked("list", "select");
        expand();
        ((IListSelector)linked().get("list")).select(names);
    }

    @Override @JDIAction
    default void select(Integer... indexes) {
        assertLinked("list", "select");
        expand();
        ((IListSelector)linked().get("list")).select(indexes);
    }

    @Override @JDIAction
    default void check(String... names) {
        assertLinked("list", "select");
        expand();
        List<String> deselected = where(names, name -> !isSelected.execute(linked().get("list"), name));
        select(toStringArray(deselected));
    }

    @Override @JDIAction
    default void check(Integer... indexes) {
        assertLinked("list", "select");
        expand();
        List<Integer> deselected = where(indexes, index -> !isSelectedByIndex.execute(linked().get("list"), index));
        select(toIntegerArray(deselected));
    }

    @Override @JDIAction
    default void uncheck(String... names) {
        assertLinked("list", "select");
        expand();
        List<String> selected = where(names, name -> isSelected.execute(linked().get("list"), name));
        select(toStringArray(selected));
    }

    @Override @JDIAction
    default void uncheck(Integer... indexes) {
        assertLinked("list", "select");
        expand();
        List<Integer> selected = where(indexes,
                index -> isSelectedByIndex.execute(linked().get("list"), index));
        select(toIntegerArray(selected));
    }

    /**
     * @return Get names of checked options
     */
    @Override @JDIAction
    default List<String> areSelected() {
        assertLinked("list", "select");
        expand();
        return where((List<String>) getNames(),
                name -> isSelected.execute(linked().get("list"), name));
    }
}