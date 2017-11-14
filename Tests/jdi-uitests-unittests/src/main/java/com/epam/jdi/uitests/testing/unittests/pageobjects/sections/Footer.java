package com.epam.jdi.uitests.testing.unittests.pageobjects.sections;

import com.epam.jdi.uitests.core.templates.base.Section;
import com.epam.jdi.uitests.web.selenium.elements.common.Link;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;

/**
 * Created by Fail on 15.09.2015.
 */
public class Footer extends Section {
    @FindBy(partialLinkText = "About")
    public Link about;

}
