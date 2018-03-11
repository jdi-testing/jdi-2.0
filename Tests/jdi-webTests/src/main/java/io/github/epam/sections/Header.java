package io.github.epam.sections;

import com.epam.jdi.uitests.web.selenium.elements.common.*;
import com.epam.jdi.uitests.web.selenium.elements.complex.*;
import com.epam.jdi.uitests.web.selenium.elements.composite.*;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.*;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.*;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;

public class Header extends Section {
	@Css("form") public LoginForm loginForm;
	@XPath(".//button[@type='submit']") public Button logout;
	@Css("img#epam_logo") public Image epamLogo;
	@Css("img#user-icon") public Image userIcon;
	@XPath(".//*[@ui='label']") public Label piterChailovskii;
	@Css("input[type=text]") public TextField textField437465;

}