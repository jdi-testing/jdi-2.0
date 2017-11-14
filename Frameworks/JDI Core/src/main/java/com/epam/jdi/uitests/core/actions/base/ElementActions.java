package com.epam.jdi.uitests.core.actions.base;

/* The MIT License
 *
 * Copyright 2004-2017 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:

 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

 */

/**
 * Created by Roman Iovlev on 10.03.2017
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
