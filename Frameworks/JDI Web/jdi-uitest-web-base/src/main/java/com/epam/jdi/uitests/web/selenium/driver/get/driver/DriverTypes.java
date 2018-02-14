package com.epam.jdi.uitests.web.selenium.driver.get.driver;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

public enum DriverTypes {
    CHROME("chrome"),
    FIREFOX("firefox"),
    IE("ie");
    // TODO add all selenium supported drivers

    public String name;

    DriverTypes(String name) {
        this.name = name;
    }

}