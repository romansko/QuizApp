package com.rkcodesolution.quizapp;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import static com.rkcodesolution.quizapp.QuestionListActivity.LIST_KEY;
import static com.rkcodesolution.quizapp.QuizApp.QATAG;


public class MainActivity extends AppCompatActivity implements
        FireBaseHandler.IResponse, FireBaseHandler.ISubscribeHandler,
        ExitDialog.IExitListener, QuestionFragment.ICorrectAnswerListener {

    private Quiz mQuiz;

    // boolean flags for orientation changes.
    private boolean mCurrentAnswerChecked;
    private boolean mShowedScore;

    /* GUI */
    private TextView mQuestionsInDB;
    private QuestionFragment mQuestionFragment;
    private Button mCheckButton;
    private TextView mQuestionIndicator; // hold the current quiz progress. e.g. q.1 / 10.
    private ProgressBar mProgBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // linking
        mQuestionsInDB = findViewById(R.id.qInDB);
        mQuestionsInDB.setVisibility(View.INVISIBLE);       // until Questions populated.
        mProgBar = findViewById(R.id.progressBar);
        mCheckButton = findViewById(R.id.checkButton);
        mCheckButton.setVisibility(View.INVISIBLE);
        mQuestionIndicator = findViewById(R.id.qIndicator);

        mCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSubmitButtonClick();  // check / next Q. button.
            }
        });
        mQuestionIndicator.setVisibility(View.INVISIBLE);
    }

    /**
     * Fetch questions after activity is built.
     * For overcoming orientation problems when fragments not done building.
     */
    @Override
    protected void onStart() {
        super.onStart();

        // after activity is built, fetch questions. Answer is in onFireBaseResponse.
        FireBaseHandler.getQuestions(this, false);

        // subscribe to FireBase updates.
        FireBaseHandler.subscribeToUpdates(this);
    }

    /**
     * Display toast on main UI thread. Both for worker and UI threads.
     */
    private void displayToast(final Context context, final int message, final int duration) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, duration).show();
            }
        });
    }

    /**
     * Create new instance of Quiz if valid questions was provided.
     */
    @Override
    public void onFireBaseResponse(ArrayList<Question> questions) {
        mProgBar.setVisibility(View.INVISIBLE);
        mQuestionsInDB.setVisibility(View.INVISIBLE);   // questions in database.

        if (questions == null) {
            displayToast(this, R.string.fb_failed, Toast.LENGTH_LONG);
        } else if (questions.isEmpty()) {
            displayToast(this, R.string.fb_empty, Toast.LENGTH_LONG);
        } else {
            mQuiz = Quiz.getInstance(questions, this);  // Create new quiz object.
            mQuestionsInDB.setVisibility(View.VISIBLE);
            String displaySize = getResources().getString(R.string.q_in_db) + " " + questions.size();
            mQuestionsInDB.setText(displaySize);

            // we check this because orientation changes.
            if (mQuiz.isQuizEnded() && !mShowedScore) { // quiz ended and we didn't show score.
                setScoreFragment();
            } else {    // quiz didn't end or quiz showed score already.
                setQuestionFragment();
            }
            onQuizReady();
        }
    }

    /**
     * Set the score fragment
     */
    private void setScoreFragment()
    {
        ScoreFragment scoreFrag = ScoreFragment.newInstance(mQuiz.Score, mQuiz.size());
        addFragment(scoreFrag);
        mCheckButton.setText(R.string.load_new_quiz);
        mShowedScore = true;
        mQuestionIndicator.setVisibility(View.INVISIBLE);
    }


    /**
     * Set the QuestionFragment
     */
    private void setQuestionFragment()
    {
        mQuestionFragment = new QuestionFragment();
        addFragment(mQuestionFragment);
        mCheckButton.setText(R.string.check);
        mQuestionFragment.populateQuestion(mQuiz.getCurrentQuestion());
        mQuestionIndicator.setVisibility(View.VISIBLE);
    }


    /**
     * Hides the progress bar and populates the Question fragment.
     * Should be called only after Quiz object is ready.
     */
    private void onQuizReady() {
        mProgBar.setVisibility(View.INVISIBLE);
        mCheckButton.setVisibility(View.VISIBLE);
        updateQuestionCount();
        mCurrentAnswerChecked = false;
    }

    private static String FRAG_TAG = "FRAGTAG"; // for dynamic fragment adding/removing.
    /**
     * Add a fragment removing the added fragment marked by FRAG_TAG.
     * @param fragment fragment to add
     */
    private void addFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment toRemove = fm.findFragmentByTag(FRAG_TAG);
        if (toRemove != null) {
            fm.beginTransaction()
                    .remove(toRemove)
                    .add(R.id.fragContainer, fragment, FRAG_TAG)
                    .commitNow();       // wait transaction to finish.
        } else {
            fm.beginTransaction()
                    .add(R.id.fragContainer, fragment, FRAG_TAG)
                    .commitNow();       // wait transaction to finish.
        }
    }

    /**
     * Handle submit button click.
     */
    private void handleSubmitButtonClick() {
        if (mQuiz.isQuizEnded()) {

            if (mShowedScore) {
                mShowedScore = false;
                mQuiz.populateNewQuiz();
                setQuestionFragment();
                onQuizReady();
                displayToast(this, R.string.new_quiz_populated, Toast.LENGTH_SHORT);
            } else {    // didn't show score.
                setScoreFragment();
            }
            return; // return from quiz ended-if.
        }

        if (mCurrentAnswerChecked){
            mQuestionFragment.populateQuestion(mQuiz.getCurrentQuestion());
            updateQuestionCount();
            mCurrentAnswerChecked = false;
            mCheckButton.setText(R.string.check);
            return;
        }

        mCurrentAnswerChecked = mQuestionFragment.submitClicked(this);
        if (mCurrentAnswerChecked) {
            mQuestionFragment.disableChoices();
            mQuiz.nextQuestion();
            mCheckButton.setText(R.string.next_question);
        } else {
            mCheckButton.setText(R.string.check);
        }
    }


    /**
     * Updates textually the question progress in the current quiz.
     */
    private void updateQuestionCount() {
        String toSet = getString(R.string.question_x_out_of);
        toSet = toSet.replace("XXX", Integer.toString(mQuiz.getCurrentQuestionNumber()));
        toSet = toSet.replace("YYY", Integer.toString(mQuiz.size()));
        mQuestionIndicator.setText(toSet);
    }


    /**
     *  We have to implement this function for menu to work.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    /**
     * Menu selection clicked.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != R.id.exit && mQuiz == null) {
            displayToast(this, R.string.fb_failed, Toast.LENGTH_LONG);
            return true;
        }

        // Handle item selection
        switch (item.getItemId()) {

            case R.id.reloadQuiz: {     // reset current quiz.
                mQuiz.reloadSameQuiz();
                mQuestionFragment.populateQuestion(mQuiz.getCurrentQuestion());
                updateQuestionCount();
                mCurrentAnswerChecked = false;
                mCheckButton.setText(R.string.check);
                displayToast(this, R.string.current_quiz_reloaded, Toast.LENGTH_LONG);
                return true;
            }

            case R.id.newQuiz: {        // new quiz.
                mQuiz.populateNewQuiz();
                mQuestionFragment.populateQuestion(mQuiz.getCurrentQuestion());
                onQuizReady();
                mQuestionIndicator.setVisibility(View.VISIBLE);
                displayToast(this, R.string.new_quiz_populated, Toast.LENGTH_SHORT);
                return true;
            }

            case R.id.setQuizSize: {            // set size and populate new quiz.
                setNumberOfQuestionPerQuiz();
                return true;
            }

            case R.id.allQuesitonsView:  {
                ArrayList<Question> allQuestions = mQuiz.getAllQuestions();
                if (allQuestions.isEmpty()) {
                    displayToast(this, R.string.no_questions, Toast.LENGTH_LONG);
                } else {
                    Log.i(QATAG, "MainActivity: Populating " + allQuestions.size() +
                            " questions to QuestionListActivity.");

                    // open new activity.
                    Intent intent = new Intent(MainActivity.this, QuestionListActivity.class);
                    intent.putParcelableArrayListExtra(LIST_KEY, allQuestions);
                    startActivity(intent);
                }
                return true;
            }

            case R.id.resetAll: {
                mQuiz.reset();      // repopulate questions session. do not reload quiz.
                displayToast(this, R.string.all_questions_reloaded, Toast.LENGTH_LONG);
                return true;
            }

            case R.id.exit: {
                new ExitDialog().show(getSupportFragmentManager(), "exitDialog");
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Set question number per quiz by showing dialog to the user.
     */
    private void setNumberOfQuestionPerQuiz() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.q_in_quiz);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);
        final Context context = this;
        builder.setPositiveButton(R.string.define, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    int size = Integer.parseInt(input.getText().toString());
                    // set means that value is valid.
                    boolean set = mQuiz.setQuizSize(getApplicationContext(), size);
                    if (set) {
                        Log.i(QATAG, "Q per quiz was set: " + size);

                        if (mShowedScore) {
                            mShowedScore = false;
                            mQuiz.populateNewQuiz();
                            setQuestionFragment();
                        } else {
                            mQuestionFragment.populateQuestion(mQuiz.getCurrentQuestion());
                        }
                        onQuizReady();
                        mQuestionIndicator.setVisibility(View.VISIBLE);
                        displayToast(context, R.string.new_quiz_populated, Toast.LENGTH_SHORT);
                    } else {
                        displayToast(context, R.string.invalid_input, Toast.LENGTH_SHORT);
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                    displayToast(context, R.string.invalid_input, Toast.LENGTH_SHORT);
                    dialog.dismiss();
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    public void onExitPressed() {
        finish();
    }

    @Override
    public void correctQuestionPressed() {
        mQuiz.Score++;
    }

    /**
     * reset all questions from DB
     */
    @Override
    public void reloadFireBaseQuestionsClicked() {
        Log.i(QATAG, "Hard Resetting: reloading questions from DB.");
        FireBaseHandler.getQuestions(this, true);
    }

    /**
     * Display a message on the UI Thread.
     */
    @Override
    public void displayMessageOnUiThread(final String message,
                                         final DialogInterface.OnClickListener clickListener) {
        // Open a dialog that asks the user if he want to reset all questions.
        final Context context = this;
        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(message)
                        .setPositiveButton(getResources().getString(R.string.yes), clickListener)
                        .setNegativeButton(getResources().getString(R.string.no), clickListener)
                        .show();
            }
        });
    }

}
