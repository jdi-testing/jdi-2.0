package com.epam.jdi.uitests.testing.unittests.pageobjects.mainwindow.dates;

import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import com.epam.jdi.uitests.win.winnium.elements.composite.TabItem;

public class DatesTab extends TabItem {
    @FindBy(className = "DatesView")
    public NestedDatesView nestedDatesView;
}
