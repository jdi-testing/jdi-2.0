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
     * Has the element locator
     *
     * @return Has the element specified locator or not
     */
    boolean hasLocator();

    /**
     * Is the engine's element empty
     *
     * @return Isn't empty engine's element
     */
    boolean hasElement();

    /**
     * Get value of {@code useCache} parameter
     *
     * @return Does engine's element uses cache
     */
    boolean isUseCache();

    /**
     * Set useCache parameter
     *
     * @param useCache Set using cache parameter
     */
    void setUseCache(boolean useCache);

    /**
     * Copy current driver
     *
     * @return Copies current engine
     */
    IEngine copy();

    /**
     * Get Driver's name
     *
     * @return Driver name
     */
    String getDriverName();

    /**
     * Set driver's name
     *
     * @param driverName Specify driver's name
     */
    void setDriverName(String driverName);
}
