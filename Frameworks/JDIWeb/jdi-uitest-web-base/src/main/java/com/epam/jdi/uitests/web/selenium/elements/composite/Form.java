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

public class Form<T> extends BaseElement implements IForm<T>, IComposite {
    public void fillAction(ISetValue element, String value) {
        element.setValue(value);
    }
    private FormFilters filter = ALL;
    public FormFilters getFilter() {
        return filter;
    }
    public void setFilterAll() {
        filter = ALL;
    }
    public void setFilter(FormFilters filter) {
        this.filter = filter;
    }
}
