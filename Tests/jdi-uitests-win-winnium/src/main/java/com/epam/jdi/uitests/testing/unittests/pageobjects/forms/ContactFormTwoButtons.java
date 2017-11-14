package com.epam.jdi.uitests.testing.unittests.pageobjects.forms;

import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import com.epam.jdi.uitests.win.winnium.elements.common.Button;

public class ContactFormTwoButtons extends ContactForm {
    @FindBy(xpath = "./*[starts-with(@AutomationId, 'calculate')]")
    public Button calculateButton;
}
