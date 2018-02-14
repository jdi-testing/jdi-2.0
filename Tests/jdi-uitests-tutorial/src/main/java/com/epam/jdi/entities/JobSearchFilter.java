package com.epam.jdi.entities;

import com.epam.jdi.enums.JobCategories;
import com.epam.jdi.tools.DataClass;

import static com.epam.jdi.enums.JobCategories.QA;
import static com.epam.jdi.enums.Locations.SAINT_PETERSBURG;

/**
 * Created by Roman_Iovlev on 10/22/2015.
 */
public class JobSearchFilter extends DataClass {
    public CharSequence keywords = "Test Automation Engineer (back-end)";
    public JobCategories skills = QA;
    public String location = SAINT_PETERSBURG.value;
    public JobSearchFilter(){ }

    public JobSearchFilter(CharSequence keywords, JobCategories skills, String location ){
        this.keywords=keywords;
        this.skills = skills;
        this.location=location;
    }

}
