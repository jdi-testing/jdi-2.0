package com.epam.jdi.uitests.core.interfaces.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.base.IBaseElement;
import com.epam.jdi.uitests.core.interfaces.base.IClickable;
import com.epam.jdi.uitests.core.interfaces.common.IText;

import java.util.List;

/**
 * Dropdown element using three locators: list, expander, value
 */
public interface IDropDown<TEnum extends Enum> extends ISelector<TEnum>, IText, IClickable {
    default boolean isExpanded() {
        assertLinked("list", "expand");
        return ((IBaseElement)linked().get("list")).displayedNow();
    }
    /**
     * Expanding DropDown
     */
    @JDIAction
    default void expand() {
        if (!isExpanded())
            click();
    }
    /**
     * Closing DropDown
     */
    @JDIAction
    default void close() {
        if (isExpanded())
            click();
    }
    @Override
    default String getValue() { return getText(); }

    @Override
    default void select(String name) {
        expand();
        ((ISelector)linked().get("list")).select(name);
    }

    @Override
    default void select(int index) {
        expand();
        ((ISelector)linked().get("list")).select(index);
    }

    @Override
    default boolean displayed(String name) {
        return isExpanded() &&
        ((ISelector)linked().get("list")).getLabels().contains(name);
    }

    @Override
    default String getSelected() {
        return getText();
    }

    /**
     * Waits while Element becomes visible
     */
    @Override
    default boolean displayed() {
        return ((IBaseElement)linked().get("value")).disabled();
    }

    /**
     * Waits while Element becomes invisible
     */
    @Override
    default boolean vanished() {
        return ((IBaseElement)linked().get("value")).vanished();
    }

    @Override
    default List<String> getOptions() {
        expand();
        return ((ISelector)linked().get("list")).getOptions();
    }
    @Override
    default void click() {
        ((IClickable)linked().get("expander")).click();
    }
    @Override
    default String getText() {
        return ((IText) linked().get("value")).getText();
    }

}