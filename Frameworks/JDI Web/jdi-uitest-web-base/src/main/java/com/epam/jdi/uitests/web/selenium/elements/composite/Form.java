package com.epam.jdi.uitests.web.selenium.elements.composite;

import com.epam.jdi.uitests.core.interfaces.base.IComposite;
import com.epam.jdi.uitests.core.interfaces.base.ISetValue;
import com.epam.jdi.uitests.core.interfaces.complex.FormFilters;
import com.epam.jdi.uitests.core.interfaces.composite.IForm;
import com.epam.jdi.uitests.web.selenium.elements.base.BaseElement;

import static com.epam.jdi.uitests.core.interfaces.complex.FormFilters.ALL;

/**
 * Created by Roman_Iovlev on 10/30/2017.
 */
public class Form<T> extends BaseElement implements IForm<T>, IComposite {
    public void fillAction(ISetValue element, String value) {
        element.setValue(value);
    }
    private FormFilters filter;
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
