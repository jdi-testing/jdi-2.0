package io.github.epam;

import com.epam.jdi.uitests.web.selenium.elements.complex.Menu;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebSite;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JSite;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.Css;
import io.github.epam.enums.Navigation;
import io.github.epam.pages.ContactFormPage;
import io.github.epam.pages.HomePage;
import io.github.epam.pages.MetalAndColorsPage;
import io.github.epam.sections.Footer;
import io.github.epam.sections.Header;
import io.github.epam.sections.NavigationSidebar;

@JSite("https://epam.github.io/JDI/")
public class EpamGithubSite extends WebSite {
	@JPage(url = "/metals-colors.html", title = "Metal and Colors")
	public static MetalAndColorsPage metalAndColorsPage;
	@JPage(url = "/contacts.html", title = "Contact Form")
	public static ContactFormPage contactFormPage;
	@JPage(url = "/index.html", title = "Home Page")
	public static HomePage homePage;

	@Css("[ui=label]")
	public static Menu<Navigation> navigation;

	@Css("header") public static Header header;
	@Css("footer") public static Footer footer;
	@Css(".uui-side-bar") public static NavigationSidebar navigationSidebar;
}