package com.epam.jdi.uitests.core.templates.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.LinqUtils;
import com.epam.jdi.tools.Timer;
import com.epam.jdi.tools.logger.LogLevels;
import com.epam.jdi.uitests.core.interfaces.base.IBaseElement;
import com.epam.jdi.uitests.core.interfaces.base.IEngine;
import com.epam.jdi.uitests.core.interfaces.base.IHasValue;
import com.epam.jdi.uitests.core.settings.JDISettings;

import java.lang.reflect.Field;

import static com.epam.jdi.tools.LinqUtils.foreach;
import static com.epam.jdi.tools.ReflectionUtils.*;
import static com.epam.jdi.tools.StringUtils.namesEqual;
import static com.epam.jdi.tools.logger.LogLevels.INFO;
import static com.epam.jdi.tools.logger.LogLevels.STEP;
import static com.epam.jdi.uitests.core.settings.JDISettings.*;
import static com.epam.jdi.tools.StringUtils.format;

public abstract class TBaseElement extends Named implements IBaseElement {
    private Object parent;
    private IEngine engine;
    protected int waitTimeout = timeouts.getCurrentTimeoutSec();

    public <T extends IBaseElement> T withTimeout(int timoutSec) {
        waitTimeout = timoutSec;
        return (T) this;
    }
    public boolean isUseCache() { return engine().isUseCache(); }
    public void setUseCache(boolean useCache) { engine().setUseCache(useCache); }
    public static final String FAILED_TO_FIND_ELEMENT_MESSAGE
            = "Can't find Element '%s' during %s seconds";
    public static final String FIND_TO_MUCH_ELEMENTS_MESSAGE
            = "Find %s elements instead of one for Element '%s' during %s seconds";

    public boolean hasLocator() {
        return engine.hasLocator();
    }
    public String getDriverName() {
        return engine.getDriverName();
    }
    public void setDriverName(String driverName) { engine.setDriverName(driverName); }

    public Timer timer(int sec) {
        return new Timer(sec * 1000);
    }
    public Timer timer() {
        return timer(timeouts.getCurrentTimeoutSec());
    }
    public IEngine engine() {
        return engine;
    }

    public TBaseElement setEngine(IEngine engine) {
        this.engine = engine.copy();
        return this;
    }
    private LinkedElements linked = new LinkedElements();
    public LinkedElements linked() { return linked; }

    public void setWaitTimeout(int sec) {
        logger.debug("Set wait timeout to " + sec);
        timeouts.setCurrentTimeoutSec(sec);
    }

    public void restoreWaitTimeout() {
        setWaitTimeout(timeouts.getDefaultTimeoutSec());
    }

    protected String getParentName() {
        return parent == null ? "" : parent.getClass().getSimpleName();
    }
    public Object getParent() { return parent; }
    public <T> T setParent(Object parent) { this.parent = parent; return (T) this;}

    public void logAction(String actionName, LogLevels level) {
        logger.toLog(format(shortLogMessagesFormat
                ? "%s for %s"
                : "Perform action '%s' with Element (%s)", actionName, this.toString()), level);
    }

    public void logAction(String actionName) {
        logAction(actionName, INFO);
    }

    public <T> T asEntity(Class<T> entityClass) {
        try {
            T data = newEntity(entityClass);
            foreach(getFields(this, IHasValue.class), item -> {
                Field field = LinqUtils.first(getFields(data, String.class), f ->
                        namesEqual(f.getName(), item.getName()));
                if (field == null)
                    return;
                try {
                    field.set(data, ((IHasValue) getValueField(item, this)).getValue());
                } catch (Exception ignore) { }
            });
            return data;
        } catch (Exception ex) {
            throw exception("Can't get entity from Form" + getName() + " for class: " + entityClass.getClass());
        }
    }

    @Override
    public String toString() {
        if (logger.getLogLevel().equalOrMoreThan(STEP))
            return getName();
        String path = linked().isEmpty()
                ? engine.toString()
                : linked().toString();
        return format(shortLogMessagesFormat
                ? "%s '%s' (%s.%s; %s)"
                : "Type:'%s', Name:'%s', In: '%s.%s', Path: '%s'",
                getTypeName(), getName(), getParentName(), getFieldName(), path);
    }
}