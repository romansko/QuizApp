package com.rkcodesolution.quizapp;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ScoreFragment extends Fragment {
    public static final String SCORE = "score";
    public static final String SIZE = "size";
    private int mScore; // number of questions was answered correct.
    private int mSize;  // num of questions that was in the quiz.

    /**
     * Get a new fragment showing the score of the quiz.
     */
    public static ScoreFragment newInstance(int score, int size) {
        ScoreFragment fragment = new ScoreFragment();
        Bundle args = new Bundle();
        args.putInt(SCORE, score);
        args.putInt(SIZE, size);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mScore = getArguments().getInt(SCORE);
            mSize = getArguments().getInt(SIZE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView messageTV = view.findViewById(R.id.message);
        TextView statusTV = view.findViewById(R.id.status);
        TextView gradeTV = view.findViewById(R.id.grade);

        // set the grade. (score).
        int grade = mScore * 100 / mSize;
        String gradeString = Integer.toString(grade);
        gradeTV.setText(gradeString);

        // set message.
        if (grade >= 90 ){
            messageTV.setText(R.string.excellent);
            messageTV.setTextColor(Color.GREEN);
            gradeTV.setTextColor(Color.GREEN);
        } else if (grade < 56 ) {
            messageTV.setText(R.string.failed);
            messageTV.setTextColor(Color.RED);
            gradeTV.setTextColor(Color.RED);
        } else {
            messageTV.setText("");  // hide
        }

        String statusText = getString(R.string.questions_answered_status);
        statusText = statusText.replace("XXX", Integer.toString(mScore));
        statusText = statusText.replace("YYY", Integer.toString(mSize));
        statusTV.setText(statusText);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_score, container, false);
    }



}
