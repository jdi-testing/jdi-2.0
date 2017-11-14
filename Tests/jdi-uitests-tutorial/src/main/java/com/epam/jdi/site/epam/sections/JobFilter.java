package com.epam.jdi.site.epam.sections;

import com.epam.jdi.entities.JobSearchFilter;
import com.epam.jdi.enums.Locations;
import com.epam.jdi.site.epam.CustomElements.JTree;
import com.epam.jdi.site.epam.CustomElements.TreeDropdown;
import com.epam.jdi.uitests.core.interfaces.base.ISetValue;
import com.epam.jdi.uitests.core.interfaces.common.IButton;
import com.epam.jdi.uitests.core.interfaces.common.ILabel;
import com.epam.jdi.uitests.core.interfaces.common.ITextField;
import com.epam.jdi.uitests.core.interfaces.complex.IDropDown;
import com.epam.jdi.uitests.core.templates.base.Form;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.ByClass;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.ByValue;


/**
 * Created by Roman_Iovlev on 10/22/2015.
 */
public class JobFilter extends Form<JobSearchFilter> {
    @ByClass("job-search-input") ITextField keywords;
    @JDropdown(
        root = @FindBy(css = ".multi-select-department"),
        expand = @FindBy(css = ".default-label"),
        list = @FindBy(css = ".multi-select-dropdown li"))
    IDropDown category;

    @JTree(
        select = @FindBy(className = "career-location-box"),
        levels = {@FindBy(css = ".location-dropdown .optgroup"),
                    @FindBy(xpath = "//..//li")}
    ) TreeDropdown<Locations> location;

    @ByValue("search")
    IButton selectButton;

    @ByClass("job-search-title")
    public ILabel label;

    @Override
    public void fillAction(ISetValue element, String text) {
        element.setValue(text);
        label.click();
    }
}
