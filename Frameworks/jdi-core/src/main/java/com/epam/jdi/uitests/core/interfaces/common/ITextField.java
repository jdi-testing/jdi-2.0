package com.epam.jdi.uitests.core.interfaces.common;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.base.IElement;
import com.epam.jdi.uitests.core.interfaces.base.ISetValue;

import static com.epam.jdi.uitests.core.actions.common.TextFieldActions.*;

/**
 * Interface for Text Fields
 */
public interface ITextField extends ISetValue, IText, IElement {
    /**
     * Input text in textfield
     *
     * @param text Text to be set to textfield
     */
    @JDIAction
    default void input(CharSequence text) {
        input.execute(this, text);
    }

    /**
     * Returns label of text field
     *
     * @return Label of text field
     */
    @JDIAction
    default String getLabel() {
        return ((IText) linked().get("label")).getText();
    }

    /**
     * Specify text to send keys to TextField
     *
     * @param text Keys to be sent to the TextField
     */
    @JDIAction
    default void sendKeys(CharSequence text) {
        input(text);
    }

    /**
     * Clear and input text in textfield
     *
     * @param text Text to input to TextField
     */
    @JDIAction
    default void newInput(CharSequence text) {
        clear();
        input(text);
    }

    /**
     * Clear textfield
     */
    @JDIAction
    default void clear() {
        clear.execute(this);
    }

    /**
     * Focus(click) on textfield
     */
    @JDIAction
    @Override
    default void focus() {
        focus.execute(this);
    }

    /**
     * Synonym for newInput method
     *
     * @param value Element's value
     */
    @Override
    default void setValue(String value) {
        newInput(value);
    }
}