package com.epam.jdi.uitests.testing.unittests.pageobjects;

import com.epam.jdi.uitests.core.interfaces.complex.ITextList;
import com.epam.jdi.uitests.testing.unittests.pageobjects.pages.*;
import com.epam.jdi.uitests.testing.unittests.pageobjects.sections.Footer;
import com.epam.jdi.uitests.testing.unittests.pageobjects.sections.Header;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebSite;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JSite;


/**
 * Created by Maksim_Palchevskii on 9/10/2015.
 */

@JSite("https://epam.github.io/JDI/")
public class EpamJDISite extends WebSite {
    @JPage(url = "/index.htm", title = "Index Page")
    public static HomePage homePage;
    @JPage(url = "/page1.htm", title = "Contact Form")
    public static ContactPage contactFormPage;
    @JPage(url = "/page2.htm", title = "Metal and Colors")
    public static MetalsColorsPage metalsColorsPage;
    @JPage(url = "/page3.htm", title = "Support")
    public static SupportPage supportPage;
    @JPage(url = "/page3.htm", title = "Support")
    public static SortingTablePage sortingTablePage;
    @JPage(url = "/page6.htm", title = "Simple Table")
    public static DatesPage dates;
    @FindBy(css = ".uui-profile-menu")
    public static Login login;
    @FindBy(css = ".uui-header")
    public static Header header;
    @FindBy(css = ".footer-content")
    public static Footer footer;
    @FindBy(css = ".logs li")
    public static ITextList actionsLog;
    @FindBy(css = ".results")
    public static ITextList resultsLog;




}