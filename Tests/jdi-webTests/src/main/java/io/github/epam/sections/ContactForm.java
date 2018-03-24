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
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JComboBox;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropList;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.Css;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.XPath;
import io.github.epam.entities.User;

public class ContactForm extends Form<User> {
	@Css("select[ui=dropdown]") public Dropdown gender;
	@JComboBox(root = @FindBy(css ="div[ui=combobox]"),
			value = @FindBy(css ="input"),
			list = @FindBy(css ="li"),
			expand = @FindBy(css =".caret")
	) public ComboBox religion;
	@JDropList(root = @FindBy(css ="div[ui=droplist]"),
			value = @FindBy(css ="button"),
			list = @FindBy(css ="li"),
			expand = @FindBy(css =".caret"),
			isselected = @FindBy(xpath ="././/input")
	) public DropList wheather;
	@XPath(".//button[@type='submit']") public Button submit;
	@Css("input[type=checkbox]#Passport") public CheckBox passport;
	@Css("input[type=checkbox]#g5") public CheckBox g5;
	@Css("input[type=checkbox]#g6") public CheckBox g6;
	@Css("input[type=checkbox]#g7") public CheckBox g7;
	@Css("input[type=checkbox]#g8") public CheckBox g8;
	@Css("input[type=checkbox]#accept-conditions") public CheckBox acceptConditions;
	@Css("input[type=text]#Name") public TextField name;
	@Css("input[type=text]#LastName") public TextField lastName;
	@Css("input[type=text]#Position") public TextField position;
	@Css("input[type=text]#Number") public TextField number;
	@Css("input[type=text]#Seria") public TextField seria;
	@Css("textarea") public TextArea description;

}