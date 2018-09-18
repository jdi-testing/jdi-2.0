package com.epam.jdi.uitests.core.interfaces.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

/**
 * Interface for any engines being test basis
 */
public interface IEngine {
    /**
     * @return Has the element specified locator or not
     */
    boolean hasLocator();

    /**
     * @return Isn't empty engine's element
     */
    boolean hasElement();

    /**
     * @return Does engine's element uses cache
     */
    boolean isUseCache();

    /**
     * @param useCache Set using cache parameter
     */
    void setUseCache(boolean useCache);

    /**
     * @return Copies current engine
     */
    IEngine copy();

    /**
     * @return Driver name
     */
    String getDriverName();

    /**
     * @param driverName Specify driver's name
     */
    void setDriverName(String driverName);
}
