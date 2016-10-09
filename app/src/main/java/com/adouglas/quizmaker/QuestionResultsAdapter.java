package com.adouglas.quizmaker;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adouglas.quizmaker.model.QuestionResult;

import java.util.List;

/**
 * Created by andrew on 9/10/2016.
 */

public class QuestionResultsAdapter extends BaseAdapter {

    List<QuestionResult> questionResults;
    Context context;
    private static LayoutInflater inflater = null;

    public  QuestionResultsAdapter(Activity activity, List<QuestionResult> questionResults)
    {
        this.questionResults = questionResults;
        this.context = activity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return questionResults.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class QuestionResultViewModel
    {
        TextView questionContent;
        TextView userChoice;
        ImageView isCorrect;
        TextView correctAnswer;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        QuestionResultViewModel viewModel = new QuestionResultViewModel();
        View rowView;
        rowView = inflater.inflate(R.layout.question_result_list, null);
        viewModel.questionContent = (TextView) rowView.findViewById(R.id.questionContent);
        viewModel.userChoice = (TextView) rowView.findViewById(R.id.userChoice);
        viewModel.isCorrect = (ImageView) rowView.findViewById(R.id.is_correct);
        viewModel.correctAnswer = (TextView) rowView.findViewById(R.id.correctAnswer);

        Resources res = context.getResources();


        viewModel.questionContent.setText(questionResults.get(position).questionContent);
        viewModel.userChoice.setText(res.getString(R.string.user_choice,
                questionResults.get(position).userChoice));
        if(questionResults.get(position).correct)
        {
            viewModel.isCorrect.setImageResource(R.drawable.checkmark);
        }
        else {
            viewModel.isCorrect.setImageResource(R.drawable.delete);
        }
        viewModel.correctAnswer.setText(res.getString(R.string.correct_choice,
                questionResults.get(position).correctChoice));

        return rowView;
    }
}
