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
import static com.epam.jdi.uitests.core.actions.complex.SelectActions.isSelectedByIndex;
import static java.util.Arrays.asList;

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
        List<String> options = getOptions();
        for (String o : options)
            if (asList(names).contains(o) && !isSelected(o)
                    || !asList(names).contains(o) && isSelected(o))
                select(o);
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
        for (int i=0; i <= getOptions().size(); i++)
            if (asList(indexes).contains(i) && !isSelected(i)
                    || !asList(indexes).contains(i) && isSelected(i))
                select(i);
    }

    /**
     * @param names Specify names
     *              Uncheck only specified options (use text) from list (all other options checked)
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
        for (int i=0; i <= getOptions().size(); i++)
            if (asList(indexes).contains(i) && isSelected(i)
            || !asList(indexes).contains(i) && !isSelected(i))
                select(i);
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
                name -> !isChecked.execute(this, name));
    }
    JFunc2<Object, String, Boolean> isChecked = isSelected;
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