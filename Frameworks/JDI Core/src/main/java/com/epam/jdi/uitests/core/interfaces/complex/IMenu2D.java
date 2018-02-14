package com.epam.jdi.uitests.core.interfaces.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JAction1;
import com.epam.jdi.tools.func.JAction2;

import static com.epam.jdi.tools.EnumUtils.getEnumValue;
import static com.epam.jdi.tools.LinqUtils.last;
import static com.epam.jdi.uitests.core.settings.JDISettings.exception;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public interface IMenu2D<TEnum extends Enum> extends IMenu<TEnum> {
    String NULL_OR_EMPTY_MSG = "Can't perform select menu '%s'. Value is null or empty";

    String getSeparator();
    JAction2<Integer, String> getNavigationAction();
    JAction1<String> getSelectAction();
    IMenu2D<TEnum> setSeparator(String separator);
    IMenu2D<TEnum> setNavigationAction(JAction2<Integer, String> navigationAction);
    IMenu2D<TEnum> setSelectAction(JAction1<String> selectAction);

    @Override
    default void select(String names) {
        if (isEmpty(names))
            throw exception(NULL_OR_EMPTY_MSG, toString());
        select(names.split(getSeparator()));
    }
    default void select(String... names) {
        if (names == null || names.length == 0)
            throw exception(NULL_OR_EMPTY_MSG, toString());
        if (names.length == 1)
            getSelectAction().execute(names[0]);
        for(int i = 0; i<names.length-1;i++)
            getNavigationAction().execute(i, names[i]);
        getSelectAction().execute(last(names));
    }

    @Override
    default void select(TEnum name) {
        select(getEnumValue(name));
    }
    default void select(TEnum... names) {
        if (names == null || names.length == 0)
            throw exception(NULL_OR_EMPTY_MSG, toString());
        for(TEnum name : names)
            select(getEnumValue(name));
    }

}
