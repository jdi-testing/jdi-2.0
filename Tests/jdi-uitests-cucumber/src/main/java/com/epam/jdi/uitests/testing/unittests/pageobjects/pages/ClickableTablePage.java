package com.epam.jdi.uitests.testing.unittests.pageobjects.pages;

import com.epam.jdi.uitests.core.interfaces.complex.tables.ITable;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;

/**
 * Created by Natalia_Grebenshchik on 10/14/2015.
 */
public class ClickableTablePage extends WebPage {

    @FindBy(className = "uui-dynamic-table")
    public ITable clickableTable;

}
