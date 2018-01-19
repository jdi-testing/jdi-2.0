package com.epam.jdi.uitests.web.selenium.elements.complex.table;

import com.epam.jdi.uitests.core.interfaces.base.IBaseElement;
import com.epam.jdi.uitests.core.interfaces.complex.tables.ICell;
import com.epam.jdi.uitests.core.interfaces.complex.tables.ITable;
import com.epam.jdi.uitests.core.interfaces.complex.tables.NameNum;
import com.epam.jdi.uitests.web.selenium.elements.base.Element;
import com.epam.jdi.uitests.web.selenium.elements.common.Button;

import static com.epam.jdi.uitests.core.initialization.MapInterfaceToElement.getClassFromInterface;
import static com.epam.jdi.uitests.core.settings.JDISettings.exception;

/**
 * Created by Roman_Iovlev on 11/14/2017.
 */
public class Cell extends Button implements ICell {
    private NameNum column;
    private NameNum row;
    public Cell(int x, int y, ITable table) {
        setParent(table);
        column = new NameNum().set(n -> {n.num = x;n.name = table.columns().headers().get(x);});
        row = new NameNum().set(n -> {n.num = y;n.name = table.rows().headers().get(y);});
    }
    public <T extends IBaseElement> T getAs(Class<T> clazz) {
        try {
            T instance = (clazz.isInterface()
                    ? (Class<T>)getClassFromInterface(clazz, clazz.getSimpleName())
                    : clazz).newInstance();
            Element el = (Element)instance;
            el.setWebElement(getWebElement()).setParent(getParent());
            return instance;
        } catch (Exception ex) { throw exception("Can't instantiate cell with class " + clazz); }
    }

    public NameNum column() {
        return column;
    }

    public NameNum row() {
        return row;
    }
}
