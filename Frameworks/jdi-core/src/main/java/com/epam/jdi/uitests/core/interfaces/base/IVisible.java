package com.epam.jdi.uitests.core.interfaces.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;

import static com.epam.jdi.uitests.core.actions.base.ElementActions.*;

/**
 * Interface for visible element
 */
public interface IVisible {
    /**
     * Waits while Element becomes visible
     */
    @JDIAction
    default boolean displayed() {
        return wait.execute(this, this::displayedNow);
    }

    /**
     * Waits while Element becomes invisible
     */
    @JDIAction
    default boolean vanished() {
        return wait.execute(this, () -> !displayedNow());
    }

    /**
     * @return Check is Element visible
     */
    @JDIAction
    default boolean displayedNow() {
        return displayed.execute(this);
    }

    /**
     * @return Check is Element hidden
     */
    @JDIAction
    default boolean hidden() {
        return !displayedNow();
    }

    /**
     * @return Check is Element enabled
     */
    @JDIAction
    default boolean enabled() {
        return enabled.execute(this);
    }

    /**
     * @return Check is Element disabled
     */
    @JDIAction
    default boolean disabled() {
        return !enabled();
    }

}