package com.rkcodesolution.quizapp;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import android.util.Log;
import java.util.HashSet;
import java.util.Set;

import static com.rkcodesolution.quizapp.QuizApp.QATAG;

/**
 * Class that represents a question.
 */
public class Question implements Parcelable, Cloneable {

    private static final int MIN_ANS = 2;   // minimum answers
    private String mQuestion;       // question string itself
    private Set<String> mChoices;   // all question answers
    private String mCorrectAnswer;  // correct answer
    private String mExplanation;    // exp. to show after answering.
    private String mImageUrl;       // image url.

    /**
     * Constructor with mandatory fields.
     * @param question Question string
     * @param choices array of answers.
     * @param correctAnswer correct answer string.
     */
    public Question(String question, Set<String> choices, String correctAnswer) throws IllegalArgumentException {
        mQuestion = question;
        mChoices = choices;
        mCorrectAnswer = correctAnswer;
        mExplanation = "";
        mImageUrl = "";
        if (!isValid()) {
            throw new IllegalArgumentException("Invalid Question arguments.");
        }
    }

    /**
     * @return true if question structure is valid.
     */
    public boolean isValid() {
        if (mQuestion == null || mQuestion.isEmpty()) {
            Log.e(QATAG, "Invalid Question.");
            return false;
        }
        if (mChoices == null || mChoices.size() < MIN_ANS){
            Log.e(QATAG, "Choices are not satisfied.");
            return false;
        }
        if (mCorrectAnswer == null || mCorrectAnswer.isEmpty()){
            Log.e(QATAG, "Invalid Correct answer.");
            return false;
        }
        if (!containsAnswer(mCorrectAnswer)) {
            Log.e(QATAG, "Choices doesn't contain correct answer.");
            return false;
        }
        return true;
    }

    /**
     * Check whether the answers set contains an answer
     */
    public boolean containsAnswer(String answer) {
        if (mChoices == null || answer == null || answer.isEmpty()) return false;
        return mChoices.contains(answer);
    }

    /**
     * Set explanation string.
     */
    public void setExplanation(String explanation) {
        if (explanation != null && !explanation.equals("$")) {
            mExplanation = explanation;
        }
    }

    /**
     * Set image url string.
     */
    public void setImageUrl(String imageUrl) {
        if (imageUrl != null) {
            mImageUrl = imageUrl;
        }
    }

    /**
     * Get the question string.
     */
    public String getQuestionString() {
        return mQuestion;
    }

    /**
     * Get choices strings set.
     */
    public Set<String> getChoices() {
        return mChoices;
    }


    /**
     * Get correct answer string.
     */
    public String getCorrectAnswer() {
        return mCorrectAnswer;
    }

    /**
     * Get explanation string.
     */
    public String getExplanation() {
        return mExplanation;
    }


    /**
     * Get image url string.
     */
    public String getImageUrl() {
        return mImageUrl;
    }

    @Override
    @NonNull
    public String toString() {
        return "\nQuestion: "+mQuestion + "\nChoices: " + mChoices + "\nCorrect: "+mCorrectAnswer;
    }


    /**************************** Parcelable implantation *********************/

    @Override
    public int describeContents() {
        return 0;       // not important.
    }

    /**
     * Needed for passing between activities.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mQuestion);
        dest.writeString(mCorrectAnswer);
        dest.writeInt(mChoices.size());     // set size
        for (String choice : mChoices) {
            dest.writeString(choice);
        }
        dest.writeString(mExplanation);
        dest.writeString(mImageUrl);
    }

    /**
     * Needed for passing between activities.
     */
    private Question(Parcel in) {
        mQuestion = in.readString();
        mCorrectAnswer = in.readString();
        int setSize = in.readInt();
        mChoices = new HashSet<>();
        for (int i=0; i<setSize; ++i) {
            mChoices.add(in.readString());
        }
        mExplanation = in.readString();
        mImageUrl = in.readString();
    }

    /**
     * Needed for making the question parsable.
     */
    public static final Creator CREATOR = new Creator() {
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        public Question[] newArray(int size) {
            return new Question[size];
        }

    };



    /******** Cloneable implantation *********/

    @Override
    protected Object clone() {
        /* Deep cloning is not needed */
        Question clonedQuestion = new Question(mQuestion, mChoices, mCorrectAnswer);
        clonedQuestion.setImageUrl(mImageUrl);
        clonedQuestion.setExplanation(mExplanation);
        return clonedQuestion;
    }


}
