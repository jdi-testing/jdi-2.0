package io.github.epam.pages;

import com.epam.jdi.uitests.web.selenium.elements.common.Label;
import com.epam.jdi.uitests.web.selenium.elements.common.Text;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.Css;

public class HomePage extends WebPage {
	@Css("h3[name='main-title']") public Label mainTitle;
	@Css(".main-txt") public Text jdiText;

}