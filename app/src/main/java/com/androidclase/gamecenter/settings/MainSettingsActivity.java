package com.androidclase.gamecenter.settings;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidclase.gamecenter.Constants;
import com.androidclase.gamecenter.R;
import com.androidclase.gamecenter.db.DbScores;

import java.util.ArrayList;

public class MainSettingsActivity extends AppCompatActivity {

    private RecyclerView scoresList;
    private ArrayList<Scores> scoresArrayList;
    private CheckBox ch2048;
    private CheckBox chSenku;
    private Spinner sortSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_main_settings);

        TextView commentSettings = findViewById(R.id.tv_settings_welcome);
        String recoveredUsername = getIntent().getStringExtra(Constants.USERNAME);
        commentSettings.setText(getString(R.string.welcome_username) + " " + recoveredUsername + "!");

        ch2048 = findViewById(R.id.ch_2048);
        chSenku = findViewById(R.id.ch_senku);
        sortSpinner = findViewById(R.id.sort_spinner);


        scoresList = findViewById(R.id.scores_list);
        scoresList.setLayoutManager(new LinearLayoutManager(this));

        DbScores dbScores = new DbScores(MainSettingsActivity.this);

        scoresArrayList = new ArrayList<>();

        ScoresListAdapter adapter = new ScoresListAdapter(dbScores.showScores2048());
        scoresList.setAdapter(adapter);

        // TODO: 31/05/2022 change color of gameText depending on game 
//        TextView gameOfScore = findViewById(R.id.tv_game);
//        if (gameOfScore.getText().equals("Senku")){
//            gameOfScore.setTextColor(Color.BLUE);
//        }else{
//            gameOfScore.setTextColor(Color.RED);
//        }
    }


    private void setupFullscreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }
}