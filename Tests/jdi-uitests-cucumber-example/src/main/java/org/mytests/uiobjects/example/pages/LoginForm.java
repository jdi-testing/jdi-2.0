package org.mytests.uiobjects.example.pages;

import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.common.TextField;
import com.epam.jdi.uitests.web.selenium.elements.composite.Form;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import org.mytests.uiobjects.example.entities.User;

/**
 * Created by Roman_Iovlev on 10/22/2015.
 */
public class LoginForm extends Form<User> {
    @FindBy(id="Login")
    public TextField name;
    @FindBy(id="Password")
    public TextField password;

    @FindBy(css="[type=submit]")
    public Button enter;
}
