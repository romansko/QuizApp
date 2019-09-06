package com.rkcodesolution.quizapp;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import static com.rkcodesolution.quizapp.QuizApp.QATAG;

public class QuestionAdapter extends ArrayAdapter<Question> {
    private final Activity mActivity;
    private ArrayList<Question> mQuestions;

    public QuestionAdapter(Activity activity, ArrayList<Question> questions) {
        super(activity, R.layout.question_view, questions);
        mActivity = activity;
        mQuestions = questions;
    }

    /**
     * Inner static class for holding the GUI for the question_view.xml
     */
    static class QuestionHolder {
        TextView QuestionTV;
        TextView AnswerTV;
        TextView ExplanationTV;
        ImageView Image;
    }

    @Override
    @NonNull
    public View getView(int questionIndex, View convertView, @NonNull ViewGroup parent) {

        // Inflate and create view only if doesn't exist.
        if (convertView == null) {
            LayoutInflater inflater = mActivity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.question_view, null);

            // configure view holder
            QuestionHolder qHolder = new QuestionHolder();
            qHolder.QuestionTV = convertView.findViewById(R.id.question);
            qHolder.AnswerTV = convertView.findViewById(R.id.answer);
            qHolder.ExplanationTV = convertView.findViewById(R.id.explanation);
            qHolder.Image = convertView.findViewById(R.id.image);
            convertView.setTag(qHolder);    // IMPORTANT.
        }

        /* Get Question and apply data to the view */
        final Question question = mQuestions.get(questionIndex);
        final QuestionHolder qHolder = (QuestionHolder) convertView.getTag();

        // set strings and image.
        qHolder.QuestionTV.setText(question.getQuestionString());
        qHolder.AnswerTV.setText(question.getCorrectAnswer());
        qHolder.ExplanationTV.setText(question.getExplanation());

        /* Try load image */
        try{
            QuizApp.sImageLoader.displayImage(question.getImageUrl(), qHolder.Image);
        }
        catch (Exception e) {
            Log.e(QATAG, e.getMessage());
        }

        // paint backgrounds.
        if (questionIndex % 2 == 0) {
            convertView.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.background));
        } else {
            convertView.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.background_teal));
        }

        return convertView;
    }

    public String getImageUrl(int position) {
        try {
            return mQuestions.get(position).getImageUrl();
        }
        catch (Exception e) {
            return null;
        }
    }



}   // QuestionAdapter
