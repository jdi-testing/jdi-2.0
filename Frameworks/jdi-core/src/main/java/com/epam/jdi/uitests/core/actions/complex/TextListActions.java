package com.epam.jdi.uitests.core.actions.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JFunc1;
import com.epam.jdi.tools.func.JFunc2;

import java.util.List;

public class TextListActions {
    // () -> element.getElement() -> WebElement
    public static JFunc2<Object, String, String> getTextByName;
    // () -> element.getElement() -> WebElement
    public static JFunc2<Object, Integer, String> getTextByIndex;
    // () -> element.getElement() -> WebElement
    public static JFunc1<Object, List<String>> getAllText;
}
