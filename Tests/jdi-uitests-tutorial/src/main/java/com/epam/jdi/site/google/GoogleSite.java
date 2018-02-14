package com.epam.jdi.site.google;

import com.epam.jdi.site.google.pages.HomePage;
import com.epam.jdi.site.google.pages.SearchPage;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebSite;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JSite;

/**
 * Created by Roman_Iovlev on 8/30/2015.
 */
@JSite("http://google.com")
public class GoogleSite extends WebSite {
    public static HomePage homePage;
    public static SearchPage searchPage;
}
