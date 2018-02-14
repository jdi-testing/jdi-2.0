package com.epam.jdi.site.epam.sections;

import com.epam.jdi.entities.JobSearchFilter;
import com.epam.jdi.enums.Locations;
import com.epam.jdi.site.epam.CustomElements.JTree;
import com.epam.jdi.site.epam.CustomElements.TreeDropdown;
import com.epam.jdi.uitests.core.interfaces.common.IButton;
import com.epam.jdi.uitests.core.interfaces.common.ITextField;
import com.epam.jdi.uitests.core.interfaces.complex.IDropDown;
import com.epam.jdi.uitests.web.selenium.elements.composite.Form;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.Css;

/**
 * Created by Roman_Iovlev on 10/22/2015.
 */
public class JobFilter extends Form<JobSearchFilter> {
    //Keyword or Vacancy ID
    @Css(".job-search__input")
    public ITextField keywords;

    //Skills
    @JDropdown(
            root = @FindBy(css = ".multi-select-filter"),
            expand = @FindBy(css = ".default-label"),
            list = @FindBy(css = ".multi-select-dropdown li"))
    IDropDown skills;

    @JTree(
            select = @FindBy(css = ".job-search__location"),
            levels = {@FindBy(css = "[role=tree]>li"),
                    @FindBy(xpath = "//..//li")}
    ) TreeDropdown<Locations> location;



    //Submit button
    @Css(".job-search__submit")
    public IButton selectButton;

    //Title
    //@Css(".job-search-title")
    //public Label label;

//    @Override
//    protected void setValueAction(String text, ISetValue element) {
//        element.setValue(text);
//        label.click();
//    }
}


