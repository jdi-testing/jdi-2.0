package com.epam.jdi.uitests.testing.unittests.tests.annotations;

import com.epam.jdi.uitests.core.interfaces.complex.IComboBox;
import com.epam.jdi.uitests.testing.unittests.InitTests;
import com.epam.jdi.uitests.testing.unittests.enums.ColorsList;
import com.epam.jdi.uitests.testing.unittests.tests.common.dataProviders.jComboBoxDP;
import com.epam.matcher.junit.JUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.epam.jdi.uitests.testing.unittests.pageobjects.EpamJDISite.metalsColorsPage;

public class JComboBoxTests extends InitTests {
    @BeforeMethod
    public void pageLoad() {
        metalsColorsPage.open();
    }

    @Test(dataProvider = "comboBoxData", dataProviderClass = jComboBoxDP.class)
    public void comboBoxTest(IComboBox comboBox, Boolean option) {
        new JUnit().areEquals(comboBox.getText(), "Colors");
        new JUnit().areEquals(comboBox.getValue(), "Colors");
        new JUnit().areEquals(comboBox.getOptionsAsText(), "Colors,Red,Green,Blue,Yellow");
        new JUnit().areEquals(comboBox.getOptions(), "[Colors, Red, Green, Blue, Yellow]");
        new JUnit().areEquals(comboBox.getValues(), "[Colors, Red, Green, Blue, Yellow]");


        comboBox.expand();
        new JUnit().isTrue(comboBox.displayed());
        comboBox.close();
        new JUnit().isFalse(comboBox.displayed());

        comboBox.select("Blue");
        new JUnit().areEquals(comboBox.getSelected(), "Blue");
        comboBox.select(2);

        new JUnit().areEquals(comboBox.getSelected(), "Red");

        comboBox.select(ColorsList.Green);
        new JUnit().areEquals(comboBox.getSelected(), "Green");

    }


    @Test(dataProvider = "comboBoxData", dataProviderClass = jComboBoxDP.class)
    public void comboBoxTestGetSelectedIndex(IComboBox comboBox, Boolean option) {
        comboBox.select(2);
        new JUnit().areEquals(comboBox.getSelectedIndex(), 2);
    }


}
