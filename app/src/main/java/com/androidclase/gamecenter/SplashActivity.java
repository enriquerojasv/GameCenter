package com.androidclase.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.androidclase.gamecenter.menu.SignUpActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        View titleView = findViewById(R.id.title_view);
        View presentsView = findViewById(R.id.presents_view);
        View logoView = findViewById(R.id.logo_view);
        View gamesView = findViewById(R.id.games_view);
        View versionView = findViewById(R.id.version_view);

        Animation fadeInTitle = AnimationUtils.loadAnimation(this, R.anim.splash_fade_in_title);
        Animation fadeInSubtitle = AnimationUtils.loadAnimation(this, R.anim.splash_fade_in_subtitle);
        Animation logoAnim = AnimationUtils.loadAnimation(this, R.anim.splash_logo_animation);

        titleView.startAnimation(fadeInTitle);
        presentsView.startAnimation(fadeInTitle);
        logoView.startAnimation(logoAnim);
        gamesView.startAnimation(fadeInSubtitle);
        versionView.startAnimation(fadeInTitle);

        fadeInTitle.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent signUp = new Intent(SplashActivity.this, SignUpActivity.class);
                startActivity(signUp);
                overridePendingTransition(0, R.anim.splash_fade_out);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}