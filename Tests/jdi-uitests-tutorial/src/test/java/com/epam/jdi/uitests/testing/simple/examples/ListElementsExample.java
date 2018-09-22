package com.epam.jdi.uitests.testing.simple.examples;

import com.epam.jdi.site.google.custom.SearchResult;
import com.epam.jdi.uitests.testing.GoogleTestsBase;
import com.epam.jdi.uitests.web.selenium.elements.complex.Elements;
import com.epam.matcher.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static com.epam.jdi.site.google.GoogleSite.homePage;
import static com.epam.jdi.site.google.GoogleSite.searchPage;
import static com.epam.jdi.tools.LinqUtils.map;
import static com.epam.jdi.tools.StringUtils.LINE_BREAK;

public class ListElementsExample extends GoogleTestsBase {
    @Test
    public void resultsAsList() {
        Assert.contains(homePage.getDriver().getCurrentUrl(),
                "https://www.google.");
        homePage.search("jdi");
        List<SearchResult> jobs = searchPage.jobsE;
        //Assertions.areEquals(vacancies.size(), 10);
        for (SearchResult job : jobs)
            System.out.println(job.print());
    }
    @Test
    public void resultsAsElements() {
        homePage.open();
        Assert.contains(homePage.getDriver().getCurrentUrl(),
                "https://www.google.");
        homePage.search("jdi");
        Assert.isTrue(searchPage.gitHubJdi.displayed());
        Elements<SearchResult> jobs = searchPage.jobsE;
        String results1 = getJobs(jobs);
        //Assertions.ignoreCase().each(select(vacancies,
        //        j -> j.description.getText())).contains("jdi");
        String results2 = getJobs(jobs);
        //Assertions.ignoreCase().each(select(vacancies,
        //        j -> j.description.getText())).contains("jdi");
        homePage.search("testing");
        jobs.refresh();
        String results3 = getJobs(jobs);
        //Assertions.ignoreCase().each(select(vacancies,
        //        j -> j.link.getText())).contains("testing");

        System.out.println(results1);
        System.out.println(results2);
        System.out.println(results3);
    }
    private String getJobs(Elements<SearchResult> jobs) {
        return "!!!" + String.join(LINE_BREAK, map(jobs, SearchResult::print));
    }


}
