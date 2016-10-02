package com.adouglas.quizmaker;
import com.orm.SugarRecord;
import java.util.List;

/**
 * Created by andrew on 2/10/2016.
 */

public class Question extends SugarRecord {
    String content;

    public Question() {}

    public Question(String content)
    {
        this.content = content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }

    @Override
    public String toString()
    {
        return content;
    }

    public Test test;

    public List<Choice> getChoices()
    {
        return Choice.find(Choice.class, "question = ?", getId().toString());
    }
}
