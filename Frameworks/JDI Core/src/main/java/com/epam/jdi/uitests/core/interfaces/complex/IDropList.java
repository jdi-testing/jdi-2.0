package com.epam.jdi.uitests.core.interfaces.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.common.IText;

public interface IDropList<TEnum extends Enum>
        extends IDropDown<TEnum>, IListSelector<TEnum>, IText {
    @Override
    default String getValue() {
        return getText();
    }
    /**
     * Expanding DropDown
     */
    @Override @JDIAction
    default void expand() {
        if (!isExpanded())
            click();
    }
    @Override
    default void setValue(String value) {
        select(value.split(getSeparator()));
    }
}