package com.epam.jdi.uitests.testing.unittests.pageobjects.mainwindow.contactform;

import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import com.epam.jdi.uitests.win.winnium.elements.composite.TabItem;

public class ContactFormTab extends TabItem {
    @FindBy(className = "ContactFormView")
    public NestedContactFormView nestedContactFormView;
}
