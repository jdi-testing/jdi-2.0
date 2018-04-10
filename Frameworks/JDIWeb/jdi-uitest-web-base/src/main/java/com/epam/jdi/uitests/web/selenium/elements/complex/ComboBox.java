package com.epam.jdi.uitests.web.selenium.elements.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.interfaces.common.IText;
import com.epam.jdi.uitests.core.interfaces.common.ITextField;
import com.epam.jdi.uitests.core.interfaces.complex.IComboBox;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JComboBox;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;

import static com.epam.jdi.uitests.web.selenium.elements.base.LinkedSetup.setupDropElement;
import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.WebAnnotationsUtil.findByToBy;
import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.FillFromAnnotationRules.fieldHasAnnotation;

public class ComboBox<TEnum extends Enum> extends Dropdown<TEnum> implements IComboBox<TEnum> {
    @Override
    public void setup(Field field) {
        if (!fieldHasAnnotation(field, JComboBox.class, IComboBox.class))
            return;
        JComboBox j = field.getAnnotation(JComboBox.class);
        setupDropElement(this, findByToBy(j.root()), findByToBy(j.value()),
            findByToBy(j.list()), findByToBy(j.expand()));
    }
    @Override
    public void input(CharSequence text) {
        if (linked().has("value"))
            ((ITextField)linked().get("value")).input(text);
        else {
            WebElement el = engine().getWebElement();
            el.sendKeys(text);
        }
    }
    @Override
    public void newInput(CharSequence text) {
        if (linked().has("value"))
            ((ITextField)linked().get("value")).newInput(text);
        else {
            WebElement el = engine().getWebElement();
            el.clear();
            el.sendKeys(text);
        }
    }
    @Override
    public String getText() {
        return linked().has("value")
            ? ((IText) linked().get("value")).getText()
            : engine().getWebElement().getAttribute("value");
    }
}