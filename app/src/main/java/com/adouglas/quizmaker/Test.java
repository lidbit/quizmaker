package com.adouglas.quizmaker;

import com.orm.SugarRecord;

/**
 * Created by andrei on 01-Oct-16.
 */

public class Test extends SugarRecord {
    String name;
    String description;
    String timelimit;
    String testType;
    Boolean running;
    int userId;

    public Test()
    {

    }

    public Test(String name, String description, String timelimit, String testType, int userId)
    {
        this.name = name;
        this.description = description;
        this.timelimit = timelimit;
        this.testType = testType;
        this.userId = userId;
    }

    @Override
    public String toString()
    {
        return this.name;
    }
}
