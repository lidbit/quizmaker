package com.adouglas.quizmaker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by andrew on 7/10/2016.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private Map<Question, List<Choice>> questionListMap;
    private List<Question> questions;

    public ExpandableListAdapter(Activity context, List<Question> questions, Map<Question, List<Choice>> questionListMap)
    {
        this.context = context;
        this.questionListMap = questionListMap;
        this.questions = questions;
    }

    @Override
    public int getGroupCount() {
        return questions.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return questionListMap.get(questions.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return questions.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return questionListMap.get(questions.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Question question = (Question)getGroup(groupPosition);
        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.group_item, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.questionContent);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(question.content);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Choice choice = (Choice) getChild(groupPosition, childPosition);
        LayoutInflater layoutInflater = context.getLayoutInflater();
        if(convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.child_item, null);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.choiceCorrect);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Choice> choices =
                        questionListMap.get(questions.get(groupPosition));
                Choice c = choices.get(childPosition);
                c.correct = !c.correct;
                c.save();
                notifyDataSetChanged();
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to remove?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                List<Choice> choice =
                                        questionListMap.get(questions.get(groupPosition));
                                choice.remove(childPosition);
                                notifyDataSetChanged();
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        if(choice.correct)
        {
            imageView.setImageResource(R.drawable.checkmark);
        }
        else
        {
            imageView.setImageResource(R.drawable.delete);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.choiceContent);
        textView.setText(choice.choiceContent);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
