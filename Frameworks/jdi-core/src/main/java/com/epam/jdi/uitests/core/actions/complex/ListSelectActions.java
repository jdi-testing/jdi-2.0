package com.epam.jdi.uitests.core.actions.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JAction2;
import com.epam.jdi.tools.func.JFunc2;
import com.epam.jdi.uitests.core.interfaces.base.IClickable;

import static com.epam.jdi.tools.LinqUtils.foreach;

public abstract class ListSelectActions {
    // () -> element.select("a", "b")
    public static JAction2<Object, String[]> select =
        (o, names) -> foreach(names, name -> SelectActions.select.execute(o, name));
    // () -> element.select(1, 3)
    public static JAction2<Object, Integer[]> selectByIndex =
        (o, nums) -> foreach(nums, num -> SelectActions.selectByIndex.execute(o, num));

    // () -> getClickable(name) -> Button.click();
    public static JFunc2<Object, String, IClickable> getClickable;
    // () -> getClickable(num) -> Button.click();
    public static JFunc2<Object, Integer, IClickable> getClickableByIndex;
}
