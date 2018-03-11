package com.epam.jdi.uitests.web.selenium.elements.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.ISetup;
import com.epam.jdi.uitests.core.interfaces.complex.IDropList;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.util.List;

import static com.epam.jdi.uitests.web.selenium.elements.base.LinkedSetup.setupDropList;
import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.WebAnnotationsUtil.findByToBy;
import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.FillFromAnnotationRules.fieldHasAnnotation;

public class DropList<TEnum extends Enum> extends Dropdown<TEnum>
        implements IDropList<TEnum>, ISetup {

    @Override
    public void setup(Field field) {
        if (!fieldHasAnnotation(field, JDropList.class, IDropList.class))
            return;
        JDropList jDropList = field.getAnnotation(JDropList.class);
        setupDropList(this, findByToBy(jDropList.root()),
                findByToBy(jDropList.value()),
                findByToBy(jDropList.list()),
                findByToBy(jDropList.expand()),
                findByToBy(jDropList.isselected()));
    }

    private String separator = ", ";
    public String getSeparator() { return separator; }
    public DropList<TEnum> setValuesSeparator(String separator) {
        this.separator = separator;
        return this;
    }
    @Override
    public boolean isSelected(WebElement el) {
        WebElement element = linked().has("isSelected")
            ? el.findElement((By)linked().get("isSelected"))
            : el;
        return super.isSelected(element);
    }
    @JDIAction
    public void check(String... names) {
        expand();
        assertLinked("list", "check");
        ((CheckList)linked().get("list")).check(names);
    }

    @JDIAction
    public void check(Integer... indexes) {
        expand();
        assertLinked("list", "check");
        ((CheckList)linked().get("list")).check(indexes);
    }

    @JDIAction @Override
    public void uncheck(String... names) {
        expand();
        assertLinked("list", "check");
        ((CheckList)linked().get("list")).uncheck(names);
    }

    @JDIAction @Override
    public void uncheck(Integer... indexes) {
        expand();
        assertLinked("list", "check");
        ((CheckList)linked().get("list")).uncheck(indexes);
    }

    /**
     * @return Get names of checked options
     */
    @JDIAction @Override
    public List<String> areSelected() {
        expand();
        assertLinked("list", "check");
        return ((CheckList)linked().get("list")).areSelected();
    }
    /**
     * @return Get names of unchecked options
     */
    @JDIAction @Override
    public List<String> areDeselected() {
        expand();
        assertLinked("list", "check");
        return ((CheckList)linked().get("list")).areDeselected();
    }

}