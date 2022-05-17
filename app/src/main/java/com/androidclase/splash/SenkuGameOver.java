package com.androidclase.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SenkuGameOver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_senku_gameover);

        TextView title = findViewById(R.id.gameOverTitle);
        TextView scoreText = findViewById(R.id.scoreGameOver);
        TextView moveText = findViewById(R.id.movesGameOver);
        TextView timeText = findViewById(R.id.timeGameOver);

        int minimumMoves = 50;
        int minimumTime = 30000;

        int moves = getIntent().getExtras().getInt("moves");
        double bonus = getIntent().getExtras().getDouble("bonus");
        long ms = getIntent().getExtras().getLong("ms");

        long score = ((minimumTime / ms) * (minimumMoves / moves)) * 100;

        int final_score = (int) (score * bonus);

        scoreText.setText("SCORE: " + final_score);
        moveText.setText("MOVES: " + moves);
        title.setText(getIntent().getExtras().getString("title"));
        timeText.setText(String.format(Locale.ENGLISH,"TIME: %02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(ms),
                TimeUnit.MILLISECONDS.toSeconds(ms) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms))
        ));


        /*
        ANOTHER WAY OF DOING A POPUP

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.95), (int) (height * 0.9));
        */

        getWindow().setBackgroundDrawableResource(R.drawable.senku_transparent_bg);
    }
}
