package com.rkcodesolution.quizapp;

import android.app.Dialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;

import java.util.ArrayList;

import static com.rkcodesolution.quizapp.QuizApp.QATAG;


public class QuestionListActivity extends ListActivity {
    private QuestionAdapter mAdapter;
    public static final String LIST_KEY = "qList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Question> questions = getIntent().getParcelableArrayListExtra(LIST_KEY);

        mAdapter = new QuestionAdapter(this, questions);
        setListAdapter(mAdapter);

        // enable scroll bars.
        getListView().setVerticalScrollBarEnabled(true);
        getListView().setVerticalScrollbarPosition(View.SCROLLBAR_POSITION_LEFT);

        this.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            /**
             * On item click, if Question has image, display it in an zoomable image dialog.
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Dialog imageDialog = new Dialog(view.getContext());
                    String url = mAdapter.getImageUrl(position);    // position is the index of question in question ArrayList.
                    if (!url.isEmpty()) {
                        // build the zoomage dialog.
                        Window window = imageDialog.getWindow();
                        if (window != null ) {
                            window.requestFeature(Window.FEATURE_NO_TITLE);
                        }
                        imageDialog.setContentView(getLayoutInflater().inflate(R.layout.zoomage, null));
                        ImageView imgView = imageDialog.findViewById(R.id.zoomage);

                        // load and display the image.
                        QuizApp.sImageLoader.displayImage(url, imgView);
                        imageDialog.show();
                    }
                } catch (Exception e) {
                    Log.e(QATAG, e.getMessage());
                }
            }
        });
    }


}
