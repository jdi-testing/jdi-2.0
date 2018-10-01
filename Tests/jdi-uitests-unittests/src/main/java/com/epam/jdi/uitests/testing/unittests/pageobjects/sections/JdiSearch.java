package com.epam.jdi.uitests.testing.unittests.pageobjects.sections;

import com.epam.jdi.uitests.core.interfaces.common.IButton;
import com.epam.jdi.uitests.core.interfaces.common.ITextField;
import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.complex.Search;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import org.openqa.selenium.By;

/**
 * Created by Roman_Iovlev on 12/17/2015.
 */
public class JdiSearch extends Search {
    @FindBy(css = ".search-field input")
    public ITextField searchInput;
    //It does not work for now
    //TODO 1/10/2018 still not working
    @FindBy(xpath = "//span[@class='icon-search active']")
    public IButton searchButton;

    @Override
    public void find(String text) {
        Button btn = new Button().setLocator(By.cssSelector(".search>.icon-search"));
        btn.click();
        searchInput.newInput(text);
        //super.findAction(text);
        searchButton.click();
    }

}
