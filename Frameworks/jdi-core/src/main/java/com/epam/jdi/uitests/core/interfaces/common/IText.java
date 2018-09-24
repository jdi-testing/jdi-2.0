package com.epam.jdi.uitests.core.interfaces.common;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.base.IElement;
import com.epam.jdi.uitests.core.interfaces.base.IHasValue;

import static com.epam.jdi.uitests.core.actions.common.TextActions.getText;

/**
 * Interface for elements with text
 */
public interface IText extends IHasValue, IElement {
    /**
     * Get Element’s text
     *
     * @return Element’s text
     */
    @JDIAction
    default String getText() {
        return getText.execute(this);
    }

    /**
     * Wait while Element’s text contains expected text
     *
     * @param text Specify expected text
     * @return Element’s text
     */
    @JDIAction
    default String waitText(String text) {
        return wait(this::getText, t -> t.equals(text));
    }

    /**
     * Wait while Element’s text matches regEx
     *
     * @param regEx Specify expected regular expression Text
     * @return Element’s text
     */
    @JDIAction
    default String waitTextMatch(String regEx) {
        return wait(this::getText, t -> t.matches(regEx));
    }
}