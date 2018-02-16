package com.epam.jdi.uitests.web.selenium.elements.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.interfaces.complex.IComboBox;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JComboBox;

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

}