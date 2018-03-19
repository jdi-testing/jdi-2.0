package com.epam.jdi.uitests.core.interfaces.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.base.IBaseElement;
import com.epam.jdi.uitests.core.interfaces.base.IClickable;
import com.epam.jdi.uitests.core.interfaces.base.IComposite;

public interface IPagination extends IBaseElement, IComposite {
    /**
     * Choose Next page
     */
    @JDIAction
    default void next() { ((IClickable)linked().get("next")).click(); }

    /**
     * Choose Previous page
     */
    @JDIAction
    default void previous() { ((IClickable)linked().get("prev")).click(); }

    /**
     * Choose First page
     */
    @JDIAction
    default void first() { ((IClickable)linked().get("first")).click(); }

    /**
     * Choose Last page
     */
    @JDIAction
    default void last() { ((IClickable)linked().get("last")).click(); }

    /**
     * @param index Specify page index
     *              Choose page by index
     */
    @JDIAction
    default void selectPage(int index) {
        ((ISelector)linked().get("page")).select(index);
    }

    /**
     * Is First button displayed
     */
    @JDIAction
    default boolean isFirstDisplayed() {
        return ((IClickable)linked().get("first")).displayed();
    }
    /**
     * Get Selected page index
     */
    @JDIAction
    default int getSelectedPageIndex() {
        return ((ISelector)linked().get("page")).getSelectedIndex();
    }

    /**
     * Is Next button displayed
     */
    @JDIAction
    default boolean isNextDisplayed() {
        return ((IClickable)linked().get("next")).displayed();
    }

    /**
     * Is Previous button displayed
     */
    @JDIAction
    default boolean isPreviousDisplayed() {
        return ((IClickable)linked().get("prev")).displayed();
    }

    /**
     * Is Last button displayed
     */
    @JDIAction
    default boolean isLastDisplayed() {
        return ((IClickable)linked().get("last")).displayed();
    }
}