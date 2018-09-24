package com.epam.jdi.uitests.web.selenium.elements.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.interfaces.ISetup;
import com.epam.jdi.uitests.core.interfaces.complex.IDropDown;
import com.epam.jdi.uitests.core.interfaces.complex.IPagination;
import com.epam.jdi.uitests.web.selenium.elements.base.Element;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JPagination;
import org.openqa.selenium.By;

import java.lang.reflect.Field;

import static com.epam.jdi.uitests.web.selenium.elements.base.LinkedSetup.setUpLinked;
import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.WebAnnotationsUtil.findByToBy;
import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.FillFromAnnotationRules.fieldHasAnnotation;

/**
 * Pagination complex element
 */
public class Pagination extends Element implements IPagination, ISetup {

    /**
     * Sets up element
     * @param field field
     */
    public void setup(Field field) {
        if (!fieldHasAnnotation(field, JPagination.class, IDropDown.class))
            return;
        JPagination jDropdown = field.getAnnotation(JPagination.class);
        By root = findByToBy(jDropdown.root());
        if (root != null) setLocator(root);
        setUpLinked(this, jDropdown.first(), "first");
        setUpLinked(this, jDropdown.last(), "last");
        setUpLinked(this, jDropdown.next(), "next");
        setUpLinked(this, jDropdown.prev(), "prev");
        setUpLinked(this, jDropdown.page(), "page");
    }
}
