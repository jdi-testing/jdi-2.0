package com.epam.jdi.uitests.web.selenium.elements.complex.table;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.interfaces.base.IBaseElement;
import com.epam.jdi.uitests.core.interfaces.complex.tables.ICell;
import com.epam.jdi.uitests.core.interfaces.complex.tables.ITable;
import com.epam.jdi.uitests.core.interfaces.complex.tables.NameNum;
import com.epam.jdi.uitests.web.selenium.elements.base.Element;
import com.epam.jdi.uitests.web.selenium.elements.common.Button;

import static com.epam.jdi.uitests.core.initialization.MapInterfaceToElement.getClassFromInterface;
import static com.epam.jdi.uitests.core.settings.JDISettings.exception;

/**
 * Cell complex element
 */
public class Cell extends Button implements ICell {
    private NameNum column;
    private NameNum row;

    /**
     * Constucts Cell
     * @param x column
     * @param y row
     * @param table table
     */
    public Cell(int x, int y, ITable table) {
        setParent(table);
        column = new NameNum().set(n -> {n.num = x;n.name = table.columns().headers().get(x);});
        row = new NameNum().set(n -> {n.num = y;n.name = table.rows().headers().get(y);});
    }

    /**
     * Gets Cell as type
     * @param clazz class
     * @param <T> type
     * @return type
     */
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

    /**
     * Gets column
     * @return column
     */
    public NameNum column() {
        return column;
    }

    /**
     * Gets row
     * @return row
     */
    public NameNum row() {
        return row;
    }
}
