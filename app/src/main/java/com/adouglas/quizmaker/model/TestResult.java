package com.adouglas.quizmaker.model;

import com.orm.SugarRecord;
import java.util.Date;

/**
 * Created by andrew on 4/10/2016.
 */

public class TestResult extends SugarRecord {
    public String testName;
    public String testDescription;
    public Date dateTaken;
    public int correctAnswers;
    public int totalQuestions;
    public int testId;

    public TestResult()
    {

    }

    @Override
    public String toString()
    {
        return testName;
    }
}
