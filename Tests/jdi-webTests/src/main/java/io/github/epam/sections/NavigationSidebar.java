package io.github.epam.sections;

import com.epam.jdi.uitests.web.selenium.elements.common.Label;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.XPath;

public class NavigationSidebar extends Section {
	@XPath(".//*[@ui='label' and contains(.,'Home')]") public Label home;
	@XPath(".//*[@ui='label' and contains(.,'Contact form')]") public Label contactForm;
	@XPath(".//*[@ui='label' and contains(.,'Support')]") public Label support;
	@XPath(".//*[@ui='label' and contains(.,'Dates')]") public Label dates;
	@XPath(".//*[@ui='label' and contains(.,'Complex Table')]") public Label complexTable;
	@XPath(".//*[@ui='label' and contains(.,'Simple Table')]") public Label simpleTable;
	@XPath(".//*[@ui='label' and contains(.,'User Table')]") public Label userTable;
	@XPath(".//*[@ui='label' and contains(.,'Table with pages')]") public Label tableWithPages;
	@XPath(".//*[@ui='label' and contains(.,'Different elements')]") public Label differentElements;
	@XPath(".//*[@ui='label' and contains(.,'Metals & Colors')]") public Label metalsColors;

}