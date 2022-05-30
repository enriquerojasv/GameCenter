package com.androidclase.gamecenter.senku;

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

public class SenkuGameOverActivity extends AppCompatActivity {

    private String thisGame = "Senku";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupFullscreen();

        setContentView(R.layout.activity_senku_gameover);
        TextView title = findViewById(R.id.game_over_title);
        TextView scoreText = findViewById(R.id.score_game_over);
        TextView moveText = findViewById(R.id.moves_game_over);
        TextView timeText = findViewById(R.id.time_game_over);

        String recoveredUsername = getIntent().getStringExtra(Constants.USERNAME);
        int moves = getIntent().getExtras().getInt("moves");
        double bonus = getIntent().getExtras().getDouble("bonus");
        long ms = getIntent().getExtras().getLong("ms");

        int finalScore = (int) ((int) (moves * 10) * bonus);

        scoreText.setText("SCORE: " + finalScore);
        moveText.setText("MOVES: " + moves);
        title.setText(getIntent().getExtras().getString("title"));
        timeText.setText(String.format(Locale.ENGLISH, "TIME: %02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(ms),
                TimeUnit.MILLISECONDS.toSeconds(ms) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms))
        ));

        getWindow().setBackgroundDrawableResource(R.drawable.senku_transparent_bg);

        //writing db
        DbScores dbScores = new DbScores(SenkuGameOverActivity.this);
        long id = dbScores.insertScore(recoveredUsername, thisGame, finalScore);

        if (id > 0) {
            Toast.makeText(SenkuGameOverActivity.this, "Score saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(SenkuGameOverActivity.this, "Error. Score not saved", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupFullscreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }
}
