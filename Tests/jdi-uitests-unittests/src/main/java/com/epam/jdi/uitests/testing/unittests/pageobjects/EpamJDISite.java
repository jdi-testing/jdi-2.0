
package com.epam.jdi.uitests.testing.unittests.pageobjects;

import com.epam.jdi.uitests.core.interfaces.complex.IList;
import com.epam.jdi.uitests.testing.unittests.enums.Navigation;
import com.epam.jdi.uitests.testing.unittests.pageobjects.pages.*;
import com.epam.jdi.uitests.testing.unittests.pageobjects.sections.Footer;
import com.epam.jdi.uitests.testing.unittests.pageobjects.sections.Header;
import com.epam.jdi.uitests.web.selenium.elements.complex.Menu;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebSite;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JSite;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.Css;


/**
 * Created by Maksim_Palchevskii on 9/10/2015.
 */

@JSite("https://epam.github.io/JDI/")
public class EpamJDISite extends WebSite {
    @JPage(url = "/index.html", title = "Home Page")
    public static HomePage homePage;

    @JPage(url = "/contacts.html", title = "Contact Form")
    public static ContactPage contactFormPage;

    @JPage(url = "/metals-colors.html", title = "Metal and Colors")
    public static MetalsColorsPage metalsColorsPage;

    @JPage(url = "/support.html", title = "Support")
    public static SupportPage supportPage;

    @JPage(url = "/dates.html", title = "Dates")
    public static DatesPage dates;

    @FindBy(css = ".uui-profile-menu")
    public static Login login;

    @FindBy(css = ".uui-header")
    public static Header header;

    @FindBy(css = ".footer-content")
    public static Footer footer;

    @FindBy(css = ".logs li")
    public static IList<String> actionsLog;

    @FindBy(css = ".results")
    public static IList<String> resultsLog;

    @Css("[ui=label]")
    public static Menu<Navigation> navigation;
}