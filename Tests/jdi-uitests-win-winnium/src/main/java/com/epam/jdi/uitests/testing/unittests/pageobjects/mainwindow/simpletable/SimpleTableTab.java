package com.epam.jdi.uitests.testing.unittests.pageobjects.mainwindow.simpletable;

import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import com.epam.jdi.uitests.win.winnium.elements.composite.TabItem;

public class SimpleTableTab extends TabItem {
    @FindBy(className = "SimpleTableView")
    public NestedSimpleTableView nestedSimpleTableView;
}
