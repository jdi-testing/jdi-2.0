package com.epam.jdi.uitests.testing.unittests.tests.common;

import com.epam.jdi.uitests.core.actions.common.CheckboxActions;
import com.epam.jdi.uitests.testing.unittests.InitTests;
import com.epam.jdi.uitests.testing.unittests.tests.common.dataProviders.CheckBoxDP;
import com.epam.jdi.uitests.web.selenium.elements.common.CheckBox;
import com.epam.matcher.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.jdi.uitests.core.preconditions.PreconditionsState.isInState;
import static com.epam.jdi.uitests.testing.unittests.enums.Preconditions.METALS_AND_COLORS_PAGE;
import static com.epam.jdi.uitests.testing.unittests.pageobjects.EpamJDISite.metalsColorsPage;
import static com.epam.jdi.uitests.testing.unittests.tests.complex.CommonActionsData.checkAction;

/**
 * Created by Dmitry_Lebedev1 on 15/12/2015.
 */
public class CheckboxTests extends InitTests {
    //TODO 1/10/2018 failed: executor cannot reach @Override public boolean isChecked() in metalsColorsPage.cbWater

    private CheckBox checkBox() {
        return metalsColorsPage.cbWater;
    }

    @BeforeMethod
    public void before(final Method method) {
        isInState(METALS_AND_COLORS_PAGE, method);
    }

    @Test
    public void checkSingleTest() {
        checkBox().check();
        checkAction("Water: condition changed to true");
    }

    @Test
    public void uncheckSingleTest() {
        //TODO 1/10/2018 failed because of non-implemented ITextList
        checkBox().click();
        checkBox().uncheck();
        checkAction("");
    }

    @Test
    public void isCheckTest() {
        Assert.isFalse(checkBox().isChecked());
        checkBox().click();
        Assert.isTrue(checkBox().isChecked());
    }

    @Test
    public void multiUncheckTest() {
        //TODO 1/10/2018 failed because of non-implemented ITextList
        checkBox().click();
        checkBox().uncheck();
        checkBox().uncheck();
        checkAction("Water: condition changed to false");
    }

    @Test
    public void multiCheckTest() {
        //TODO 1/10/2018 failed because of non-implemented ITextList
        checkBox().check();
        checkBox().check();
        checkAction("Water: condition changed to true");
    }

    @Test
    public void clickTest() {
        //TODO 1/10/2018 failed because of non-implemented ITextList
        checkBox().click();
        checkAction("Water: condition changed to true");
        checkBox().click();
        checkAction("Water: condition changed to false");
    }

    @Test(dataProvider = "inputData", dataProviderClass = CheckBoxDP.class)
    public void setValue(String input, Boolean expected){
        if (!expected)
            checkBox().click();
        checkBox().setValue(input);
        String resMsg = "Water: condition changed to " + expected.toString();
        checkAction(resMsg);
    }

    @Test(dataProvider = "inputDataNothing2Changed", dataProviderClass = CheckBoxDP.class)
    public void setValueNothing2Changed(String input){
        checkBox().click();
        checkBox().setValue(input);
        checkAction("Water: condition changed to true");
        checkBox().click();
        checkBox().setValue(input);
        checkAction("Water: condition changed to false");
    }
}
