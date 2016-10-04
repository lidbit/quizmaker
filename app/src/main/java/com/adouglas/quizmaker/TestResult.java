package com.adouglas.quizmaker;

import com.orm.SugarRecord;
import java.util.Date;

/**
 * Created by andrew on 4/10/2016.
 */

public class TestResult extends SugarRecord {
    String testName;
    String testDescription;
    Date dateTaken;
    int correctAnswers;
    int totalQuestions;
    int testId;

    public TestResult()
    {

    }

    @Override
    public String toString()
    {
        return testName;
    }
}
