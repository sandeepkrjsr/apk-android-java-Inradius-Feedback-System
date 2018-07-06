package com.bleedcode.inradius.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;

import com.bleedcode.inradius.R;

/**
 * Created by 1505560 on 07-Jan-18.
 */

public class Activity_Splash extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                Intent intent = new Intent(Activity_Splash.this, Activity_Login.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }
}
