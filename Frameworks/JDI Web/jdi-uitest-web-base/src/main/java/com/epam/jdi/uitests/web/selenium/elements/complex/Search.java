package com.epam.jdi.uitests.web.selenium.elements.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.interfaces.complex.ISearch;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JSearch;

import java.lang.reflect.Field;

import static com.epam.jdi.uitests.web.selenium.elements.base.LinkedSetup.setupDropElement;
import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.WebAnnotationsUtil.findByToBy;
import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.FillFromAnnotationRules.fieldHasAnnotation;

public class Search extends ComboBox implements ISearch {
    @Override
    public void setup(Field field) {
        if (!fieldHasAnnotation(field, JSearch.class, ISearch.class))
            return;
        JSearch j = field.getAnnotation(JSearch.class);
        setupDropElement(this, findByToBy(j.root()), findByToBy(j.input()),
            findByToBy(j.suggestions()), findByToBy(j.searchButton()));
    }
}
