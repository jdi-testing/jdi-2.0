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
     * @param name name of the Element to select from list of Elements
     */
    @JDIAction
    default void select(String name) {
        select.execute(this, name);
    }

    /**
     * @param name enum with the name of the Element to select from list of Elements
     */
    @JDIAction
    default void select(TEnum name) {
        select(getEnumValue(name));
    }

    /**
     * @param index index of the Element to select from list of Elements
     */
    @JDIAction
    default void select(int index) {
        selectByIndex.execute(this, index);
    }

    /**
     * @return name of the selected Element
     */
    @JDIAction
    default String getSelected() {
        return getSelected.execute(this);
    }

    /**
     * @return index of the selected Element
     */
    @JDIAction
    default int getSelectedIndex() {
        return getSelectedIndex.execute(this);
    }

    /**
     * Waits until the option is selected
     *
     * @param name option name
     */
    @JDIAction
    default void waitSelected(String name) {
        wait.execute(this, () -> isSelected(name));
    }

    /**
     * Waits until the option is selected
     *
     * @param name enum with the option name
     */
    @JDIAction
    default void waitSelected(TEnum name) {
        wait.execute(this, () -> isSelected(name));
    }

    /**
     * @return name of the selected Element
     */
    @Override
    default String getValue() {
        return getSelected();
    }

    /**
     * @param value name of the Element to select from list of Elements
     */
    @Override
    default void setValue(String value) {
        select(value);
    }
}