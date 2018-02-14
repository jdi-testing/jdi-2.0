package com.epam.jdi.uitests.core.interfaces.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.common.IText;

import static com.epam.jdi.uitests.core.actions.common.CheckboxActions.isChecked;
import static com.epam.jdi.uitests.core.settings.JDISettings.exception;

public interface ISelect extends IClickable, IText, ISetValue {
    /**
     * Selects Element. Similar to click()
     */
    @JDIAction
    default void select() { click(); }

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
        switch (value.toLowerCase()) {
            case "true":
            case "1":
            case "check":
                if (!isSelected()) select();
                break;
            case "false":
            case "0":
            case "uncheck":
                if (isSelected()) select();
                break;
        }
        throw exception("Can't set value '%s'. Supported values: 'true', '1', 'check', 'false', '0', 'uncheck'");
    }

    @Override @JDIAction
    default String getText() {
        return isSelected() + "";
    }
}