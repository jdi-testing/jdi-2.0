package com.epam.jdi.uitests.core.interfaces.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.common.ITextField;

public interface IComboBox<TEnum extends Enum>
        extends IDropDown<TEnum>, ITextField {
    @Override @JDIAction
    default void input(CharSequence text) {
        ((ITextField)linked().get("value")).input(text);
    }

    @Override @JDIAction
    default String getLabel() {
        return ((ITextField)linked().get("value")).getLabel();
    }

    @JDIAction
    default void clear() {
        ((ITextField)linked().get("value")).clear();
    }

    @JDIAction
    default void focus() {
        ((ITextField)linked().get("value")).focus();
    }

    @Override
    default void setValue(String value) {
        newInput(value);
    }
}