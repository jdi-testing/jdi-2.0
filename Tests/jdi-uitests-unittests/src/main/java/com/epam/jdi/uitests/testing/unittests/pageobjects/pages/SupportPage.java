package com.epam.jdi.uitests.testing.unittests.pageobjects.pages;


import com.epam.jdi.uitests.core.interfaces.complex.tables.ITable;
import com.epam.jdi.uitests.testing.unittests.entities.SupportEntity;
import com.epam.jdi.uitests.web.selenium.elements.complex.table.EntityTable;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JTable;

import static com.epam.jdi.uitests.core.interfaces.complex.tables.TableHeaderTypes.ALL_HEADERS;
import static com.epam.jdi.uitests.core.interfaces.complex.tables.TableHeaderTypes.NO_HEADERS;

/**
 * Created by Maksim_Palchevskii on 8/17/2015.
 */
public class SupportPage extends WebPage {
    @JTable(
        root = @FindBy(css = ".uui-table.stripe")
    ) public ITable tableRoot;

    @JTable(
            root = @FindBy(css = ".uui-table.stripe"),
            header = {"Type", 	"Now", 	"Plans"}
    ) public ITable tableRootRowHeader;

    @JTable(
            root = @FindBy(css = ".uui-table.stripe>tbody"),
            header = {"Type", 	"Now", 	"Plans"},
            row = @FindBy(tagName = "tr")
    ) public ITable tableRootHeaderRow;

    @FindBy(css = "table[class='uui-table stripe']")
    public ITable supportTable;
    @FindBy(css = ".uui-table") @JTable(headerType = ALL_HEADERS)
    public ITable tableWithHeaders;
    @FindBy(css = ".uui-table") @JTable(headerType = NO_HEADERS)
    public ITable tableWithoutHeaders;
    @FindBy(css = ".uui-table")
    public EntityTable<SupportEntity, ?> entityTable;
    @JTable(
            root = @FindBy(css = "root locator"),
            header = {"header1", "header2"},
            rowsHeader = {"row1", "row2"},
            cell = @FindBy(css = "root locator"),
            row = @FindBy(css = "root locator"),
            column = @FindBy(css = "root locator"),
            useCache = false,
            height = 5,
            width = 1,
            colStartIndex = 1,
            rowStartIndex = 1)
    public ITable jTable;

}
