package com.epam.jdi.uitests.web.selenium.elements.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.interfaces.ISetup;
import com.epam.jdi.uitests.core.interfaces.complex.ICheckList;
import com.epam.jdi.uitests.core.interfaces.complex.IDropList;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JCheckList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;

import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.WebAnnotationsUtil.findByToBy;
import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.FillFromAnnotationRules.fieldHasAnnotation;

/**
 * CheckList complex element
 * @param <TEnum> selector
 */
public class CheckList<TEnum extends Enum> extends MultiSelector<TEnum> implements ICheckList<TEnum>, ISetup {

    /**
     * Sets up element
     * @param field
     */
    @Override
    public void setup(Field field) {
        if (!fieldHasAnnotation(field, JCheckList.class, IDropList.class))
            return;
        JCheckList jCheckList = field.getAnnotation(JCheckList.class);
        By locator = findByToBy(jCheckList.value());
        By isSelected = findByToBy(jCheckList.isSelected());
        if (locator != null)
            setLocator(locator);
        if (isSelected != null)
            linked().add("isSelected", isSelected);
    }

    /**
     * Checks whether element is selected
     * @param el element
     * @return true if element is selected, false otherwise
     */
    @Override
    public boolean isSelected(WebElement el) {
        WebElement element = linked().has("isSelected")
                ? el.findElement((By)linked().get("isSelected"))
                : el;
        return super.isSelected(element);
    }
}