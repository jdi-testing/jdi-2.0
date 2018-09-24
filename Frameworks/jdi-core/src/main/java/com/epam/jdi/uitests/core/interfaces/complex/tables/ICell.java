package com.epam.jdi.uitests.core.interfaces.complex.tables;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.interfaces.base.IBaseElement;
import com.epam.jdi.uitests.core.interfaces.common.IButton;

public interface ICell extends IButton {
    <T extends IBaseElement> T getAs(Class<T> clazz);

    NameNum column();

    NameNum row();

    /**
     * Clicks on the cell
     */
    default void select() {
        click();
    }
}