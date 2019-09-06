package com.rkcodesolution.quizapp;
import android.app.Application;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class QuizApp extends Application {
    public static final String QATAG = "QATAG";         // Tag for debugging.
    public static ImageLoader sImageLoader;             // for loading images.

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize ImageLoader
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(this)
                .build();
        sImageLoader = ImageLoader.getInstance();
        sImageLoader.init(config);
    }
}
