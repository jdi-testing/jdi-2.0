package com.epam.jdi.uitests.web.selenium.elements.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JAction1;
import com.epam.jdi.tools.func.JAction2;
import com.epam.jdi.uitests.core.interfaces.complex.IMenu;
import com.epam.jdi.uitests.core.interfaces.complex.IMenu2D;
import com.epam.jdi.uitests.web.selenium.elements.base.Element;
import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.WebAnnotationsUtil;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JMenu;
import org.openqa.selenium.By;

import java.lang.reflect.Field;
import java.util.List;

import static com.epam.jdi.tools.LinqUtils.map;
import static com.epam.jdi.uitests.web.selenium.driver.WebDriverByUtils.fillByTemplate;
import static com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.FillFromAnnotationRules.fieldHasAnnotation;

/**
 * Menu2D complex element
 * @param <TEnum> selector
 */
public class Menu2D<TEnum extends Enum> extends Selector<TEnum> implements IMenu2D<TEnum> {
    /**
     * Gets separator
     * @return separator
     */
    public String getSeparator() { return  separator; }

    /**
     * Gets navigation action
     * @return navigation action
     */
    public JAction2<Integer, String> getNavigationAction() {
        return navigationAction;
    }

    /**
     * Gets select action
     * @return select action
     */
    public JAction1<String> getSelectAction() {
        return selectAction;
    }

    /**
     * Sets separator
     * @param separator separator
     * @return Menu2D
     */
    public IMenu2D<TEnum> setSeparator(String separator) {
        this.separator = separator;
        return this;
    }

    /**
     * Sets navigation action
     * @param navigationAction navigation action
     * @return Menu2D
     */
    public IMenu2D<TEnum> setNavigationAction(JAction2<Integer, String> navigationAction) {
        this.navigationAction = navigationAction;
        return this;
    }

    /**
     * Sets select action
     * @param selectAction select action
     * @return Menu2D
     */
    public IMenu2D<TEnum> setSelectAction(JAction1<String> selectAction) {
        this.selectAction = selectAction;
        return this;
    }

    private String separator = "\\|";
    private JAction2<Integer, String> navigationAction = this::select;
    private JAction1<String> selectAction = super::select;

    /**
     * Hovers and clicks elements with names
     * @param names names
     */
    public void hoverAndClick(String... names) {
        navigationAction = this::hover;
        selectAction = this::select;
        select(names);
    }

    /**
     * Selects element
     * @param index index
     * @param name name
     */
    protected void select(int index, String name) {
        Button btn = new Button().setLocator(
            fillByTemplate(levelLocators.get(index), name));
        btn.click();
    }

    /**
     * Hovers over element
     * @param index index
     * @param name name
     */
    protected void hover(int index, String name) {
        Element el = new Element().setLocator(
                fillByTemplate(levelLocators.get(index), name));
        el.hover();
    }
    private List<By> levelLocators;

    /**
     * Sets up element
     * @param field field
     */
    public void setup(Field field) {
        if (!fieldHasAnnotation(field, JMenu.class, IMenu.class))
            return;
        JMenu jMenu = field.getAnnotation(JMenu.class);
        levelLocators = map(jMenu.value(), WebAnnotationsUtil::findByToBy);
    }
}