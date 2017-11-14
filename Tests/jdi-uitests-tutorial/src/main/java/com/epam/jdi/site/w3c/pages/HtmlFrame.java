package com.epam.jdi.site.w3c.pages;

import com.epam.jdi.uitests.core.interfaces.base.IComposite;
import com.epam.jdi.uitests.core.interfaces.complex.IDropDown;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;

/**
 * Created by Roman_Iovlev on 4/3/2017.
 */
public class HtmlFrame implements IComposite {
    @JDropdown(
            value = @FindBy(tagName = "select"),
            list = @FindBy(css = "select option")
    ) public IDropDown cars;
    @FindBy(tagName = "select") public IDropDown carsSimple;

}
