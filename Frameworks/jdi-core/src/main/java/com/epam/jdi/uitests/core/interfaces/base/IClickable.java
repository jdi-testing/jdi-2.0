package com.epam.jdi.uitests.core.interfaces.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;

import static com.epam.jdi.uitests.core.actions.common.ClickActions.click;

/**
 * Interface for clickable element
 */
public interface IClickable extends IElement {
    /**
     * Click on Element
     */
    @JDIAction("Click on element")
    default void click() {
        click.execute(this);
    }
}