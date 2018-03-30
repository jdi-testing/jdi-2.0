package io.github.epam.pages;

import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.common.CheckBox;
import com.epam.jdi.uitests.web.selenium.elements.complex.ComboBox;
import com.epam.jdi.uitests.web.selenium.elements.complex.DropList;
import com.epam.jdi.uitests.web.selenium.elements.complex.Dropdown;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JComboBox;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropList;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.Css;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.XPath;
import io.github.epam.enums.ColorsList;
import io.github.epam.enums.Metals;
import io.github.epam.enums.Vegetables;
import io.github.epam.sections.LogSidebar;

public class MetalAndColorsPage extends WebPage {
	@Css(".uui-side-bar[name='log-sidebar']") public LogSidebar logSidebar;
	@JDropdown(root = @FindBy(css ="div[ui=dropdown]"),
			value = @FindBy(css =".filter-option"),
			list = @FindBy(css ="li"),
			expand = @FindBy(css =".caret")
	) public Dropdown<ColorsList> colors;
	@JComboBox(root = @FindBy(css ="div[ui=combobox]"),
			value = @FindBy(css ="input"),
			list = @FindBy(css ="li"),
			expand = @FindBy(css =".caret")
	) public ComboBox<Metals> metals;
	@JDropList(root = @FindBy(css ="div[ui=droplist]"),
			value = @FindBy(css ="button"),
			list = @FindBy(css ="li"),
			expand = @FindBy(css =".caret"),
			isselected = @FindBy(xpath ="././/input")
	) public DropList<Vegetables> vegetables;
	@XPath(".//button[@type='submit' and contains(.,'Calculate')]") public Button calculate;
	@XPath(".//button[@type='submit' and contains(.,'Submit')]") public Button submit;
	@Css("input[type=checkbox]#g1") public CheckBox g1;
	@Css("input[type=checkbox]#g2") public CheckBox g2;
	@Css("input[type=checkbox]#g3") public CheckBox g3;
	@Css("input[type=checkbox]#g4") public CheckBox g4;
	@Css("input[type=checkbox]#g5") public CheckBox g5;
	@Css("input[type=checkbox]#g6") public CheckBox g6;
	@Css("input[type=checkbox]#g7") public CheckBox g7;
	@Css("input[type=checkbox]#g8") public CheckBox g8;

}