package com.androidclase.gamecenter.settings;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidclase.gamecenter.Constants;
import com.androidclase.gamecenter.R;
import com.androidclase.gamecenter.db.DbScores;

public class MainSettingsActivity extends AppCompatActivity {

    EditText etUser, etGame, etScore;
    Button btSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_main_settings);

        etUser = findViewById(R.id.et_username);
        etGame = findViewById(R.id.et_game);
        etScore = findViewById(R.id.et_score);
        btSave = findViewById(R.id.bt_save);
        TextView commentSettings = findViewById(R.id.tv_settings_welcome);

        String recoveredUsername = getIntent().getStringExtra(Constants.USERNAME);
        commentSettings.setText(getString(R.string.welcome_username) + " " + recoveredUsername + "!");

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbScores dbScores = new DbScores(MainSettingsActivity.this);
                dbScores.insertScore(etUser.getText().toString(), etGame.getText().toString(), etScore.getText().toString());
            }
        });
    }

    private void setupFullscreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }
}