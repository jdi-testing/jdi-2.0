package com.epam.jdi.uitests.core.interfaces.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

public interface IEngine {
    boolean hasLocator();
    boolean hasElement();
    boolean isUseCache();
    void setUseCache(boolean useCache);
    IEngine copy();

    String getDriverName();
    void setDriverName(String driverName);
}
