package com.epam.jdi.uitests.web.selenium.elements.complex;

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

public class Menu2D<TEnum extends Enum> extends Selector<TEnum> implements IMenu2D<TEnum> {
    public String getSeparator() { return separator; }
    public JAction2<Integer, String> getNavigationAction() {
        return navigationAction;
    }
    public JAction1<String> getSelectAction() {
        return selectAction;
    }
    public IMenu2D<TEnum> setSeparator(String separator) {
        this.separator = separator;
        return this;
    }
    public IMenu2D<TEnum> setNavigationAction(JAction2<Integer, String> navigationAction) {
        this.navigationAction = navigationAction;
        return this;
    }
    public IMenu2D<TEnum> setSelectAction(JAction1<String> selectAction) {
        this.selectAction = selectAction;
        return this;
    }

    private String separator = "\\|";
    private JAction2<Integer, String> navigationAction = this::select;
    private JAction1<String> selectAction = super::select;

    public void hoverAndClick(String... names) {
        navigationAction = this::hover;
        selectAction = this::select;
        select(names);
    }
    protected void select(int index, String name) {
        Button btn = new Button().setLocator(
            fillByTemplate(levelLocators.get(index), name));
        btn.click();
    }
    protected void hover(int index, String name) {
        Element el = new Element().setLocator(
                fillByTemplate(levelLocators.get(index), name));
        el.hover();
    }
    private List<By> levelLocators;

    public void setup(Field field) {
        if (!fieldHasAnnotation(field, JMenu.class, IMenu.class))
            return;
        JMenu jMenu = field.getAnnotation(JMenu.class);
        levelLocators = map(jMenu.value(), WebAnnotationsUtil::findByToBy);
    }
}