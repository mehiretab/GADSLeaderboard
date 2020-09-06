package com.mehiretab.gadsleaderboard;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        enableStrictMode();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            Intent leaderBoardIntent = new Intent(this, LeaderBoardActivity.class);
            leaderBoardIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(this,
                    android.R.anim.fade_in, android.R.anim.fade_out);
            startActivity(leaderBoardIntent, options.toBundle());
            finish();
        }, 2000);
    }

    private void enableStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .detectAll().penaltyLog().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

}