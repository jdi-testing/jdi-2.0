package com.epam.jdi.uitests.testing.unittests.pageobjects.pages;

import com.epam.jdi.uitests.core.interfaces.common.IButton;
import com.epam.jdi.uitests.core.interfaces.complex.IMenu;
import com.epam.jdi.uitests.web.selenium.elements.common.Image;
import com.epam.jdi.uitests.web.selenium.elements.common.Label;
import com.epam.jdi.uitests.web.selenium.elements.common.Link;
import com.epam.jdi.uitests.web.selenium.elements.common.Text;
import com.epam.jdi.uitests.web.selenium.elements.complex.Search;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JMenu;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JSearch;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.Css;

/**
 * Created by Maksim_Palchevskii on 8/17/2015.
 */

public class HomePage extends WebPage {

    @FindBy(css = ".m-l8")
    public IMenu menu1;

    @FindBy(css = ".main-title")
    public Label titleLabel;
    @FindBy(css = ".main-txt")
    public Text text;
    @FindBy(css = ".epam-logo img")
    public Image logoImage;
    @FindBy(linkText = "About")
    public Link about;

    @FindBy(css = "[class=icon-search]")
    public IButton openSearchButton;

    @Css(".main-title")
    public Label titleConstructor;

    @FindBy(text = "About")
    public Link aboutJ;

    @FindBy(css = ".search")
    public IButton openSearch;

    @JSearch(
            root = @FindBy(css = ".search"),
            input = @FindBy(tagName = "input"),
            searchButton = @FindBy(css = ".icon-search")
    ) public Search jSearchRootInputSearchButton;

    @JSearch(
            input = @FindBy(tagName = "input"),
            searchButton = @FindBy(css = ".icon-search")
    ) public Search jSearchInputSearchButton;

    @JSearch(
            root = @FindBy(css = ".search"),
            input = @FindBy(tagName = "input")
    ) public Search jSearchRootInput;


    @JMenu({@FindBy (css = ".uui-navigation.nav.navbar-nav.m-l8>li>a"),
            @FindBy (css = ".dropdown-menu>li>a")}
    ) public IMenu menu;
}
