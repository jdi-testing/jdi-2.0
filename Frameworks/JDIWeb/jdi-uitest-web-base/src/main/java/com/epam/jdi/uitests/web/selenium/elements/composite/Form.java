package com.epam.jdi.uitests.web.selenium.elements.composite;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.interfaces.base.IComposite;
import com.epam.jdi.uitests.core.interfaces.base.ISetValue;
import com.epam.jdi.uitests.core.interfaces.complex.FormFilters;
import com.epam.jdi.uitests.core.interfaces.composite.IForm;
import com.epam.jdi.uitests.web.selenium.elements.base.BaseElement;

import static com.epam.jdi.uitests.core.interfaces.complex.FormFilters.ALL;

/**
 * From composite element
 * @param <T>
 */
public class Form<T> extends BaseElement implements IForm<T>, IComposite {
    /**
     * Fills action
     * @param element element
     * @param value value
     */
    public void fillAction(ISetValue element, String value) {
        element.setValue(value);
    }
    private FormFilters filter = ALL;

    /**
     * Gets filter
     * @return filter
     */
    public FormFilters getFilter() {
        return filter;
    }

    /**
     * Sets filter to ALL
     */
    public void setFilterAll() {
        filter = ALL;
    }

    /**
     * Sets filter to specified value
     * @param filter filter
     */
    public void setFilter(FormFilters filter) {
        this.filter = filter;
    }
}
