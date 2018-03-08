package io.github.epam.pages;

import com.epam.jdi.uitests.web.selenium.elements.common.*;
import com.epam.jdi.uitests.web.selenium.elements.complex.*;
import com.epam.jdi.uitests.web.selenium.elements.composite.*;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.*;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.*;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import io.github.epam.sections.*;

public class ContactFormPage extends WebPage {
	@Css(".uui-side-bar[name='log-sidebar']") public LogSidebar logSidebar;
	@Css(".main-form") public Main main;

}