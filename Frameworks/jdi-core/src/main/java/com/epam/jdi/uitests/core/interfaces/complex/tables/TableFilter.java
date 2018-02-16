package com.epam.jdi.uitests.core.interfaces.complex.tables;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.utils.common.IFilter;

import java.util.ArrayList;
import java.util.List;

import static com.epam.jdi.uitests.core.settings.JDISettings.exception;
import static com.epam.jdi.uitests.core.utils.common.Filters.*;

public class TableFilter {
    private String name;
    private IFilter<String> filter;
    public String getName() { return name; }
    public IFilter<String> getFilter() { return filter; }

    public TableFilter(String template) {
        String[] split;
        if (template.matches("[^=]+\\*=[^=]*")) {
            split = template.split("\\*=");
            name = split[0];
            filter = equalsTo(split[1]);
            return;
        }
        if (template.matches("[^=]+~=[^=]*")) {
            split = template.split("~=");
            name = split[0];
            filter = contains(split[1]);
            return;
        }
        if (template.matches("[^=]+=[^=]*")) {
            split = template.split("=");
            name = split[0];
            filter = matchesRegEx(split[1]);
            return;
        }
        if (template.matches("[^=]+!=[^=]*")) {
            split = template.split("!=");
            name = split[0];
            filter = not(equalsTo(split[1]));
            return;
        }
        throw exception("Wrong searchCriteria for Cells: " + template);
    }
    public static List<TableFilter> getFilters(String[] filters) {
        List<TableFilter> result = new ArrayList<>();
        for (String colNameValue : filters)
            result.add(new TableFilter(colNameValue));
        return result;
    }

}
