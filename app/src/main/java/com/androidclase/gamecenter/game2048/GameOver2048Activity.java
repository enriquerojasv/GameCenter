package com.androidclase.gamecenter.game2048;


import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidclase.gamecenter.Constants;
import com.androidclase.gamecenter.R;
import com.androidclase.gamecenter.db.DbScores;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class GameOver2048Activity extends AppCompatActivity {

    private String thisGame = "2048";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupFullscreen();

        setContentView(R.layout.activity_g2048_gameover);
        TextView title = findViewById(R.id.game_over_title);
        TextView scoreText = findViewById(R.id.score_game_over);
        TextView moveText = findViewById(R.id.moves_game_over);
        TextView timeText = findViewById(R.id.time_game_over);

        int minimumMoves = 50;
        int minimumTime = 30000;

        String recoveredUsername = getIntent().getStringExtra(Constants.USERNAME);
        long score = getIntent().getExtras().getInt("score");
        int moves = getIntent().getExtras().getInt("moves");
        double bonus = getIntent().getExtras().getDouble("bonus");
        long ms = getIntent().getExtras().getLong("ms");

        if (score == 0) {
            score = ((minimumTime / ms) * (minimumMoves / moves)) * 100;
        }
        int finalScore = (int) (score * bonus);

        scoreText.setText("SCORE: " + finalScore);
        moveText.setText("MOVES: " + moves);
        title.setText(getIntent().getExtras().getString("title"));
        timeText.setText(String.format(Locale.ENGLISH, "TIME: %02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(ms),
                TimeUnit.MILLISECONDS.toSeconds(ms) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms))
        ));

        getWindow().setBackgroundDrawableResource(R.drawable.g2048_transparent_bg);

        //writing db
        DbScores dbScores = new DbScores(GameOver2048Activity.this);
        long id = dbScores.insertScore(recoveredUsername, thisGame, finalScore);

        if (id > 0) {
            Toast.makeText(GameOver2048Activity.this, "Score saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(GameOver2048Activity.this, "Error. Score not saved", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupFullscreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }
}
