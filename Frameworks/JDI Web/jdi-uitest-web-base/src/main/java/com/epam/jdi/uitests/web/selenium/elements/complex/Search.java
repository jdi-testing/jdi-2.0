package com.epam.jdi.uitests.web.selenium.elements.complex;

import com.epam.jdi.uitests.core.interfaces.ISetup;
import com.epam.jdi.uitests.core.interfaces.complex.ISearch;
import com.epam.jdi.uitests.web.selenium.elements.base.Element;
import com.epam.jdi.uitests.web.selenium.elements.base.LinkedSetup;
import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.common.Input;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JSearch;
import org.openqa.selenium.By;

import java.lang.reflect.Field;

import static com.epam.jdi.uitests.web.selenium.elements.base.LinkedSetup.setUpLinked;
import static com.epam.jdi.uitests.web.selenium.elements.base.LinkedSetup.setupDropElement;
import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.WebAnnotationsUtil.findByToBy;
import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.FillFromAnnotationRules.fieldHasAnnotation;

/**
 * Created by Roman_Iovlev on 11/14/2017.
 */
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
