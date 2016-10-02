package com.adouglas.quizmaker;

import com.orm.SugarRecord;

/**
 * Created by andrew on 2/10/2016.
 */

public class Choice extends SugarRecord {

    String choiceContent;
    boolean correct;

    public Choice() {}

    public Choice(String choiceContent, boolean correct)
    {
        this.choiceContent = choiceContent;
        this.correct = correct;
    }

    Question question;

    @Override
    public String toString()
    {
        return choiceContent;
    }
}
