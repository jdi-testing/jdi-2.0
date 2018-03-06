package com.epam.jdi.uitests.web.selenium.elements.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JFunc1;
import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.common.Input;
import com.epam.jdi.uitests.web.selenium.elements.complex.CheckList;
import com.epam.jdi.uitests.web.selenium.elements.complex.Selector;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import org.openqa.selenium.By;

import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.WebAnnotationsUtil.findByToBy;

public class LinkedSetup {

    public static void setupDropElement(BaseElement el, By root, By value, By list, By expand) {
        if (root != null)
            el.setLocator(root);
        if (value != null) {
            Input eValue = new Input().setLocator(value).setParent(el);
            el.linked().add("value", eValue);
            if (expand == null)
                el.linked().add("expander", eValue);
        }
        if (list != null)
            el.linked().add("list", new Selector()
                    .setLocator(list).setParent(el));
        if (expand != null) {
            Button eExpand = new Button().setLocator(expand).setParent(el);
            el.linked().add("expander", eExpand);
            if (value == null)
                el.linked().add("value", eExpand);
        }
    }

    public static void setupDropList(BaseElement el, By root, By value,
             By list, By expand, By isSelected) {
        if (root != null)
            el.setLocator(root);
        if (value != null) {
            Input eValue = new Input().setLocator(value).setParent(el);
            el.linked().add("value", eValue);
            if (expand == null)
                el.linked().add("expander", eValue);
        }
        if (list != null)
            el.linked().add("list", new CheckList()
                    .setLocator(list).setParent(el));
        if (expand != null) {
            Button eExpand = new Button().setLocator(expand).setParent(el);
            el.linked().add("expander", eExpand);
            if (value == null)
                el.linked().add("value", eExpand);
        }
        if (isSelected != null)
            el.linked().add("isSelected", isSelected);
    }
    public static void setUpLinked(BaseElement parent, FindBy locator, String name) {
        setUpLinked(parent, locator, name, by -> new Button().setLocator(by));
    }
    public static void setUpLinked(BaseElement parent, FindBy locator, String name, JFunc1<By, BaseElement> newElement) {
        By by = findByToBy(locator);
        if (by != null) {
            BaseElement el = newElement.execute(by).setParent(parent);
            parent.linked().add(name, el);
        }
    }
}
