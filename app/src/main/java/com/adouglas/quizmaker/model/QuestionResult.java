package com.adouglas.quizmaker.model;

import com.orm.SugarRecord;

/**
 * Created by andrew on 4/10/2016.
 */

public class QuestionResult extends SugarRecord {
    public String questionContent;
    public boolean correct;
    public String userChoice;
    public String correctChoice;

    public QuestionResult() {}

    public TestResult testResult;

    @Override
    public String toString()
    {
        return userChoice + " " + correct;
    }
}
