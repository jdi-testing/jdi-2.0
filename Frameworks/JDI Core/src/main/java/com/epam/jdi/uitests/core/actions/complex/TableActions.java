package com.epam.jdi.uitests.core.actions.complex;

import com.epam.jdi.tools.LinqUtils;
import com.epam.jdi.tools.func.JFunc1;
import com.epam.jdi.tools.func.JFunc2;
import com.epam.jdi.uitests.core.interfaces.base.IBaseElement;
import com.epam.jdi.uitests.core.interfaces.complex.tables.ICell;
import com.epam.jdi.uitests.core.interfaces.complex.tables.TableLine;
import com.epam.jdi.uitests.core.templates.base.ETable;

import java.lang.reflect.Field;
import java.util.List;

import static com.epam.jdi.tools.LinqUtils.filter;
import static com.epam.jdi.tools.ReflectionUtils.convertStringToType;
import static com.epam.jdi.tools.ReflectionUtils.newEntity;
import static com.epam.jdi.uitests.core.settings.JDISettings.exception;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.reflect.FieldUtils.writeField;

/**
 * Created by Roman_Iovlev on 11/14/2017.
 */
public class TableActions {
    public static JFunc2<Object, TableLine, Object> castToRow =
            (o, row) -> {
                Object newRow = newRow(((ETable)o).rowClass);
                List<Field> fields = filter(newRow.getClass().getFields(),
                        f -> f.getType().isAssignableFrom(IBaseElement.class));
                row.pairs.forEach(pair ->
                        setRowField(newRow, fields, pair.key, pair.value));
                return newRow;
            };
    public static JFunc2<Object, TableLine, Object> rowToEntity =
            (o, row) -> {
                Object entity = newEntity(((ETable)o).dataClass);
                if (row == null)
                    return entity;

                List<Field> fields = asList(entity.getClass().getFields());
                row.pairs.forEach(entry
                        -> setEntityField(entity, fields, entry.key, entry.value.getText()));
                return entity;
            };
    private static void setEntityField(Object entity, List<Field> fields, String fieldName, String value)
    {
        setField(entity, fields, fieldName, field -> convertStringToType(value, field));
    }
    private static Object newRow(Class<?> rowClass){
        if (rowClass == null)
            throw exception("Row class was not specified");
        try {
            return rowClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw exception("Can't Instantiate row: " + rowClass.getName());
        }
    }
    private static void setField(Object row, List<Field> fields, String fieldName,
                          JFunc1<Field, Object> valueFunc)
    {
        Field field = LinqUtils.first(fields,
                f -> f.getName().equalsIgnoreCase(fieldName));
        if (field == null) return;
        try {
            writeField(field, row, valueFunc.execute(field), true);
        } catch (IllegalAccessException e) {
            throw exception("Can't write field with name: " + fieldName);
        }
    }
    private static void setRowField(Object row, List<Field> fields, String fieldName, ICell cell)
    {
        setField(row, fields, fieldName, field -> {
            Class clazz = field.getType();
            if (clazz == null) return null;
            IBaseElement value;
            try {
                value = cell.getAs(clazz);
            } catch (Exception e) {
                throw exception("Can't Instantiate row element: " + fieldName);
            }
            return value;
        });
    }

}
