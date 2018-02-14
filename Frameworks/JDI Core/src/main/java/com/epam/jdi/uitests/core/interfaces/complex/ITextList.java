package com.epam.jdi.uitests.core.interfaces.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.LinqUtils;
import com.epam.jdi.uitests.core.interfaces.base.IBaseElement;
import com.epam.jdi.uitests.core.interfaces.common.IText;

import java.util.List;

public interface ITextList extends IList<IText>, IBaseElement {
    default List<String> asText() {
        return LinqUtils.map(getAll().values(), IText::getText);
    }
    default String firstText() {
        return first().getText();
    }
    default String lastText() {
        return last().getText();
    }
    default String getText(int index) {
        return get(index).getText();
    }
}
