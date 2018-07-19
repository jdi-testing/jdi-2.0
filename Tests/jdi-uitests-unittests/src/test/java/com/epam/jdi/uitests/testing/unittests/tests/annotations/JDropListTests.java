package com.epam.jdi.uitests.testing.unittests.tests.annotations;

import com.epam.jdi.uitests.core.interfaces.complex.IDropList;
import com.epam.jdi.uitests.testing.unittests.InitTests;
import com.epam.jdi.uitests.testing.unittests.tests.common.dataProviders.JDropListDP;
import com.epam.matcher.testng.TestNG;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.jdi.uitests.testing.unittests.pageobjects.EpamJDISite.metalsColorsPage;

public class JDropListTests extends InitTests {
    @BeforeMethod
    public void pageLoad() {
        metalsColorsPage.open();
    }

    @Test(dataProvider = "dropListData", dataProviderClass = JDropListDP.class)
    public void dropListTest(IDropList dropList) {

        new TestNG().areEquals(dropList.getOptionsAsText(), "Cucumber, Tomato, Salad, Onion");

        dropList.select("Tomato");
        List<String> listTomato = Stream.of("Tomato").collect(Collectors.toList());
        new TestNG().areEquals(dropList.areSelected(), listTomato);

        dropList.select("Tomato", "Cucumber");
        List<String> listTomatoCucumber = Stream.of("Tomato", "Cucumber").collect(Collectors.toList());
        new TestNG().areEquals(dropList.areDeselected(), listTomatoCucumber);

    }

}
