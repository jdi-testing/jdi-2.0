package io.github.epam.sections;

import com.epam.jdi.uitests.web.selenium.elements.common.*;
import com.epam.jdi.uitests.web.selenium.elements.complex.*;
import com.epam.jdi.uitests.web.selenium.elements.composite.*;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.*;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.*;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import io.github.epam.entities.*;

public class LoginForm extends Form<User> {
	@XPath(".//button[@type='submit']") public Button enter;
	@Css("input[type=text]") public TextField name;
	@Css("input[type=password]") public TextField password;

}