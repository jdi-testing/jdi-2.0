package com.epam.jdi.uitests.web.selenium.settings;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JFunc1;
import com.epam.jdi.uitests.core.exceptions.JDIUIException;
import com.epam.jdi.uitests.core.initialization.MapInterfaceToElement;
import com.epam.jdi.uitests.core.interfaces.base.IClickable;
import com.epam.jdi.uitests.core.interfaces.base.IElement;
import com.epam.jdi.uitests.core.interfaces.common.*;
import com.epam.jdi.uitests.core.interfaces.complex.*;
import com.epam.jdi.uitests.core.interfaces.complex.tables.IEntityTable;
import com.epam.jdi.uitests.core.interfaces.complex.tables.ITable;
import com.epam.jdi.uitests.core.logger.JDILogger;
import com.epam.jdi.uitests.core.settings.JDISettings;
import com.epam.jdi.uitests.web.selenium.elements.base.Element;
import com.epam.jdi.uitests.web.selenium.elements.base.J;
import com.epam.jdi.uitests.web.selenium.elements.common.*;
import com.epam.jdi.uitests.web.selenium.elements.complex.*;
import com.epam.jdi.uitests.web.selenium.elements.complex.table.EntityTable;
import com.epam.jdi.uitests.web.selenium.elements.complex.table.Table;
import org.openqa.selenium.*;

import java.util.List;
import java.util.Objects;

import static com.epam.jdi.tools.PropertyReader.fillAction;
import static com.epam.jdi.uitests.web.selenium.driver.ScreenshotMaker.screensPath;

import static com.epam.jdi.uitests.web.selenium.driver.WebDriverFactory.getDriver;
import static com.epam.jdi.uitests.web.selenium.driver.get.DownloadDriverManager.*;
import static com.epam.jdi.uitests.web.selenium.driver.get.DriverData.*;
import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static org.openqa.selenium.PageLoadStrategy.EAGER;
import static org.openqa.selenium.PageLoadStrategy.NONE;
import static org.openqa.selenium.PageLoadStrategy.NORMAL;

public class WebSettings extends JDISettings {
    public static String domain;
    public static String killBrowser;
    public static JFunc1<WebElement, Boolean> SEARCH_CONDITION = WebElement::isDisplayed;
    public static boolean SEARCH_SINGLE_ELEMENT = true;
    public static Dimension BROWSER_SIZES;
    public static boolean hasDomain() {
        return domain != null && domain.contains("://");
    }
    public static JavascriptExecutor getJSExecutor() {
        if (getDriver() instanceof JavascriptExecutor)
            return (JavascriptExecutor) getDriver();
        else
            throw new ClassCastException("JavaScript Executor doesn't support");
    }
    public static synchronized void init()  {
        logger = JDILogger.instance("JDI");
        MapInterfaceToElement.init(defaultInterfacesMap);
    }
    public static boolean initialized = false;

    public static synchronized void initFromProperties() {
        init();
        JDISettings.initFromProperties();
        fillAction(p -> domain = p, "domain");
        fillAction(p -> DRIVER_VERSION = p, "drivers.version");
        fillAction(p -> DRIVERS_FOLDER = p, "drivers.folder");
        fillAction(p -> screensPath = p, "screens.folder");
        fillAction(p -> DOWNLOAD_DRIVER =
                p.toLowerCase().equals("true") || p.toLowerCase().equals("1"), "driver.getLatest");
        fillAction(p -> asserter.doScreenshot(p), "screenshot.strategy");
        killBrowser = "afterAndBefore";
        fillAction(p -> killBrowser = p, "browser.kill");
        fillAction(p -> {
            p = p.toLowerCase();
            if (p.equals("soft"))
                p = "any, multiple";
            if (p.equals("strict"))
                p = "visible, single";
            if (p.split(",").length == 2) {
                List<String> params = asList(p.split(","));
                if (params.contains("visible") || params.contains("displayed"))
                    SEARCH_CONDITION = WebElement::isDisplayed;
                if (params.contains("any") || params.contains("all"))
                    SEARCH_CONDITION = Objects::nonNull;
                if (params.contains("single"))
                    SEARCH_SINGLE_ELEMENT = true;
                if (params.contains("multiple"))
                    SEARCH_SINGLE_ELEMENT = false;
            }
        }, "search.element.strategy" );
        fillAction(p -> {
            String[] split = null;
            if (p.split(",").length == 2)
                split = p.split(",");
            if (p.toLowerCase().split("x").length == 2)
                split = p.toLowerCase().split("x");
            if (split != null)
                BROWSER_SIZES = new Dimension(parseInt(split[0].trim()), parseInt(split[1].trim()));
        }, "browser.size");
        fillAction(p -> PAGE_LOAD_STRATEGY = getPageLoadStrategy(p), "page.load.strategy");
        initialized = true;
    }
    private static PageLoadStrategy getPageLoadStrategy(String strategy) {
        switch (strategy.toLowerCase()) {
            case "normal": return NORMAL;
            case "none": return NONE;
            case "eager": return EAGER;
        }
        return NORMAL;
    }
    private static Object[][] defaultInterfacesMap = new Object[][]{
            {IElement.class, Element.class},
            //{SelenideElement.class, J.class},
            {WebElement.class, J.class},
            {IClickable.class, Button.class},
            {IButton.class, Button.class},
            {IComboBox.class, ComboBox.class},
            {ILink.class, Link.class},
            {ISelector.class, Selector.class},
            {IText.class, Text.class},
            {IImage.class, Image.class},
            {ITextArea.class, TextArea.class},
            {ITextField.class, TextField.class},
            {ILabel.class, Label.class},
            {IDropDown.class, Dropdown.class},
            {IDropList.class, DropList.class},
            {ITable.class, Table.class},
            {ICheckBox.class, CheckBox.class},
            {IRadioButtons.class, RadioButtons.class},
            {ICheckList.class, CheckList.class},
            {ITabs.class, Tabs.class},
            {IMenu.class, Menu.class},
            {IFileInput.class, FileInput.class},
            {IDatePicker.class, DatePicker.class},
            {IEntityTable.class, EntityTable.class},
            {IList.class, Elements.class},
            {List.class, Elements.class}
    };
    public static void initDriver() {
        if (!initialized)
            try {
                initFromProperties();
            } catch (Exception ex) {
                throw new JDIUIException(ex);
            }
    }
}