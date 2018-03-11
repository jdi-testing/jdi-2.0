package com.google.pages;

import com.epam.jdi.uitests.web.selenium.elements.base.Element;
import com.epam.jdi.uitests.web.selenium.elements.complex.Elements;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.Css;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.WithText;
import com.google.custom.SearchResult;

/**
 * Created by Roman_Iovlev on 10/22/2015.
 */
public class SearchPage extends WebPage {
    @Css(".srg>.g") public Elements<SearchResult> jobsE;
    @WithText("test Framework for UI") public Element gitHubJdi;

}
