package com.epam.jdi.uitests.core.interfaces.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import java.lang.reflect.Field;

import static com.epam.jdi.tools.LinqUtils.first;
import static com.epam.jdi.tools.LinqUtils.foreach;
import static com.epam.jdi.tools.ReflectionUtils.*;
import static com.epam.jdi.tools.StringUtils.namesEqual;
import static com.epam.jdi.uitests.core.settings.JDISettings.exception;

/**
 * Interface for composite element
 */
public interface IComposite extends INamed {
    /**
     * @return Returns new entity of specified class
     */
    default <T> T asEntity(Class<T> entityClass) {
        try {
            T data = newEntity(entityClass);
            foreach(getFields(this, IHasValue.class), item -> {
                Field field = first(getFields(data, String.class), f ->
                        namesEqual(f.getName(), item.getName()));
                if (field == null)
                    return;
                try {
                    field.set(data, ((IHasValue) getValueField(item, this)).getValue());
                } catch (Exception ignore) {
                }
            });
            return data;
        } catch (Exception ex) {
            throw exception("Can't get entity from '" + getName() + "' for class: " + entityClass.getClass());
        }
    }
}