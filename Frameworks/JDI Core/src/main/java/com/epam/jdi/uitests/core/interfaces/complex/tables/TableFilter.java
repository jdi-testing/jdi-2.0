package com.epam.jdi.uitests.core.interfaces.complex.tables;

/* The MIT License
 *
 * Copyright 2004-2017 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:

 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

 */

/**
 * Created by Roman Iovlev on 10.03.2017
 */

import com.epam.jdi.uitests.core.utils.common.IStringFilter;

import java.util.ArrayList;
import java.util.List;

import static com.epam.jdi.uitests.core.settings.JDISettings.exception;
import static com.epam.jdi.uitests.core.utils.common.Filters.*;

public class TableFilter {
    private String name;
    private IStringFilter filter;
    public String getName() { return name; }
    public IStringFilter getFilter() { return filter; }

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
            filter = isNot(split[1]);
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
