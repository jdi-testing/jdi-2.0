package com.epam.jdi.uitests.core.actions.common;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JAction1;
import com.epam.jdi.tools.func.JAction2;

import static com.epam.jdi.uitests.core.actions.common.ClickActions.click;

public abstract class TextFieldActions {
    // text -> element.sendKeys(text)
    public static JAction2<Object, CharSequence> input;
    // text -> element.clear()
    public static JAction1<Object> clear;
    // text -> element.clear()
    public static JAction1<Object> focus = click;

    // text -> element.addNewLine(text)
    public static JAction2<Object, String> addNewLine =
        (o, text) -> input.execute(o, "\n" + text);
}
