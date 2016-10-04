package com.adouglas.quizmaker;

import com.orm.SugarRecord;

/**
 * Created by andrew on 4/10/2016.
 */

public class QuestionResult extends SugarRecord {
    String questionContent;
    boolean correct;
    String userChoice;
    String correctChoice;

    public QuestionResult() {}

    TestResult testResult;

    @Override
    public String toString()
    {
        return userChoice + " " + correct;
    }
}
