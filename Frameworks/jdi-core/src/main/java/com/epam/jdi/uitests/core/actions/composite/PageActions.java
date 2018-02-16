package com.epam.jdi.uitests.core.actions.composite;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JAction1;
import com.epam.jdi.tools.func.JFunc1;

public class PageActions {
    // () -> driver.open()
    public static JAction1<Object> openPage;
    // () -> driver.checkOpened()
    public static JFunc1<Object, Boolean> isOpened;
}
