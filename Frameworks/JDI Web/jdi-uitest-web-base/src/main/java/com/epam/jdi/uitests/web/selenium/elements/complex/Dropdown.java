package com.epam.jdi.uitests.web.selenium.elements.complex;

import com.epam.jdi.uitests.core.interfaces.ISetup;
import com.epam.jdi.uitests.core.interfaces.complex.IDropDown;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.lang.reflect.Field;
import java.util.List;

import static com.epam.jdi.uitests.core.settings.JDISettings.exception;
import static com.epam.jdi.uitests.web.selenium.elements.base.LinkedSetup.setupDropElement;
import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.WebAnnotationsUtil.findByToBy;
import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.FillFromAnnotationRules.fieldHasAnnotation;
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

    private static final String SELECT_ERROR = "Can't %s element in dropdown '%s'";
    private static final String TOMUCH_ELEMENTS_FOUND_ERROR = "Found more than 1 <select> tag with locator '%s' for dropdown '%s'";
    @Override
    public void select(String name) {
        if (linked().isAny())
            super.select(name);
        else getSelectElement(format("select '%s'", name)).selectByValue(name);
    }
    @Override
    public void select(int index) {
        if (linked().isAny())
            super.select(index);
        else getSelectElement(format("select '%s'", index)).selectByIndex(index);
    }
    @Override
    public String getSelected() {
        if (linked().isAny())
            return getText();
        return getSelectElement("getSelected").getFirstSelectedOption().getText();
    }

    private Select getSelectElement(String action) {
        if (getLocator().toString().contains("select")) {
            List<WebElement> result = wait(super::getWebElements, r -> r.size() == 0);
            if (result != null)
                return new Select(result.get(0));
            throw exception(TOMUCH_ELEMENTS_FOUND_ERROR, getLocator(), this);
        } throw exception(SELECT_ERROR, action, this);
    }
}