package io.github.epam.tests;

import io.github.epam.SimpleTestsInit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.github.epam.EpamGithubSite.*;
import static io.github.epam.enums.ColorsList.Green;
import static io.github.epam.enums.Metals.Gold;
import static io.github.epam.enums.Navigation.*;
import static io.github.epam.enums.Vegetables.Onion;
import static io.github.epam.enums.Vegetables.Tomato;
import static io.github.epam.steps.Preconditions.loggedIn;


public class ComplexElementsTests extends SimpleTestsInit {

    @BeforeMethod
    public void before() {
        loggedIn();
        metalAndColorsPage.shouldBeOpened();
    }
    @Test
    public void complexTest() {
        metalAndColorsPage.colors.select(Green);
        metalAndColorsPage.metals.select(Gold);
        metalAndColorsPage.vegetables.check(Onion, Tomato);
        System.out.println("Set elements");
    }
    @Test
    public void navigationTest() {
        navigation.select(ContactForm);
        contactFormPage.checkOpened();
        navigation.select(Home);
        homePage.checkOpened();
        navigation.select(ComplexTable);
    }
}
