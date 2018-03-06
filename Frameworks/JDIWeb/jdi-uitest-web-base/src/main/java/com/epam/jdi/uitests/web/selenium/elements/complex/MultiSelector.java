package com.epam.jdi.uitests.web.selenium.elements.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.complex.IListSelector;

import java.util.List;

import static com.epam.jdi.tools.LinqUtils.*;

public abstract class MultiSelector<TEnum extends Enum>
        extends BaseSelector implements IListSelector<TEnum> {

    private String separator = ", ";
    public String getSeparator() { return separator; }
    public MultiSelector<TEnum> setValuesSeparator(String separator) {
        this.separator = separator;
        return this;
    }
    @JDIAction
    public void check(String... names) {
        expand();
        List<String> deselected = where(names, name -> !isSelected(name));
        select(toStringArray(deselected));
    }

    @JDIAction
    public void check(Integer... indexes) {
        expand();
        List<Integer> deselected = where(indexes, index -> !isSelected(index));
        select(toIntegerArray(deselected));
    }

    @JDIAction
    public void uncheck(String... names) {
        expand();
        List<String> selected = where(names, this::isSelected);
        select(toStringArray(selected));
    }

    @JDIAction
    public void uncheck(Integer... indexes) {
        expand();
        List<Integer> selected = where(indexes, this::isSelected);
        select(toIntegerArray(selected));
    }

    /**
     * @return Get names of checked options
     */
    @JDIAction
    public List<String> areSelected() {
        expand();
        return where((List<String>) getNames(), this::isSelected);
    }
    /**
     * @return Get names of unchecked options
     */
    @JDIAction
    public List<String> areDeselected() {
        expand();
        return where((List<String>) getNames(), name -> !isSelected(name));
    }
}