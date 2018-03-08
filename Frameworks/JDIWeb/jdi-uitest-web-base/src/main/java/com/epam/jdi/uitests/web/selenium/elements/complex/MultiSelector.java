package com.epam.jdi.uitests.web.selenium.elements.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.complex.IListSelector;

import java.util.List;

import static com.epam.jdi.tools.LinqUtils.where;

public abstract class MultiSelector<TEnum extends Enum>
        extends BaseSelector implements IListSelector<TEnum> {

    private String separator = ", ";
    public String getSeparator() { return separator; }
    public MultiSelector<TEnum> setValuesSeparator(String separator) {
        this.separator = separator;
        return this;
    }

    /**
     * @return Get names of checked options
     */
    @JDIAction
    public List<String> areSelected() {
        expand();
        return where((List<String>)getNames(), this::isSelected);
    }
    /**
     * @return Get names of unchecked options
     */
    @JDIAction
    public List<String> areDeselected() {
        expand();
        return where((List<String>)getNames(), name -> !isSelected(name));
    }
}