package com.epam.jdi.uitests.testing.unittests.pageobjects.sections;

import com.epam.jdi.tools.LinqUtils;
import com.epam.jdi.uitests.core.interfaces.complex.IList;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import org.openqa.selenium.WebElement;

import java.util.List;


public class LogsPanel extends Section {
    @FindBy(css = ".logs li")
    public IList<WebElement> actionsLog;

    @FindBy(css = ".results li")
    public IList<WebElement> resultsLog;

    public String getLastActionsLog() {
        return getActionsLogs().get(0);
    }

    public String getLastResultsLog() {
        return getResultsLogs().get(0);
    }

    public List<String> getActionsLogs() {
        return asText(actionsLog);
    }

    public List<String> getResultsLogs() {
        return asText(resultsLog);
    }

    private List<String> asText(IList<WebElement> logs) {
        return LinqUtils.map(logs.getAll().values(), WebElement::getText);
    }

}


