package com.androidclase.gamecenter.game2048;


import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidclase.gamecenter.R;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class GameOver2048Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_g2048_gameover);

        TextView title = findViewById(R.id.game_over_title);
        TextView scoreText = findViewById(R.id.score_game_over);
        TextView moveText = findViewById(R.id.moves_game_over);
        TextView timeText = findViewById(R.id.time_game_over);

        int minimumMoves = 50;
        int minimumTime = 30000;

        long score = getIntent().getExtras().getInt("score");
        int moves = getIntent().getExtras().getInt("moves");
        double bonus = getIntent().getExtras().getDouble("bonus");
        long ms = getIntent().getExtras().getLong("ms");

        if (score == 0) {
            score = ((minimumTime / ms) * (minimumMoves / moves)) * 100;
        }
        int final_score = (int) (score * bonus);

        scoreText.setText("SCORE: " + final_score);
        moveText.setText("MOVES: " + moves);
        title.setText(getIntent().getExtras().getString("title"));
        timeText.setText(String.format(Locale.ENGLISH, "TIME: %02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(ms),
                TimeUnit.MILLISECONDS.toSeconds(ms) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms))
        ));

        getWindow().setBackgroundDrawableResource(R.drawable.g2048_transparent_bg);
    }
}
