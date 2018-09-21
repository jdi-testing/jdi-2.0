package com.epam.jdi.uitests.web.selenium.elements.complex.table;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JFunc;
import com.epam.jdi.uitests.core.interfaces.ISetup;
import com.epam.jdi.uitests.core.interfaces.complex.tables.ICell;
import com.epam.jdi.uitests.core.interfaces.complex.tables.ITable;
import com.epam.jdi.uitests.core.interfaces.complex.tables.TableRow;
import com.epam.jdi.uitests.core.interfaces.complex.tables.TableVerify;
import com.epam.jdi.uitests.core.templates.base.LinkedElements;
import com.epam.jdi.uitests.core.templates.base.TTable;
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
 * Table complex element
 */
public class Table extends Element implements ITable, ISetup {
    private TTable tTable = new TTable();

    /**
     * Returns all cells
     * @return list of cells
     */
    public List<ICell> allCells() { return tTable.allCells(); }

    /**
     * Performs validation
     * @param results results
     * @param <T> type
     * @return validation results
     */
    public <T> T validation(JFunc<T> results) {
        return tTable.validation(results);
    }

    /**
     * Waits for specified number of seconds
     * @param timeoutSec wait time in seconds
     * @return TableVerify
     */
    public TableVerify waitWhile(int timeoutSec) {
        return tTable.waitWhile(timeoutSec);
    }

    /**
     * Asserts table
     * @param timeoutSec wait time in seconds
     * @return TableVerify
     */
    public TableVerify assertThat(int timeoutSec) {
        return tTable.assertThat(timeoutSec);
    }

    /**
     * Returns rows
     * @return TableRow
     */
    public TableRow rows() {
        return tTable.rows();
    }

    /**
     * Returns columns
     * @return TableRow
     */
    public TableRow columns() {
        return tTable.columns();
    }

    /**
     * Cleans table
     */
    public void clean() { tTable.clean(); }

    /**
     * Sets using cache value
     * @param value value
     * @return ITable
     */
    public ITable useCache(boolean value) {
         tTable.setUseCache(value);
         return tTable;
    }

    /**
     * Returns linked elements
     * @return LinkedElements
     */
    @Override
    public LinkedElements linked() { return tTable.linked(); }

    /**
     * Constructs table
     */
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

    /**
     * Gets selector
     * @param locator locator
     * @return Selector
     */
    private Selector getSelector(By locator) {
        return new Selector().setLocator(locator);
    }

    /**
     * Sets up element
     * @param field field
     */
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
