package com.androidclase.gamecenter.settings;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private RadioButton rb2048;
    private RadioButton rbSenku;
    private RadioButton rbBoth;
    private Spinner sortSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_main_settings);

        TextView commentSettings = findViewById(R.id.tv_settings_welcome);
        String recoveredUsername = getIntent().getStringExtra(Constants.USERNAME);
        commentSettings.setText(getString(R.string.welcome_username) + " " + recoveredUsername + "!");

        RadioGroup rgSelectionGame = findViewById(R.id.rg_selection_game);
        rb2048 = findViewById(R.id.rb_2048);
        rbSenku = findViewById(R.id.rb_senku);
        rbBoth = findViewById(R.id.rb_both);
        sortSpinner = findViewById(R.id.sort_spinner);


        scoresList = findViewById(R.id.scores_list);
        scoresList.setLayoutManager(new LinearLayoutManager(this));


    }


    private void setupFullscreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }

    public void onSelectGameClicked(View view) {

        DbScores dbScores = new DbScores(MainSettingsActivity.this);

//        scoresArrayList = new ArrayList<>();

        ScoresListAdapter adapter = new ScoresListAdapter(dbScores.showScores2048());
        scoresList.setAdapter(adapter);
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rb_2048:
                if (checked)
                    adapter = new ScoresListAdapter(dbScores.showScores2048());
                scoresList.setAdapter(adapter);
                break;
            case R.id.rb_senku:
                if (checked)
                    adapter = new ScoresListAdapter(dbScores.showScoresSenku());
                scoresList.setAdapter(adapter);
                break;
            case R.id.rb_both:
                if (checked)
                    adapter = new ScoresListAdapter(dbScores.showScores());
                scoresList.setAdapter(adapter);
                break;
        }
    }
}