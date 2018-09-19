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
     * Chooses the Next page
     */
    @JDIAction
    default void next() {
        ((IClickable) linked().get("next")).click();
    }

    /**
     * Chooses the Previous page
     */
    @JDIAction
    default void previous() {
        ((IClickable) linked().get("prev")).click();
    }

    /**
     * Chooses the First page
     */
    @JDIAction
    default void first() {
        ((IClickable) linked().get("first")).click();
    }

    /**
     * Chooses the Last page
     */
    @JDIAction
    default void last() {
        ((IClickable) linked().get("last")).click();
    }

    /**
     * @param index index of a page to select
     */
    @JDIAction
    default void selectPage(int index) {
        ((ISelector) linked().get("page")).select(index);
    }

    /**
     * @return the selected page index
     */
    @JDIAction
    default int pageSelected() {
        return ((ISelector) linked().get("page")).getSelectedIndex();
    }
}