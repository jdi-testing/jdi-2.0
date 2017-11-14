package com.epam.jdi.uitests.testing.unittests.pageobjects.mainwindow;

import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import com.epam.jdi.uitests.win.winnium.elements.common.TextBox;
import com.epam.jdi.uitests.win.winnium.elements.composite.Section;

public class MainWindow extends Section {
    @FindBy(id = "tabControl")
    public MainTabPane mainTabPane;

    @FindBy(id = "logTextBox")
    public TextBox logTextBox;

    @FindBy(id = "resultTextBox")
    public TextBox passwordTextBox;
}
