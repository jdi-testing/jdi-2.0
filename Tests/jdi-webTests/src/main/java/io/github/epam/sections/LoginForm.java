package io.github.epam.sections;

import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.common.TextField;
import com.epam.jdi.uitests.web.selenium.elements.composite.Form;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.Css;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.XPath;
import io.github.epam.entities.User;

public class LoginForm extends Form<User> {
	@XPath(".//button[@type='submit']") public Button enter;
	@Css("input[type=text]") public TextField name;
	@Css("input[type=password]") public TextField password;

}