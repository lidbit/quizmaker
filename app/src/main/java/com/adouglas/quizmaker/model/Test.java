package com.adouglas.quizmaker.model;
import com.adouglas.quizmaker.model.Question;
import com.orm.SugarRecord;
import java.util.List;

/**
 * Created by andrei on 01-Oct-16.
 */

public class Test extends SugarRecord {
    public String name;
    public String description;
    public String timelimit;
    public String testType;
    public Boolean running;
    public int userId;

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
        this.running = false;
    }

    public List<Question> getQuestions()
    {
        return Question.find(Question.class, "test = ?", getId().toString() );
    }

    @Override
    public String toString()
    {
        return this.name;
    }
}
