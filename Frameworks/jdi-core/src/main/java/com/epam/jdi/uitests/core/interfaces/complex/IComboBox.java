package com.epam.jdi.uitests.core.interfaces.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.common.ITextField;

import static com.epam.jdi.uitests.core.actions.common.TextFieldActions.clear;
import static com.epam.jdi.uitests.core.actions.common.TextFieldActions.focus;
import static com.epam.jdi.uitests.core.actions.common.TextFieldActions.input;

public interface IComboBox<TEnum extends Enum> extends IDropDown<TEnum>, ITextField {

    /**
     * Inputs text to the text field
     * @param text text to input
     */
    @Override
    @JDIAction
    default void input(CharSequence text) {
        if (linked().has("value"))
            ((ITextField) linked().get("value")).input(text);
        else input.execute(this, text);
    }

    /**
     * Clears the text field of the combo box
     */
    @JDIAction
    default void clear() {
        if (linked().has("value"))
            ((ITextField) linked().get("value")).clear();
        else clear.execute(this);
    }

    /**
     * Focuses on the text field
     */
    @JDIAction
    default void focus() {
        if (linked().has("value"))
            ((ITextField) linked().get("value")).focus();
        else focus.execute(this);
    }

    /**
     * Clears the text field and inputs text to it
     * @param value text to set
     */
    @Override
    default void setValue(String value) {
        newInput(value);
    }
}