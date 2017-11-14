package com.epam.jdi.uitests.core.interfaces.complex;

import com.epam.jdi.tools.LinqUtils;
import com.epam.jdi.uitests.core.interfaces.base.IBaseElement;
import com.epam.jdi.uitests.core.interfaces.common.IText;

import java.util.List;

/**
 * Created by Roman_Iovlev on 10/24/2017.
 */
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
