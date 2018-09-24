package com.epam.jdi.uitests.core.interfaces.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;

import static com.epam.jdi.uitests.core.actions.base.ElementActions.*;

/**
 * Interface for elements can be visible
 */
public interface IVisible {
    /**
     * Waits while Element becomes visible
     *
     * @return Became the element visible or not
     */
    @JDIAction
    default boolean displayed() {
        return wait.execute(this, this::displayedNow);
    }

    /**
     * Waits while Element becomes invisible
     *
     * @return Became the element invisible or not
     */
    @JDIAction
    default boolean vanished() {
        return wait.execute(this, () -> !displayedNow());
    }

    /**
     * Check is Element visible
     *
     * @return Is the Element visible or not
     */
    @JDIAction
    default boolean displayedNow() {
        return displayed.execute(this);
    }

    /**
     * Check is Element hidden
     *
     * @return Is the Element hidden or not
     */
    @JDIAction
    default boolean hidden() {
        return !displayedNow();
    }

    /**
     * Check is Element enabled
     *
     * @return Is the Element enabled or not
     */
    @JDIAction
    default boolean enabled() {
        return enabled.execute(this);
    }

    /**
     * Check is Element disabled
     *
     * @return Is the Element disabled or not
     */
    @JDIAction
    default boolean disabled() {
        return !enabled();
    }

}