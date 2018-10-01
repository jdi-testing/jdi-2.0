package com.epam.jdi.uitests.testing.unittests.tests.common;

import com.epam.jdi.uitests.core.interfaces.common.IButton;
import com.epam.jdi.uitests.testing.unittests.InitTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.function.Supplier;

import static com.epam.jdi.uitests.core.preconditions.PreconditionsState.isInState;
import static com.epam.jdi.uitests.testing.unittests.enums.Preconditions.METALS_AND_COLORS_PAGE;
import static com.epam.jdi.uitests.testing.unittests.pageobjects.EpamJDISite.metalsColorsPage;
import static com.epam.jdi.uitests.testing.unittests.tests.complex.CommonActionsData.checkAction;
import static com.epam.jdi.uitests.testing.unittests.tests.complex.CommonActionsData.checkCalculate;

/**
 * Test button element
 */
public class ButtonTests extends InitTests {
    private Supplier<IButton> button = () -> metalsColorsPage.calculateButton;

    @BeforeMethod
    public void before(final Method method) {
        isInState(METALS_AND_COLORS_PAGE, method);
    }
//checkAction
    @Test
    public void clickTest() {
        button.get().click();
        System.out.println(metalsColorsPage.calculateText.getAttribute("value"+"---------------------------"));
        System.out.println(metalsColorsPage.calculateText.getText()+"++++++++++++++++++++++++++++++++");
        checkCalculate("Summary: 3");
    }

    @Test
    public void clickActionTest() {
        //TODO 1/10/2018 failed because of non-implemented ITextList
        button.get().click();
        checkAction("button clicked");
    }


    @Factory
    public Object[] textTests() {
        return new Object[]{
                new TextTests("Button", METALS_AND_COLORS_PAGE, button::get, "CALCULATE", "CUL", ".*LCU.*")
        };
    }
}
