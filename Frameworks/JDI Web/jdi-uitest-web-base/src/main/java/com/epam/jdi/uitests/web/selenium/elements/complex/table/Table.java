package com.epam.jdi.uitests.web.selenium.elements.complex.table;

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

import com.epam.jdi.tools.func.JFunc;
import com.epam.jdi.uitests.core.interfaces.ISetup;
import com.epam.jdi.uitests.core.interfaces.complex.tables.ICell;
import com.epam.jdi.uitests.core.interfaces.complex.tables.ITable;
import com.epam.jdi.uitests.core.templates.base.TTable;
import com.epam.jdi.uitests.core.interfaces.complex.tables.TableRow;
import com.epam.jdi.uitests.core.templates.base.LinkedElements;
import com.epam.jdi.uitests.web.selenium.elements.base.Element;
import com.epam.jdi.uitests.web.selenium.elements.complex.Selector;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JTable;
import org.openqa.selenium.By;

import java.lang.reflect.Field;
import java.util.List;

import static com.epam.jdi.uitests.core.settings.JDISettings.exception;
import static com.epam.jdi.uitests.web.selenium.elements.base.LinkedSetup.setUpLinked;
import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.WebAnnotationsUtil.findByToBy;
import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.FillFromAnnotationRules.fieldHasAnnotation;
import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Created by Roman Iovlev on 10.03.2017
 */
public class Table extends Element implements ITable, ISetup {
    private TTable tTable = new TTable();
    public List<ICell> allCells() { return tTable.allCells(); }
    public <T> T validation(JFunc<T> results) {
        return tTable.validation(results);
    }
    public ITable waitWhile(int timeoutSec) {
        return tTable.waitWhile(timeoutSec);
    }
    public TableRow rows() {
        return tTable.rows();
    }
    public TableRow columns() {
        return tTable.columns();
    }
    public void clean() { tTable.clean(); }
    public ITable useCache(boolean value) {
         tTable.setUseCache(value);
         return tTable;
    }
    @Override
    public LinkedElements linked() { return tTable.linked(); }

    public Table() {
        tTable.setEngine(engine());
        linked().add("cell", new Element().setLocator(
            By.xpath("//tr[{1}]/td[{0}]")));
        linked().add("cells",
            getSelector(By.tagName("td")));
        linked().add("row",
            getSelector(By.xpath("//tr[%s]/td")));
        linked().add("column",
            getSelector(By.xpath("//tr/td[%s]")));
        linked().add("rowHeader",
            getSelector(By.xpath("//tr/td[1]")));
        linked().add("columnHeader",
            getSelector(By.xpath("//th")));
        linked().add("footer",
            getSelector(By.xpath("//tfoot//td")));
    }
    private Selector getSelector(By locator) {
        return new Selector().setLocator(locator);
    }

    public void setup(Field field) {
        if (!fieldHasAnnotation(field, JTable.class, ITable.class))
            return;
        JTable jTable = field.getAnnotation(JTable.class);
        By root = findByToBy(jTable.root());
        if (root != null) setEngine(root);
        setUpLinked(this, jTable.cell(), "cell", by -> new Element().setLocator(by));
        setUpLinked(this, jTable.cells(), "cells", this::getSelector);
        setUpLinked(this, jTable.row(), "row", this::getSelector);
        setUpLinked(this, jTable.column(), "column", this::getSelector);
        setUpLinked(this, jTable.rowNames(), "rowNames", this::getSelector);
        setUpLinked(this, jTable.headers(), "headers", this::getSelector);
        setUpLinked(this, jTable.footer(), "footer", this::getSelector);

        int rowStartIndex = jTable.rowStartIndex();
        if (rowStartIndex != -1) rows().setStart(rowStartIndex);
        int colStartIndex = jTable.colStartIndex();
        if (colStartIndex != -1) rows().setStart(colStartIndex);

        if (jTable.rowsHeader().length > 0)
            rows().setHeaders(asList(jTable.rowsHeader()));
        if (jTable.header().length > 0)
            columns().setHeaders(asList(jTable.header()));
        int x = 0;
        int y = 0;
        if (jTable.height() > 0)
            y = jTable.height();
        if (jTable.width() > 0)
            x = jTable.width();
        if ((x ==0 || y == 0) && isNotEmpty(jTable.size())) {
            String[] split = jTable.size().split("x");
            if (split.length == 1)
                split = jTable.size().split("X");
            if (split.length == 1)
                split = jTable.size().split(":");
            if (split.length != 2)
                throw exception("Can't setup Table from attribute. Bad size: " + jTable.size());
            if (x == 0) x = parseInt(split[1]);
            if (y == 0) y = parseInt(split[0]);
        }
        if (y > 0) rows().setSize(y);
        if (x > 0) columns().setSize(x);
        tTable.headerType = jTable.headerType();
        useCache(jTable.useCache());
    }

}
