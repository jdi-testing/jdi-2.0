package io.github.epam.sections;

import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.common.Image;
import com.epam.jdi.uitests.web.selenium.elements.common.Label;
import com.epam.jdi.uitests.web.selenium.elements.common.TextField;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.Css;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.XPath;

public class Header extends Section {
	@Css("form") public LoginForm loginForm;
	@XPath(".//button[@type='submit']") public Button logout;
	@Css("img#epam_logo") public Image epamLogo;
	@Css("img#user-icon") public Image userIcon;
	@XPath(".//*[@ui='label']") public Label piterChailovskii;
	@Css("input[type=text]") public TextField textField437465;

}