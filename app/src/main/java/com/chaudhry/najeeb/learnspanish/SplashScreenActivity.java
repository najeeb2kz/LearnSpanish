package com.chaudhry.najeeb.learnspanish;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;


public class SplashScreenActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;  //2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView splashScreenImageViewId = (ImageView) findViewById(R.id.splashScreenImageViewId);
        TextView splashScreenLearnSpanishTextViewId = (TextView) findViewById(R.id.splashScreenLearnSpanishTextViewId);
        TextView splashScreenDeveloperInfoTextVeiwId = (TextView) findViewById(R.id.splashScreenDeveloperInfoTextVeiwId);

        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);  //appear
        fadeIn.setStartOffset(500);    //Animation will start after this time
        fadeIn.setDuration(1000);       //Animation will last for this time
        fadeIn.setFillAfter(true);     //This will keep TextView appeared

        AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);  //disappear
        fadeOut.setStartOffset(1000);   //Animation will start after this time
        fadeOut.setDuration(1000);      //Animation will last for this time
        fadeOut.setFillAfter(true);     //This will keep TextView disappeared

        assert splashScreenImageViewId != null;
        splashScreenImageViewId.startAnimation(fadeIn);
        splashScreenImageViewId.startAnimation(fadeOut);

        assert splashScreenLearnSpanishTextViewId != null;
        splashScreenLearnSpanishTextViewId.startAnimation(fadeIn);
        splashScreenLearnSpanishTextViewId.startAnimation(fadeOut);

        assert splashScreenDeveloperInfoTextVeiwId != null;
        splashScreenDeveloperInfoTextVeiwId.startAnimation(fadeIn);
        splashScreenDeveloperInfoTextVeiwId.startAnimation(fadeOut);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}

