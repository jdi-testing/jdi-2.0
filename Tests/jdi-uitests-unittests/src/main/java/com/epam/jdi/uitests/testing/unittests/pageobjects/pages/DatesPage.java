package com.epam.jdi.uitests.testing.unittests.pageobjects.pages;

import com.epam.jdi.uitests.core.interfaces.common.IFileInput;
import com.epam.jdi.uitests.core.interfaces.common.ILabel;
import com.epam.jdi.uitests.web.selenium.elements.common.DatePicker;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;

/**
 * Created by Maksim_Palchevskii on 8/17/2015.
 */
public class DatesPage extends WebPage {
    @FindBy(css = "#datepicker input")
    public DatePicker datepicker;
    @FindBy(css = "[data-provides=fileinput]")
    public IFileInput imageInput;
    @FindBy(css = "[class=filename] span")
    public ILabel uploadedFileName;
    @FindBy(css = "main form")
    public ContactForm contactForm;
}
