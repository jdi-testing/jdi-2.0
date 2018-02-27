package com.epam.jdi.uitests.core.actions.common;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JAction1;
import com.epam.jdi.tools.func.JAction2;
import com.epam.jdi.tools.func.JFunc1;

import static com.epam.jdi.tools.StringUtils.LINE_BREAK;
import static com.epam.jdi.uitests.core.actions.common.ClickActions.click;
import static com.epam.jdi.uitests.core.settings.JDISettings.exception;

public abstract class CheckboxActions {
    // () -> element.isChecked() -> Boolean
    public static JFunc1<Object, Boolean> isChecked;
    // () -> element.check()
    public static JAction1<Object> check = o -> {
        if (!isChecked.execute(o))
            click.execute(o);
        if (!isChecked.execute(o))
            throw exception("Can't check element. Verify locator for click or isCheckedAction");
    };
    // () -> element.uncheck()
    public static JAction1<Object> uncheck = o -> {
        if (isChecked.execute(o))
            click.execute(o);
        if (isChecked.execute(o))
            throw exception("Can't uncheck element. Verify locator for click or isCheckedAction");
    };
    public static JAction2<Object, String> select = (o, value) -> {
        switch (value.toLowerCase()) {
            case "true":
            case "1":
            case "check":
                check.execute(o);
                return;
            case "false":
            case "0":
            case "uncheck":
                uncheck.execute(o);
                return;
        }
        throw exception("Can't set value '%s' (%s)." + LINE_BREAK + "Supported values: 'true', '1', 'check', 'false', '0', 'uncheck'", value, o.toString());
    };
}
