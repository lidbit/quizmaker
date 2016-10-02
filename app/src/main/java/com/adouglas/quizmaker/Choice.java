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

    public void setChoiceContent(String choiceContent)
    {
        this.choiceContent = choiceContent;
    }

    public void setCorrect(boolean correct)
    {
        this.correct = correct;
    }

    public String getChoiceContent()
    {
        return choiceContent;
    }

    public boolean getCorrect()
    {
        return correct;
    }

    Question question;
}
