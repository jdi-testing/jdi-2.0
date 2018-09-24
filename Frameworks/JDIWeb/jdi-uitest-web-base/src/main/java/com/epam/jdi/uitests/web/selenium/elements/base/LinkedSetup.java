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

/**
 * Sets up linked elements
 */
public class LinkedSetup {

    /**
     * Sets up DropElement
     * @param el element
     * @param root root
     * @param value value
     * @param list list
     * @param expand expand
     */
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

    /**
     * Sets up DropList
     * @param el element
     * @param root root
     * @param value value
     * @param list list
     * @param expand expand
     * @param isSelected isSelected
     */
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
        if (list != null) {
            CheckList checklist = new CheckList().setLocator(list).setParent(el);
            if (isSelected != null)
                checklist.linked().add("isSelected", isSelected);
            el.linked().add("list", checklist);
        }
        if (expand != null) {
            Button eExpand = new Button().setLocator(expand).setParent(el);
            el.linked().add("expander", eExpand);
            if (value == null)
                el.linked().add("value", eExpand);
        }
    }

    /**
     * Sets up linked element
     * @param parent parent
     * @param locator locator
     * @param name name
     */
    public static void setUpLinked(BaseElement parent, FindBy locator, String name) {
        setUpLinked(parent, locator, name, by -> new Button().setLocator(by));
    }

    /**
     * Sets up linked element
     * @param parent parent
     * @param locator locator
     * @param name name
     * @param newElement newElement
     */
    public static void setUpLinked(BaseElement parent, FindBy locator, String name, JFunc1<By, BaseElement> newElement) {
        By by = findByToBy(locator);
        if (by != null) {
            BaseElement el = newElement.execute(by).setParent(parent);
            parent.linked().add(name, el);
        }
    }
}
