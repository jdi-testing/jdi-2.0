package com.epam.jdi.uitests.core.templates.base;
/* The MIT License
 *
 * Copyright 2004-2017 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:

 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

 */

/**
 * Created by Roman Iovlev on 10.03.2017
 */

import com.epam.jdi.tools.LinqUtils;
import com.epam.jdi.tools.Timer;
import com.epam.jdi.uitests.core.interfaces.base.IBaseElement;
import com.epam.jdi.uitests.core.interfaces.base.IEngine;
import com.epam.jdi.uitests.core.interfaces.base.IHasValue;
import com.epam.jdi.uitests.core.logger.LogLevels;

import java.lang.reflect.Field;
import java.text.MessageFormat;

import static com.epam.jdi.tools.LinqUtils.foreach;
import static com.epam.jdi.tools.ReflectionUtils.*;
import static com.epam.jdi.tools.StringUtils.namesEqual;
import static com.epam.jdi.uitests.core.logger.LogLevels.INFO;
import static com.epam.jdi.uitests.core.settings.JDISettings.*;
import static java.lang.String.format;

public abstract class TBaseElement extends Named implements IBaseElement {
    private Object parent;
    private IEngine engine;
    protected int waitTimeout = timeouts.getCurrentTimeoutSec();
    private boolean useCache;

    public <T extends IBaseElement> T withTimeout(int timoutSec) {
        waitTimeout = timoutSec;
        return (T) this;
    }
    public boolean isUseCache() { return useCache; }
    public void setUseCache(boolean useCache) { this.useCache = useCache; }
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
        toLog(format(shortLogMessagesFormat
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
        return MessageFormat.format(shortLogMessagesFormat
                        ? "{1} ''{0}'' ({2}.{3}; {4})"
                        : "Name: ''{0}'', Type: ''{1}'' In: ''{2}'', {4}",
                getName(), getTypeName(), getParentName(), getFieldName(), engine);
    }
}