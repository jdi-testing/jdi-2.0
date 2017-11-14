package com.epam.jdi.site.epam.pages;

import com.epam.jdi.entities.Job;
import com.epam.jdi.site.epam.CustomElements.JobRecord;
import com.epam.jdi.uitests.core.interfaces.complex.tables.ITable;
import com.epam.jdi.uitests.core.interfaces.complex.tables.TableLine;
import com.epam.jdi.uitests.web.selenium.elements.complex.table.EntityTable;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JTable;

import static com.epam.jdi.enums.JobListHeaders.apply;
import static com.epam.jdi.enums.JobListHeaders.name;
import static com.epam.jdi.uitests.core.interfaces.complex.tables.Column.column;
import static com.epam.jdi.uitests.core.utils.common.Filters.equalsTo;

/**
 * Created by Roman_Iovlev on 10/22/2015.
 */
public class JobListingPage extends WebPage {

    @JTable(
        root = @FindBy(className = "search-result-list"),
        row = @FindBy(xpath = ".//li[%s]//div"),
        column = @FindBy(xpath = ".//li//div[%s]"),
        cell = @FindBy(xpath = ".//li[{1}]//div[{0}]"),
        header = {"name", "category", "location", "apply"})
    public ITable jobsList;

    @JTable(
            root = @FindBy(className = "search-result-list"),
            row = @FindBy(xpath = ".//li[%s]//div"),
            column = @FindBy(xpath = ".//li//div[%s]"),
            //cell = @FindBy(xpath = ".//li[{1}]//div[{0}]"),
            header = {"name", "category", "location", "apply"})
    public EntityTable<Job, JobRecord> jobsListEntity;

    public void getJobRowByName(String jobName) {
        JobRecord row = jobsListEntity.line(equalsTo(jobName), column(name));
        row.apply.click();
    }
    public void getJobRow(String jobName) {
        TableLine row = jobsList.row(equalsTo(jobName), column(name));
        row.get(apply.toString()).select();
    }
}
