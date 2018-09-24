package com.epam.jdi.uitests.core.actions.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.interfaces.common.IButton;
import com.epam.jdi.uitests.core.interfaces.common.ILabel;
import com.epam.jdi.uitests.core.interfaces.common.IText;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import static com.epam.jdi.tools.LinqUtils.first;
import static com.epam.jdi.tools.LinqUtils.select;
import static com.epam.jdi.tools.ReflectionUtils.getFields;
import static com.epam.jdi.tools.ReflectionUtils.getValueField;
import static com.epam.jdi.tools.StringUtils.namesEqual;
import static com.epam.jdi.uitests.core.settings.JDISettings.exception;

/**
 * Represents utility methods for ui-elements (buttons, labels...)
 */
public class UIUtils {
    /**
     * Retrieves button from {@link Object} by its name
     * @param obj           object on page to retrieve button from (form, section)
     * @param buttonName    name of button for retrieval
     * @return IButton      button object with IButton interface
     */
    public static IButton getButton(Object obj, String buttonName) {
        List<Field> fields = getFields(obj, IButton.class);
        switch (fields.size()) {
            case 0:
                throw exception("Can't find any buttons on form '%s.", obj);
            case 1:
                return (IButton) getValueField(fields.get(0), obj);
            default:
                Collection<IButton> buttons = select(fields, f -> (IButton) getValueField(f, obj));
                IButton button = first(buttons, b -> namesEqual(toButton(b.getName()), toButton(buttonName)));
                if (button == null)
                    throw exception("Can't find button '%s' for Element '%s'", buttonName, obj);
                return button;
        }
    }

    /**
     * Converts the string to buttonName string for further usage
     * @param buttonName    string to convert (for example, 'OK')
     * @return String       converted string (for example, 'okbutton')
     */
    private static String toButton(String buttonName) {
        return buttonName.toLowerCase().contains("button") ? buttonName : buttonName + "button";
    }

    /**
     * Retrieves button from {@link Object} by its function
     * @param obj       object on page to retrieve button from (form, section)
     * @param funcName  name of function for retrieved button
     * @return IButton  button object with IButton interface
     */
    public static IButton getButton(Object obj, Functions funcName) {
        List<Field> fields = getFields(obj, IButton.class);
        if (fields.size() == 1)
            return (IButton) getValueField(fields.get(0), obj);
        Collection<IButton> buttons = select(fields, f -> (IButton) getValueField(f, obj));
        String name = funcName.name + "button";
        IButton button = first(buttons, b -> {
            String bName = b.getName().toLowerCase();
            bName = bName.toLowerCase().contains("button") ? bName : bName + "button";
            return namesEqual(bName, name);
        });
        if (button == null)
            throw exception("Can't find button '%s' for Element '%s'", name, obj);
        return button;
    }

    /**
     * Retrieves text from {@link Object}
     * @param obj       object on page to retrieve text from (form, section)
     * @return IText    text with IText interface
     */
    public static IText getTextElement(Object obj) {
        Field textField = first(obj.getClass().getDeclaredFields(),  f ->
            f.getType() == IText.class || f.getType() == ILabel.class);
        if (textField == null)
            throw exception("Can't find Text Element '%s'", obj);
        return (IText) getValueField(textField, obj);
    }
}