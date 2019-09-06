package com.rkcodesolution.quizapp;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static com.rkcodesolution.quizapp.QuizApp.QATAG;

/**
 * Logical class to handle quiz logic.
 */
public class Quiz {
    private static Quiz sQuiz;       // static instance.
    private static final String SETTINGS = "quizapp.settings";      // to use for saving data.
    private static ArrayList<Question> sQuestions;      // questions left in session.
    private static ArrayList<Question> sQuestionsSave;  // all questions untouched.
    private int mSize;                                // current quiz size.
    private ArrayList<Question> mCurrentQuiz;         // current quiz questions
    private int mCurrentQuestionIndex;   // current question index relatively to mCurrentQuiz.
    public int Score;       // represents the correct questions answered.

    /**
     * @return static instance of the quiz.
     */
    public static Quiz getInstance(ArrayList<Question> questions, Context context) {
        if (questions == null || questions.isEmpty()) {
            Log.e(QATAG, "Quiz::makeQuiz: empty questions provided.");
            return sQuiz;
        }

        if (sQuiz == null || questions.size() != sQuestionsSave.size()) {
            int quizSize = 10;      // default.
            /* Try to read saved quiz size, do nothing if failed */
            try {
                FileInputStream fin = context.openFileInput(SETTINGS);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
                quizSize = Integer.parseInt(reader.readLine());
                reader.close();
                fin.close();
            } catch (Exception e) {
                // Don't care.
            }
            if (quizSize <= 0 || quizSize > questions.size()) {
                quizSize = 10;
            }
            sQuiz = new Quiz(questions, quizSize);
        }
        return sQuiz;
    }

    /**
     * Private constructor
     *
     * @param questions not null.
     * @param size      0 < size < questions.size()
     */
    private Quiz(@NonNull ArrayList<Question> questions, int size) {
        mSize = size;      // how many questions in single quiz.
        sQuestionsSave = questions;
        reset();
        populateNewQuiz();
    }

    /**
     * Repopulate read saved questions.
     */
    public void reset() {
        try {
            sQuestions = new ArrayList<>(sQuestionsSave);
            shuffleQuestions(sQuestions);   // randomly shuffle questions.
        } catch (Exception e) {
            Log.e(QATAG, "Quiz::reset: " + e.getMessage());
        }
    }


    /**
     * @return shallow copy of all questions.
     */
    public ArrayList<Question> getAllQuestions() {
        return new ArrayList<>(sQuestionsSave); // shallow copy.
    }

    /**
     * @return current quiz size.
     */
    public int size() {
        return mSize;
    }


    /**
     * @return true if user answered all the quiz.
     */
    public boolean isQuizEnded() {
        return (mCurrentQuestionIndex >= mSize);
    }

    /**
     * @return current question.
     */
    public Question getCurrentQuestion() {

        /* This occur when score is showed and orientation is changed. */
        if (mCurrentQuestionIndex >= mSize){
            populateNewQuiz();
        }
        return mCurrentQuiz.get(mCurrentQuestionIndex);
    }

    /**
     * @return current question number.
     */
    public int getCurrentQuestionNumber() {
        return (mCurrentQuestionIndex + 1);   // 1 to mSize.
    }

    /**
     * Increment index to point at next question.
     */
    public void nextQuestion() {
        mCurrentQuestionIndex++;
    }

    /**
     * Set new quiz size.
     *
     * @param context calling context.
     * @param size    new quiz size.
     */
    public boolean setQuizSize(Context context, int size) {
        if (size <= 0 || size > sQuestionsSave.size()) {
            Log.e(QATAG, "Quiz::setQuizSize: size was not set. requested: " +
                    size + ". AllQuestions size: " + sQuestionsSave.size());
            return false;
        }

        mSize = size;

        /* try write quiz size to file */
        try {
            FileOutputStream fos = context.openFileOutput(SETTINGS, Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            writer.write(Integer.toString(mSize));
            writer.close();
            fos.close();
        } catch (Exception e) {
            Log.e(QATAG, "Quiz::setQuizSize: " + e.getMessage());
        }

        populateNewQuiz();
        return true;
    }

    /**
     * Reload same quiz just by resetting the index.
     */
    public void reloadSameQuiz() {
        mCurrentQuestionIndex = 0;
        Score=0;
    }

    /**
     * populate a quiz by extracting questions from questions list.
     */
    public void populateNewQuiz() {
        ArrayList<Question> extracted = new ArrayList<>();
        for (int i = 0; i < mSize; ++i) {   // mSize is the num of Question in the quiz.
            amendConsistency(extracted);    // session questions might be empty in the middle.
            extracted.add(sQuestions.remove(sQuestions.size() - 1));
        }
        mCurrentQuiz = extracted;
        mCurrentQuestionIndex = 0;
        Score=0;
    }

    /**
     * Check if the question array is empty.
     * If empty, repopulate with all questions and shuffle.
     * Ignore population of given questions in the input list
     */
    private void amendConsistency(@NonNull ArrayList<Question> toIgnore) {
        if (sQuestions.size() == 0) {
            sQuestions = new ArrayList<>(sQuestionsSave);
            for (Question ignoredQ : toIgnore) {
                sQuestions.remove(ignoredQ);
            }
            shuffleQuestions(sQuestions);
        }
    }

    /**
     * Randomly shuffle objects array.
     */
    public static void shuffleObjects(@NonNull Object[] arr) {
        if (arr.length == 0) return;
        for (int i = arr.length - 1; i > 0; --i) {
            int j = (int) (Math.random() * (i + 1));
            /* Swap */
            Object o = arr[i];
            arr[i] = arr[j];
            arr[j] = o;
        }
    }

    /**
     * Randomly shuffle questions list.
     */
    private static void shuffleQuestions(@NonNull ArrayList<Question> list) {
        if (list.isEmpty()) return;
        for (int i = list.size() - 1; i > 0; --i) {
            int j = (int) (Math.random() * (i + 1));
            /* Swap */
            Question q = list.get(i);
            list.set(i, list.get(j));
            list.set(j, q);
        }
    }

}
