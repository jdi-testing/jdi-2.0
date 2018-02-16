package com.epam.jdi.uitests.core.actions.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.*;
import com.epam.jdi.uitests.core.templates.base.TBaseElement;

import java.util.List;

import static com.epam.jdi.uitests.core.settings.JDISettings.asserter;

public abstract class ElementActions {
    // () -> element.isDisplayed() -> String
    public static JFunc1<Object, Boolean> displayed;
    // () -> element.isDisplayed() -> String
    public static JFunc1<Object, Boolean> enabled;

    // () -> element.getAttribute("class") -> String
    public static JFunc2<Object, String, String> getAttribute;
    // () -> element.getAtribute("class").value = "new"
    public static JAction3<Object, String, String> setAttribute;
    // () -> element.getElement() -> WebElement
    public static JFunc2<Object, Object[], Object> getElement;
    // () -> element.getElements() -> List<WebElement>
    public static JFunc2<Object, Object[], List<Object>> getElements;
    // () -> element.focus()
    public static JAction1<Object> focus;
    public static JFunc1<Object, String> printContext;

    // condition -> condition.execute()
    public static JFunc2<Object, JFunc<Boolean>, Boolean> wait =
        (o, func) -> tBase(o).timer().wait(func);
    public static JAction2<Object, Boolean> assertTrue =
        (o, func) -> asserter.isTrue(func);
    // condition -> condition.execute()
    public static JFunc3<Object, JFunc<Object>, JFunc1<Object, Boolean>, Object>
        getResultWithCondition =
        (o, func, condition) -> tBase(o).timer().getResultByCondition(func, condition);

    public static TBaseElement tBase(Object o) { return ((TBaseElement)o); }
    public static JFunc1<Object, Boolean> eDisplayed;
    public static JFunc1<Object, String> eGetText;
}
