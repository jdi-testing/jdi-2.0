package io.github.epam.sections;

import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.common.CheckBox;
import com.epam.jdi.uitests.web.selenium.elements.common.TextArea;
import com.epam.jdi.uitests.web.selenium.elements.common.TextField;
import com.epam.jdi.uitests.web.selenium.elements.complex.ComboBox;
import com.epam.jdi.uitests.web.selenium.elements.complex.DropList;
import com.epam.jdi.uitests.web.selenium.elements.complex.Dropdown;
import com.epam.jdi.uitests.web.selenium.elements.composite.Form;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropList;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.Css;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.XPath;
import io.github.epam.entities.User;

public class ContactForm extends Form<User> {
	@Css("select[ui=dropdown]") public Dropdown gender;
	@Css("#religion") public ComboBox religion;
	@JDropList(root = @FindBy(css ="div[ui=droplist]"),
			value = @FindBy(css ="button"),
			list = @FindBy(css ="li"),
			expand = @FindBy(css =".caret"),
			isselected = @FindBy(xpath ="././/input")
	) public DropList wheather;
	@XPath(".//button[@type='submit']") public Button submit;
	@Css("#passport") public CheckBox passport;
	@Css("input[type=checkbox]#accept-conditions") public CheckBox acceptConditions;
	@Css("#name") public TextField name;
	@Css("#last-name") public TextField lastName;
	@Css("#position") public TextField position;
	@Css("#passport-number") public TextField number;
	@Css("#passport-seria") public TextField seria;
	@Css("textarea") public TextArea description;

}