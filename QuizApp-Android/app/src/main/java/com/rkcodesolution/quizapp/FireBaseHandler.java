package com.rkcodesolution.quizapp;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import static com.rkcodesolution.quizapp.QuizApp.QATAG;

public class FireBaseHandler extends FirebaseMessagingService {
    private static final String ROOT_NAME = "rootName";   // Root name in FireBase DB

    /* Keys for each question as defined in FireBase */
    private final static String KEY_QUESTION = "question";
    private final static String KEY_CHOICES  = "choices";
    private final static String KEY_ANSWER   = "correct";
    private final static String KEY_EXPLAIN  = "explanation";
    private final static String KEY_IMAGE    = "image";
    /**/

    /**
     * DataBase Questions.
     * If questions read from FireBase, do not read again.
     */
    private static ArrayList<Question> sQuestions = null;

    /**
     * Interface for requesting questions from FireBase.
     * Will return questions via the function onFireBaseResponse.
     */
    public interface IResponse {
        void onFireBaseResponse(ArrayList<Question> questions);
    }

    /**
     * get Questions from FireBase. or Null if failed.
     */
    public static void getQuestions(final IResponse caller, boolean hardReset) {
        if (hardReset) {
            sQuestions = null;
        }
        final Timer timer = new Timer();    // Timer for stop trying to access FireBase
        final int TIMEOUT = 10000;          // 10 seconds timeout. (10k ms).

        // get root node ("hebrew")
        final DatabaseReference databaseReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(ROOT_NAME);

        /* FireBase callback */
        final ValueEventListener dataFetchEventListener = new ValueEventListener() {

            /**
             * This function handles the given objects from FireBase.
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Build questions from firebase objects.
                sQuestions = FireBaseHandler.parseQuestions(dataSnapshot.getValue());
                timer.cancel();
                if (sQuestions == null) {
                    caller.onFireBaseResponse(null);
                } else {
                    caller.onFireBaseResponse(new ArrayList<>(sQuestions));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(QATAG, databaseError.getMessage());
                timer.cancel();
                if (sQuestions == null) {
                    caller.onFireBaseResponse(null);
                } else {
                    caller.onFireBaseResponse(new ArrayList<>(sQuestions));
                }
            }

        };   // ValueEventListener



        // use the declared ValueEventListener.
        if (sQuestions == null || sQuestions.isEmpty()) {
            databaseReference.addListenerForSingleValueEvent(dataFetchEventListener);

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Log.e(QATAG, "FireBaseHandler timed out.");
                    timer.cancel();
                    databaseReference.removeEventListener(dataFetchEventListener);
                    caller.onFireBaseResponse(null);
                }
            };
            timer.schedule(timerTask, TIMEOUT);

        } else {        // sQuestions are not null. return a copy of them.
            caller.onFireBaseResponse(new ArrayList<>(sQuestions));    // shallow copy.
        }
    }   // end of getQuestions

    /**
     * Read and parse FireBase object containing all the question.
     * @param fbObject should be in form of ArrayList< HashMap<String, Object> >
     * @return ArrayList of questions.
     */
    private static ArrayList<Question> parseQuestions(Object fbObject) {
        ArrayList hashmapArrayList = (ArrayList) fbObject;

        // we have already questions collection, don't waste time re-building.
        if (sQuestions != null && sQuestions.size() == hashmapArrayList.size()) {
            Log.i(QATAG, "FireBaseHandler: Returned pre-read questions.");
            return sQuestions;
        }

        /* Parse and Build Questions from FireBase object */
        ArrayList<Question> questions = new ArrayList<>();
        Log.i(QATAG, "FireBaseHandler: Read " + hashmapArrayList.size() + " questions from FB.");
        for (Object object : hashmapArrayList) {
            try {
                HashMap hashmap = (HashMap) object; // each HashMap represents question.
                String qText = (String) hashmap.get(KEY_QUESTION);  // question string

                // choices string
                Collection choicesCollection = ((HashMap) hashmap.get(KEY_CHOICES)).values();
                Set<String> choices = new HashSet<>();
                for (Object c : choicesCollection) {
                    // $ indicates empty string in JS project. (old convention)
                    if (!((String) c).isEmpty() && !c.equals("$"))
                        choices.add((String) c);
                }
                String correct = (String) hashmap.get(KEY_ANSWER);      // correct answer
                String explanation = (String) hashmap.get(KEY_EXPLAIN); // explanation string.
                String imageUrl = (String) hashmap.get(KEY_IMAGE);      // image url string.
                Question question = new Question(qText, choices, correct);
                question.setExplanation(explanation);
                question.setImageUrl(imageUrl);
                questions.add(question);
            } catch (Exception e) {
                Log.e(QATAG, "FireBaseHandler::parseAllQuestions " + e.getMessage());
            }
        }   // foreach
        sQuestions = questions;
        return sQuestions;
    }

    /************ FireBase Messages *********************/

    private static ISubscribeHandler sSubscribeHandler;

    public interface ISubscribeHandler {
        void reloadFireBaseQuestionsClicked();   // reload questions from FireBase.
        void displayMessageOnUiThread(final String message,
                                      final DialogInterface.OnClickListener clickListener);
    }

    /**
     * Subscribe app to Topic: db_updates
     */
    public static void subscribeToUpdates(@NonNull ISubscribeHandler subscribeHandler) {
        final String TOPIC = "db_updates";
        sSubscribeHandler = subscribeHandler;

        FirebaseMessaging.getInstance()
                .subscribeToTopic(TOPIC)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i(QATAG, "Successfully subscribed to FireBase updates.");
                        } else {
                            Log.e(QATAG, "Failed subscribing to FireBase updates.");
                        }
                    }
                });
    }

    /**
     * Foreground message handler
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.i(QATAG, "onMessageReceived::From: " + remoteMessage.getFrom());

        final String message = getResources().getString(R.string.q_updated_msg);
        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int res) {
                if (res == DialogInterface.BUTTON_POSITIVE ) {
                    // yes button clicked. Reload all questions.
                    sSubscribeHandler.reloadFireBaseQuestionsClicked();
                }
            }
        };
        sSubscribeHandler.displayMessageOnUiThread(message, dialogClickListener);
    }

}
