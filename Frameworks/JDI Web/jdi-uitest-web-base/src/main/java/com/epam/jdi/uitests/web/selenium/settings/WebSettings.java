package com.epam.jdi.uitests.web.selenium.settings;
/*
 * Copyright 2004-2016 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * JDI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JDI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JDI. If not, see <http://www.gnu.org/licenses/>.
 */

import com.epam.jdi.tools.func.JFunc;
import com.epam.jdi.tools.func.JFunc1;
import com.epam.jdi.uitests.core.initialization.MapInterfaceToElement;
import com.epam.jdi.uitests.core.interfaces.base.IElement;
import com.epam.jdi.uitests.core.interfaces.common.*;
import com.epam.jdi.uitests.core.interfaces.complex.*;
import com.epam.jdi.uitests.core.interfaces.complex.tables.ITable;
import com.epam.jdi.uitests.core.logger.JDILogger;
import com.epam.jdi.uitests.core.settings.JDISettings;
import com.epam.jdi.uitests.web.selenium.driver.WebDriverFactory;
import com.epam.jdi.uitests.web.selenium.driver.get.driver.DriverTypes;
import com.epam.jdi.uitests.web.selenium.elements.base.Element;
import com.epam.jdi.uitests.web.selenium.elements.base.J;
import com.epam.jdi.uitests.web.selenium.elements.common.*;
import com.epam.jdi.uitests.web.selenium.elements.complex.*;
import com.epam.jdi.uitests.web.selenium.elements.complex.table.Table;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;

import static com.epam.jdi.tools.PropertyReader.fillAction;
import static com.epam.jdi.uitests.web.selenium.driver.get.driver.DriverData.DRIVER_VERSION;
import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;


/**
 * Created by Roman_Iovlev on 11/13/2015.
 */
public class WebSettings extends JDISettings {
    public static String domain;
    public static String killBrowser;
    public static JFunc1<WebElement, Boolean> SEARCH_CONDITION = WebElement::isDisplayed;
    public static boolean SEARCH_SINGLE_ELEMENT = true;
    public static Dimension BROWSER_SIZES;
    public static boolean hasDomain() {
        return domain != null && domain.contains("://");
    }
    public static WebDriver getDriver() {
        return getDriverFactory().getDriver();
    }
    public static WebDriverFactory driverFactory;
    public static WebDriverFactory getDriverFactory() {
        initDriver();
        return driverFactory;
    }
    public static String useDriver(DriverTypes driverName) {
        return getDriverFactory().registerDriver(driverName);
    }
    public static String useDriver(JFunc<WebDriver> driver) {
        return getDriverFactory().registerDriver(driver);
    }
    public static JavascriptExecutor getJSExecutor() {
        if (getDriver() instanceof JavascriptExecutor)
            return (JavascriptExecutor) getDriver();
        else
            throw new ClassCastException("JavaScript Executor doesn't support");
    }
    public static synchronized void init()  {
        logger = new JDILogger("JDI Logger");
        MapInterfaceToElement.init(defaultInterfacesMap);
        driverFactory = new WebDriverFactory();
    }
    public static boolean initialized = false;

    public static synchronized void initFromProperties() {
        init();
        JDISettings.initFromProperties();
        fillAction(p -> domain = p, "domain");
        fillAction(p -> DRIVER_VERSION = p, "drivers.version");
        fillAction(driverFactory::setDriverPath, "drivers.folder");
        fillAction(p -> driverFactory.getLatestDriver =
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
        fillAction(p -> driverFactory.pageLoadStrategy = p, "page.load.strategy");
        initialized = true;
    }

    private static Object[][] defaultInterfacesMap = new Object[][]{
            {IElement.class, Element.class},
            //{SelenideElement.class, J.class},
            {WebElement.class, J.class},
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
    };
    public static void initDriver() {
        if (!initialized)
            try {
                initFromProperties();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
    }
}