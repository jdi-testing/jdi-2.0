package com.epam.jdi.uitests.testing.unittests.pageobjects.mainwindow.complextable;

import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import com.epam.jdi.uitests.win.winnium.elements.composite.TabItem;

public class ComplexTableTab extends TabItem {
    @FindBy(className = "ComplexTableView")
    public NestedComplexTableView nestedComplexTableView;
}
