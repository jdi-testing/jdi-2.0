package com.epam.jdi.uitests.web.selenium.elements.complex;

import com.epam.jdi.tools.map.MapArray;
import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.common.IText;
import com.epam.jdi.uitests.core.interfaces.complex.ITextList;
import com.epam.jdi.uitests.web.selenium.elements.common.Text;

import java.util.List;

/**
 * TextList complex element
 */
public class TextList extends Elements<Text> implements ITextList {
    private MapArray<String, IText> elements;
    private List<IText> texts;

    /**
     * Constructs Elements with type
     *
     * @param classType type
     */
    public TextList(Class<Text> classType) {
        super(classType);
    }

    @JDIAction
    @Override
    public MapArray<String, IText> getAll() {
        MapArray<String, Text> map = super.getAll();
        MapArray<String, IText> result = new MapArray<String, IText>();

        return result;
    }

    @JDIAction
    @Override
    public List<IText> values() {
        return getAll().values();
    }
}
