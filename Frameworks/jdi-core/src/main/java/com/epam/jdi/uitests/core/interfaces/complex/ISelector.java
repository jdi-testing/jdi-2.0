package com.epam.jdi.uitests.core.interfaces.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
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

    @Override
    default void setValue(String value) {
        select(value);
    }
}