package com.epam.jdi.uitests.core.actions.complex;

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

import com.epam.jdi.tools.func.JAction2;
import com.epam.jdi.tools.func.JFunc1;
import com.epam.jdi.tools.func.JFunc2;
import com.epam.jdi.tools.func.JFunc4;
import com.epam.jdi.uitests.core.interfaces.base.IClickable;
import com.epam.jdi.uitests.core.interfaces.complex.tables.ICell;
import com.epam.jdi.uitests.core.interfaces.complex.tables.ITable;

import java.util.List;

import static com.epam.jdi.uitests.core.actions.complex.ListSelectActions.getClickable;
import static com.epam.jdi.uitests.core.actions.complex.ListSelectActions.getClickableByIndex;

public abstract class SelectActions {
    // () -> element.isSelected("a") -> true
    public static JFunc2<Object, String, Boolean> isSelected;
    // () -> element.isSelected(3) -> true
    public static JFunc2<Object, Integer, Boolean> isSelectedByIndex;
    // () -> element.select("b")
    public static JAction2<Object, String> select = (o, name) -> {
        IClickable cl = getClickable.execute(o, name);
        if (cl != null)
            cl.click();
    };
    // () -> element.select(2)
    public static JAction2<Object, Integer> selectByIndex = (o, num) -> {
        IClickable cl = getClickableByIndex.execute(o, num);
        if (cl != null)
            cl.click();
    };
    // () -> element.getSelected() -> "c"
    public static JFunc1<Object, String> getSelected;
    // () -> element.getSelected() -> 1
    public static JFunc1<Object, Integer> getSelectedIndex;
    // () -> element.getOptions() -> {"a", "c", "b"}
    public static JFunc1<Object, List<String>> getOptions;
    public static JFunc4<ITable, Object, Integer, Integer, ICell> toCell;
}
