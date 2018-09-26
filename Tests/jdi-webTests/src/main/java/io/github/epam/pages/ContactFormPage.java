package io.github.epam.pages;

import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.Css;

import io.github.epam.sections.LogSidebar;
import io.github.epam.sections.Main;

/**
 * Contact form page
 */
public class ContactFormPage extends WebPage {
	@Css(".uui-side-bar[name='log-sidebar']") public LogSidebar logSidebar;
	@Css(".main-form") public Main main;

}