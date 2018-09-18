package com.epam.jdi.uitests.core.interfaces.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.common.IText;

import static com.epam.jdi.uitests.core.actions.common.CheckboxActions.isChecked;
import static com.epam.jdi.uitests.core.actions.common.CheckboxActions.select;

/**
 * Interface for selectable elements
 */
public interface ISelect extends IClickable, IText, ISetValue {
    /**
     * Selects Element. Similar to click()
     */
    @JDIAction
    default void select() {
        click();
    }

    /**
     * @return Checks is Element selected
     */
    @JDIAction
    default boolean isSelected() {
        return isChecked.execute(this);
    }

    /**
     * @param value Specify element value
     *              Set value to Element
     */
    default void setValue(String value) {
        select.execute(this, value);
    }

    /**
     * @return isSelected
     */
    @Override
    @JDIAction
    default String getText() {
        return String.valueOf(isSelected());
    }
}