package com.epam.jdi.uitests.testing.unittests.pageobjects.sections;

import com.epam.jdi.tools.LinqUtils;
import com.epam.jdi.uitests.core.interfaces.common.IText;
import com.epam.jdi.uitests.core.interfaces.complex.IList;
import com.epam.jdi.uitests.web.selenium.elements.common.Text;
import com.epam.jdi.uitests.web.selenium.elements.complex.Elements;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;

import java.util.List;


public class LogsPanel extends Section {
    @FindBy(css = ".logs li")
    public IList<Text> actionsLog;

    @FindBy(css = ".results")
    public IList<Text> resultsLog;

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

    private List<String> asText(IList<Text> logs) {
        return LinqUtils.map(logs.getAll().values(), IText::getText);
    }

}


