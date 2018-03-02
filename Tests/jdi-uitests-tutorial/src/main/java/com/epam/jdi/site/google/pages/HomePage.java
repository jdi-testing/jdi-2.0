package com.epam.jdi.site.google.pages;

import com.epam.jdi.uitests.core.interfaces.common.ITextField;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.Css;
import org.openqa.selenium.Keys;

/**
 * Created by Roman_Iovlev on 10/22/2015.
 */
@JPage(url = "/")
public class HomePage extends WebPage {
    @Css("[name=q]") public ITextField search;
    public void search(String text) {
        search.newInput(text + Keys.ENTER);
    }
}
