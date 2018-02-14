package com.epam.jdi.site.epam.sections;

import com.epam.jdi.entities.Attendee;
import com.epam.jdi.uitests.core.interfaces.common.IButton;
import com.epam.jdi.uitests.core.interfaces.common.ITextArea;
import com.epam.jdi.uitests.core.interfaces.common.ITextField;
import com.epam.jdi.uitests.core.interfaces.complex.IDropDown;
import com.epam.jdi.uitests.web.selenium.elements.composite.Form;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.Css;

/**
 * Created by Roman_Iovlev on 10/23/2015.
 */
public class AddCVForm extends Form<Attendee> {

    @Css("[placeholder='First Name*']") ITextField name;
    @Css("[placeholder='Last Name*']") ITextField lastName;
    @Css("[placeholder='Email*']") ITextField email;

    @JDropdown( root = @FindBy(css = ".country-field"),
        expand = @FindBy(css = ".arrow"),
        list = @FindBy(css = ".options li"))
    public IDropDown country;

    @JDropdown( root = @FindBy(css = ".city-field"),
            expand = @FindBy(css = ".arrow"),
            list = @FindBy(css = ".options li"))
    public IDropDown city;

/*    @Css(".file-upload")
    RFileInput cv;*/

    @Css(".comment__input")
    ITextArea comment;

    @Css("button.button-ui")
    IButton submit;

}
