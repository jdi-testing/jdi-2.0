package com.epam.jdi.uitests.web.selenium.elements.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.interfaces.complex.IListSelector;

public abstract class MultiSelector<TEnum extends Enum>
        extends BaseSelector implements IListSelector<TEnum> {

    private String separator = ", ";
    public String getSeparator() { return separator; }
    public MultiSelector<TEnum> setValuesSeparator(String separator) {
        this.separator = separator;
        return this;
    }
}