package com.androidclase.gamecenter.settings;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidclase.gamecenter.Constants;
import com.androidclase.gamecenter.R;

public class MainSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_settings);

        setupFullscreen();

        TextView commentSettings = findViewById(R.id.tv_settings_welcome);
        String recoveredUsername = getIntent().getStringExtra(Constants.USERNAME);
        commentSettings.setText(getString(R.string.welcome_username) + " " + recoveredUsername + "!");
    }

    private void setupFullscreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }
}