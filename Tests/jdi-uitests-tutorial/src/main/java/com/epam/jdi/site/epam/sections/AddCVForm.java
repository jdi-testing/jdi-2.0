package com.epam.jdi.site.epam.sections;

import com.epam.jdi.entities.Attendee;
import com.epam.jdi.uitests.core.interfaces.common.IButton;
import com.epam.jdi.uitests.core.interfaces.common.ITextArea;
import com.epam.jdi.uitests.core.interfaces.common.ITextField;
import com.epam.jdi.uitests.core.interfaces.complex.IDropDown;
import com.epam.jdi.uitests.core.templates.base.Form;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.Attribute;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.ByTitle;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.Css;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.XPath;

/**
 * Created by Roman_Iovlev on 10/23/2015.
 */
public class AddCVForm extends Form<Attendee> {
    @Css("[placeholder='First Name']") ITextField name;
    @Css("[placeholder='Last Name']") ITextField lastName;
    @Attribute(name ="placeholder", value="Email") ITextField email;

    @JDropdown(
            root = @FindBy(className = "country-wrapper"),
            value = @FindBy(className = "arrow"),
            list = @FindBy(xpath = "*root*//li[contains(@id,'applicantCountry') and .='%s']")
    ) public IDropDown country;

    @JDropdown(
            root = @FindBy(className = "city-wrapper"),
            expand = @FindBy(className = "arrow"),
            list = @FindBy(css = "*root*li[id*=applicantCity]")
        //jlist = @JFindBy(xpath = "*root*//*[contains(@id,'select-box-applicantCity')]//li")
    ) IDropDown city;

    @Css(".comment-input") ITextArea comment;

    @XPath( "//*[.='Submit']") IButton submit;
    @XPath("//*[.='Cancel']") IButton cancel;

    @ByTitle("Reload") public IButton reload;
}
