package com.epam.jdi.uitests.core.interfaces.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.LinqUtils;
import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.base.IBaseElement;
import com.epam.jdi.uitests.core.interfaces.common.IText;

import java.util.List;

import static com.epam.jdi.tools.LinqUtils.map;
import static com.epam.jdi.uitests.core.actions.base.ElementActions.getElements;
import static com.epam.jdi.uitests.core.logger.LogLevels.DEBUG;

public interface ITextList extends IList<IText>, IBaseElement {

    /**
     * @return list of texts from all elements
     */
    @JDIAction
    default List<String> asText() {
        return LinqUtils.map(getAll().values(), IText::getText);
    }

    /**
     * @return text of the first element of the list
     */
    @JDIAction
    default String firstText() {
        return first().getText();
    }

    /**
     * @return text of the last element of the list
     */
    @JDIAction
    default String lastText() {
        return last().getText();
    }

    /**
     * @param index index of the element
     * @return text of the element specified by index
     */
    @JDIAction
    default String getText(int index) {
        return get(index).getText();
    }
}
