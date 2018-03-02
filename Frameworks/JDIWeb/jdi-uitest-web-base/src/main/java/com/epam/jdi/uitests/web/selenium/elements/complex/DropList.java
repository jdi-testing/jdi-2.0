package com.epam.jdi.uitests.web.selenium.elements.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.interfaces.ISetup;
import com.epam.jdi.uitests.core.interfaces.complex.IDropList;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropList;

import java.lang.reflect.Field;

import static com.epam.jdi.uitests.web.selenium.elements.base.LinkedSetup.setupDropList;
import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.WebAnnotationsUtil.findByToBy;
import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.FillFromAnnotationRules.fieldHasAnnotation;

public class DropList<TEnum extends Enum> extends Dropdown<TEnum>
        implements IDropList<TEnum>, ISetup {

    @Override
    public void setup(Field field) {;
        if (!fieldHasAnnotation(field, JDropList.class, IDropList.class))
            return;
        JDropList jDroplist = field.getAnnotation(JDropList.class);
        setupDropList(this, findByToBy(jDroplist.root()),
                findByToBy(jDroplist.value()),
                findByToBy(jDroplist.list()),
                findByToBy(jDroplist.expand()));
    }

    private String separator = ", ";
    public String getSeparator() { return separator; }
    public DropList<TEnum> setValuesSeparator(String separator) {
        this.separator = separator;
        return this;
    }
}