package com.epam.jdi.uitests.core.interfaces.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.EnumUtils;
import com.epam.jdi.tools.LinqUtils;
import com.epam.jdi.tools.func.JFunc2;
import com.epam.jdi.uitests.core.annotations.JDIAction;

import java.util.List;

import static com.epam.jdi.tools.LinqUtils.*;
import static com.epam.jdi.tools.PrintUtils.print;
import static com.epam.jdi.uitests.core.actions.base.ElementActions.wait;
import static com.epam.jdi.uitests.core.actions.complex.ListSelectActions.select;
import static com.epam.jdi.uitests.core.actions.complex.ListSelectActions.selectByIndex;
import static com.epam.jdi.uitests.core.actions.complex.SelectActions.isSelected;
import static java.util.Arrays.asList;

public interface IListSelector<TEnum extends Enum> extends IBaseSelector {
    default void expand() {
    }

    /**
     * @param names names of the list options to select (change their state selected/deselected)
     */
    @JDIAction
    default void select(String... names) {
        expand();
        select.execute(this, names);
    }

    /**
     * @param names enums with names of the list options to select (change their state selected/deselected)
     */
    @JDIAction
    default void select(TEnum... names) {
        select(toStringArray(LinqUtils.select(names, EnumUtils::getEnumValue)));
    }

    /**
     * @param indexes indexes of the list options to select (change their state selected/deselected)
     */
    @JDIAction
    default void select(Integer... indexes) {
        expand();
        selectByIndex.execute(this, indexes);
    }

    /**
     * @param names names of the list options to check (all other options set as unchecked)
     */
    @JDIAction
    default void check(String... names) {
        expand();
        List<String> options = getOptions();
        for (String o : options)
            if (asList(names).contains(o) && !isSelected(o)
                    || !asList(names).contains(o) && isSelected(o))
                select(o);
    }

    /**
     * @param names enums with names of the list options to check (all other options set as unchecked)
     */
    @JDIAction
    default void check(TEnum... names) {
        check(toStringArray(LinqUtils.select(names, EnumUtils::getEnumValue)));
    }

    /**
     * @param indexes indexes of the list options to check (all other options set as unchecked)
     */
    @JDIAction
    default void check(Integer... indexes) {
        expand();
        for (int i = 0; i <= getOptions().size(); i++)
            if (asList(indexes).contains(i) && !isSelected(i)
                    || !asList(indexes).contains(i) && isSelected(i))
                select(i);
    }

    /**
     * @param names names of the list options to uncheck (all other options set as checked)
     */
    @JDIAction
    default void uncheck(String... names) {
        expand();
        List<String> options = getOptions();
        for (String o : options)
            if (asList(names).contains(o) && isSelected(o)
                    || !asList(names).contains(o) && !isSelected(o))
                select(o);
    }

    /**
     * @param names enums with names of the list options to uncheck (all other options set as checked)
     */
    @JDIAction
    default void uncheck(TEnum... names) {
        uncheck(toStringArray(LinqUtils.select(names, EnumUtils::getEnumValue)));
    }

    /**
     * @param indexes indexes of the list options to uncheck (all other options set as checked)
     */
    @JDIAction
    default void uncheck(Integer... indexes) {
        expand();
        for (int i = 0; i <= getOptions().size(); i++)
            if (asList(indexes).contains(i) && isSelected(i)
                    || !asList(indexes).contains(i) && !isSelected(i))
                select(i);
    }

    /**
     * @return a list with names of checked options
     */
    @JDIAction
    default List<String> areSelected() {
        expand();
        return where((List<String>) getNames(),
                name -> isSelected.execute(this, name));
    }

    /**
     * Waits until all options with the names are selected
     * @param names options names
     */
    @JDIAction
    default void waitSelected(String... names) {
        wait.execute(this,
                () -> listEquals(areSelected(), names));
    }

    /**
     * Waits until all options with the names are selected
     * @param names enums with options names
     */
    @JDIAction
    default void waitSelected(TEnum... names) {
        waitSelected(toStringArray(LinqUtils.select(names, EnumUtils::getEnumValue)));
    }

    /**
     * @return a list with names of unchecked options
     */
    @JDIAction
    default List<String> areDeselected() {
        expand();
        return where((List<String>) getNames(),
                name -> !isChecked.execute(this, name));
    }

    JFunc2<Object, String, Boolean> isChecked = isSelected;

    /**
     * Waits until all options with the names are deselected
     * @param names options names
     */
    @JDIAction
    default void waitDeselected(String... names) {
        wait.execute(this, () -> listEquals(areDeselected(), names));
    }

    /**
     * Waits until all options with the names are deselected
     * @param names enums with options names
     */
    @JDIAction
    default void waitDeselected(TEnum... names) {
        waitDeselected(toStringArray(LinqUtils.select(names, EnumUtils::getEnumValue)));
    }

    /**
     * Sets all options checked
     */
    @JDIAction
    default void checkAll() {
        check(toStringArray(getOptions()));
    }

    /**
     * Sets all options checked
     */
    @JDIAction
    default void selectAll() {
        checkAll();
    }

    /**
     * Sets all options unchecked
     */
    @JDIAction
    default void clear() {
        uncheckAll();
    }

    /**
     * Sets all options unchecked
     */
    @JDIAction
    default void uncheckAll() {
        uncheck(toStringArray(getOptions()));
    }

    /**
     * @return a String with with names of checked options
     */
    @Override
    default String getValue() {
        return print(areSelected());
    }

    /**
     * Selects multiple options
     * @param value a String with options names separated by the separator
     */
    @Override
    default void setValue(String value) {
        select(value.split(getSeparator()));
    }

    /**
     * @return Returns options names separator
     */
    String getSeparator();
}