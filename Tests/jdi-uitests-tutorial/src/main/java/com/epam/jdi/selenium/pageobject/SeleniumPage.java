package com.epam.jdi.selenium.pageobject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by Roman_Iovlev on 5/23/2017.
 */
public class SeleniumPage {
    @FindBy(css = ".header__logo")
    public WebElement logo;
    @FindBy(css = ".top-navigation__list>*>*>a")
    public List<WebElement> menu;
}
