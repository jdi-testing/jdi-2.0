package io.github.epam.tests;

import com.epam.web.matcher.testng.Assert;
import com.google.custom.SearchResult;
import io.github.epam.GoogleInit;
import org.testng.annotations.Test;

import java.util.List;

import static com.google.GoogleSite.homePage;
import static com.google.GoogleSite.searchPage;

/**
 * Created by Roman_Iovlev on 3/2/2018.
 */
public class ElementsGoogleTests extends GoogleInit {
    @Test
    public void resultsAsList() {
        Assert.contains(homePage.getDriver().getCurrentUrl(),
                "https://www.google.");
        homePage.search("jdi");
        List<SearchResult> jobs = searchPage.jobsE;
        for (SearchResult job : jobs)
            System.out.println(job.print());
    }
}
