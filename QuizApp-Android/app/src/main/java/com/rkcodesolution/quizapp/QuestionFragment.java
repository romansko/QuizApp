package com.rkcodesolution.quizapp;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import static com.rkcodesolution.quizapp.QuizApp.QATAG;


public class QuestionFragment extends Fragment {
    private static Drawable sButtonBackground;  // default background.
    private Question mCurrentQuestion;
    private TextView mQuestionTV;
    private TextView mExplanation;
    private ViewGroup mChoicesContainer;
    private ImageView mImageView;
    private ScrollView mScrollView;
    private ConstraintLayout.LayoutParams mLayoutParamsWithImage;
    private ConstraintLayout.LayoutParams mLayoutParamsWithoutImage;
    private ChoiceClickHandler mChoiceClickHandler;     // responsible for coloring choices.

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /* Linking */
        mQuestionTV = view.findViewById(R.id.question);
        mExplanation = view.findViewById(R.id.explanation);
        mChoicesContainer = view.findViewById(R.id.choices);
        mImageView = view.findViewById(R.id.zoomage);
        mScrollView = view.findViewById(R.id.scrollView);

        mLayoutParamsWithImage = (ConstraintLayout.LayoutParams) mScrollView.getLayoutParams();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            View tempView = view.findViewById(R.id.tempView);
            mLayoutParamsWithoutImage = (ConstraintLayout.LayoutParams) tempView.getLayoutParams();
            tempView.setVisibility(View.GONE);
        } else {
            mLayoutParamsWithoutImage = mLayoutParamsWithImage; // landscape view
        }

        mChoiceClickHandler = new ChoiceClickHandler();
        sButtonBackground = (new Button(getContext())).getBackground();
    }

    /**
     * Populate a question to the fragment
     * @param question the question to populate.
     */
    public void populateQuestion(Question question) {
        mCurrentQuestion = question;
        mQuestionTV.setText(question.getQuestionString());
        mExplanation.setText("");
        if (question.getImageUrl().isEmpty()) {
            mImageView.setVisibility(View.INVISIBLE);   // do not remove, just hide.
            mScrollView.setLayoutParams(mLayoutParamsWithoutImage);
        } else {
            QuizApp.sImageLoader.displayImage(question.getImageUrl(), mImageView);
            mImageView.setVisibility(View.VISIBLE);
            mScrollView.setLayoutParams(mLayoutParamsWithImage);
        }

        // Populate choices
        mChoicesContainer.removeAllViews();
        Object[] choices = question.getChoices().toArray();
        if (choices != null) {
            Quiz.shuffleObjects(choices);
            for (Object choice : choices) {
                Button choiceHolder = new Button(getContext());
                choiceHolder.setText((String) choice);
                choiceHolder.setOnClickListener(mChoiceClickHandler);
                choiceHolder.setBackground(sButtonBackground);
                mChoicesContainer.addView(choiceHolder);    // add button to linear layout
            }   //foreach
        } else {
            Log.e(QATAG, "populateQuestion: Failed to get choices. Question: " + question.getQuestionString());
        }
    }

    /**
     * Correct answer pressed listener.
     */
    public interface ICorrectAnswerListener {
        void correctQuestionPressed();
    }


    /**
     * Handles check button click from main activity.
     * returns true if answer checked.
     */
    public boolean submitClicked(ICorrectAnswerListener listener) {
        if (mCurrentQuestion == null) {
            return false;
        }

        Button correct = null;
        Button selected = null;

        // for loop for holding reference for selected and correct buttons.
        for (int i = 0; i < mChoicesContainer.getChildCount(); ++i) {
            Button c = (Button) mChoicesContainer.getChildAt(i);
            String cText = c.getText().toString();  // get text from button.
            if (mCurrentQuestion.getCorrectAnswer().equals(cText)) {
                correct = c;
                if (!(c.getBackground().equals(sButtonBackground))) {   // selected right answer.
                    selected = c;
                    listener.correctQuestionPressed();
                }
            } else {    // wrong answer
                if (!(c.getBackground().equals(sButtonBackground))) { // selected wrong answer.
                    selected = c;
                }
            }
        }   // for

        if (correct == null || selected == null) {
            Toast.makeText(getContext(), R.string.select_answer, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            selected.setBackgroundColor(Color.RED);
            correct.setBackgroundColor(Color.GREEN);
        }
        mExplanation.setText(mCurrentQuestion.getExplanation());
        return true;
    }


    /**
     * Make choices un-clickable.
     */
    public void disableChoices() {
        for (int i = 0; i < mChoicesContainer.getChildCount(); ++i) {
            View c = mChoicesContainer.getChildAt(i);
            c.setClickable(false);
        }
    }


    /**
     * Inner class to handle choices touch. Just to make a choice look selected on touch.
     */
    private class ChoiceClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getBackground().equals(sButtonBackground)) {  // not selected

                /* Un-select others */
                for (int i = 0; i < mChoicesContainer.getChildCount(); ++i) {
                    View c = mChoicesContainer.getChildAt(i);
                    c.setBackground(sButtonBackground);     // set button background to grey.
                    ((Button) c).setTypeface(null, Typeface.NORMAL);    // set font to normal
                }

                // set selected button background to teal.
                v.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.selected));
                ((Button) v).setTypeface(null, Typeface.BOLD);  // set selected button font to bold.

            } else {      // already selected
                v.setBackground(sButtonBackground);
                ((Button) v).setTypeface(null, Typeface.NORMAL); // set font to normal
            }
        }
    }


}
