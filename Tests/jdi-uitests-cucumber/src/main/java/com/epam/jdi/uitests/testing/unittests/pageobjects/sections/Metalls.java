package com.epam.jdi.uitests.testing.unittests.pageobjects.sections;

import com.epam.jdi.uitests.core.annotations.Name;
import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;

/**
 * Created by Roman_Iovlev on 10/18/2017.
 */
public class Metalls extends Section {
    @Name("КНОПКА")
    @FindBy(id = "calculate-button")
    public Button calculateButton;
}
