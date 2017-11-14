package com.epam.jdi.uitests.core.templates.base;

import com.epam.jdi.tools.func.JAction2;
import com.epam.jdi.uitests.core.interfaces.base.ISetValue;
import com.epam.jdi.uitests.core.interfaces.complex.FormFilters;
import com.epam.jdi.uitests.core.interfaces.composite.IForm;

import static com.epam.jdi.uitests.core.interfaces.complex.FormFilters.ALL;

/**
 * Created by Roman_Iovlev on 10/30/2017.
 */
public class Form<T> extends Section implements IForm<T> {
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
