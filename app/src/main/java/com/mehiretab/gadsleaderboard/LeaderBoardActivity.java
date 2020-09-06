package com.mehiretab.gadsleaderboard;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class LeaderBoardActivity extends AppCompatActivity {

    private boolean isFirstBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        Toolbar toolbar = findViewById(R.id.leader_board_toolbar);
        setSupportActionBar(toolbar);

        init();
    }

    private void init() {
        MaterialButton submitBtn = findViewById(R.id.leader_board_submit_btn);
        submitBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, ProjectSubmissionActivity.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(this,
                    android.R.anim.fade_in, android.R.anim.fade_out);
            startActivity(intent, options.toBundle());
        });

        TabLayout tabLayout = findViewById(R.id.leader_board_tabLayout);
        ViewPager2 tabViewPager = findViewById(R.id.leader_board_viewpager);
        tabViewPager.setUserInputEnabled(true);

        LearningLeaderFragment learningLeaderFragment = LearningLeaderFragment.newInstance();
        SkillIqLeaderFragment skillIqLeaderFragment = SkillIqLeaderFragment.newInstance();

        LeaderBoardTabAdapter tabAdapter = new LeaderBoardTabAdapter(this);
        tabAdapter.add(learningLeaderFragment);
        tabAdapter.add(skillIqLeaderFragment);
        tabViewPager.setAdapter(tabAdapter);

        new TabLayoutMediator(tabLayout, tabViewPager, (tab, position) -> {
            switch (position) {
                case 2:
                    tab.setText(R.string.skill_iq_leaders);
                    break;
                case 1:
                default:
                    tab.setText(R.string.learning_leaders);
            }
        }).attach();
    }

    @Override
    public void onBackPressed() {
        if (this.isFirstBackPressed)
            super.onBackPressed();
        else {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

            this.isFirstBackPressed = true;
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(() -> this.isFirstBackPressed = false, 2500);
        }
    }
}