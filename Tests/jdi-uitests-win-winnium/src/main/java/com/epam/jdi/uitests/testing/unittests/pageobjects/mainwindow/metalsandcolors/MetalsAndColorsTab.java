package com.epam.jdi.uitests.testing.unittests.pageobjects.mainwindow.metalsandcolors;

import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import com.epam.jdi.uitests.win.winnium.elements.composite.TabItem;

public class MetalsAndColorsTab extends TabItem {
    @FindBy(className = "MetalsAndColorsView")
    public NestedMetalsAndColorsView nestedMetalsAndColorsView;
}
