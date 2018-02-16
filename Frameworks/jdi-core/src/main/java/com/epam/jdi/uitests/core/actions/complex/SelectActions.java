package com.epam.jdi.uitests.core.actions.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
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
