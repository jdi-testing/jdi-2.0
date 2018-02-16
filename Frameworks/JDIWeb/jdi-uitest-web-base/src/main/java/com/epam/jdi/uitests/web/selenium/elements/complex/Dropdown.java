package com.epam.jdi.uitests.web.selenium.elements.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.interfaces.ISetup;
import com.epam.jdi.uitests.core.interfaces.complex.IDropDown;
import com.epam.jdi.uitests.core.interfaces.complex.ISelector;
import com.epam.jdi.uitests.web.selenium.elements.common.Input;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.lang.reflect.Field;
import java.util.List;

import static com.epam.jdi.uitests.core.settings.JDISettings.exception;
import static com.epam.jdi.uitests.web.selenium.elements.base.LinkedSetup.setupDropElement;
import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.WebAnnotationsUtil.findByToBy;
import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.FillFromAnnotationRules.fieldHasAnnotation;
import static com.google.common.base.Strings.isNullOrEmpty;
import static java.lang.String.format;

public class Dropdown<TEnum extends Enum> extends Selector<TEnum>
        implements IDropDown<TEnum>, ISetup {

    public void setup(Field field) {
        if (!fieldHasAnnotation(field, JDropdown.class, IDropDown.class))
            return;
        JDropdown j = field.getAnnotation(JDropdown.class);
        setupDropElement(this,  findByToBy(j.root()), findByToBy(j.value()),
            findByToBy(j.list()), findByToBy(j.expand()));
    }

    private static final String SELECT_ERROR =
        "Can't %s element in dropdown '%s'. Dropdown should have JDropdown annotation or locator to 'select' tag";
    private static final String TO_MUCH_ELEMENTS_FOUND_ERROR =
        "Found more than 1 <select> tag with locator '%s' for dropdown '%s'";
    @Override
    public void select(String name) {
        if (linked().isAny()) {
            assertLinked("list", "select");
            assertLinked("expander", "select");
            expand();
            ((ISelector)linked().get("list")).select(name);
        }
        else getSelectElement(format("select '%s'", name)).selectByVisibleText(name);
    }
    private void assertLinked(String name, String action) {
        if (!linked().has(name))
            throw exception(format("You must specify '%s' in Dropdown annotation in order to perform %s action", name, action));
    }
    @Override
    public void select(int index) {
        if (linked().isAny()){
            assertLinked("list", "select");
            assertLinked("expander", "select");
            expand();
            ((ISelector)linked().get("list")).select(index);
        }
        else getSelectElement(format("select '%s'", index)).selectByIndex(index);
    }
    @Override
    public String getText() {
        if (linked().isAny()) {
            assertLinked("value", "select");
            String result = "";
            Input value = (Input)linked().get("value");
            if (value.getLocator().toString().contains("select")) try {
                result = new Select(value.getWebElement()).getFirstSelectedOption().getText();
            } catch (Exception ignore) {}
            return !isNullOrEmpty(result) ? result : value.getText();
        }
        return getSelectElement("getSelected").getFirstSelectedOption().getText();

    }


    private Select getSelectElement(String action) {
        if (getLocator().toString().contains("select")) {
            List<WebElement> result = wait(super::getWebElements, r -> r.size() == 1);
            if (result == null)
                throw exception(TO_MUCH_ELEMENTS_FOUND_ERROR, getLocator(), this);
            return new Select(result.get(0));
        }
        throw exception(SELECT_ERROR, action, this);
    }
}
