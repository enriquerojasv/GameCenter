package com.androidclase.gamecenter.settings;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

    private RadioGroup rgSelectionGame;

    // TODO: 31/05/2022 Extract Constants
    private String gameSelected = Constants.NAME_BOTH;
    private String sortType = Constants.NAME_DESC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_main_settings);

        TextView commentSettings = findViewById(R.id.tv_settings_welcome);
        String recoveredUsername = getIntent().getStringExtra(Constants.USERNAME);
        commentSettings.setText(getString(R.string.welcome_username) + " " + recoveredUsername + "!");


        rb2048 = findViewById(R.id.rb_2048);
        rbSenku = findViewById(R.id.rb_senku);
        rbBoth = findViewById(R.id.rb_both);
        Button btShow = findViewById(R.id.bt_show);

        RadioButton rbAsc = findViewById(R.id.rb_asc);
        rgSelectionGame = findViewById(R.id.rg_selection_game);


        scoresList = findViewById(R.id.scores_list);
        scoresList.setLayoutManager(new LinearLayoutManager(this));

        btShow.setOnClickListener(view -> onBtShowClick(view));


    }


    private void setupFullscreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }

    public void onSelectGameClicked(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rb_2048:
                if (checked)
                    gameSelected = Constants.NAME_2048;
                break;
            case R.id.rb_senku:
                if (checked)
                    gameSelected = Constants.NAME_SENKU;
                break;
            case R.id.rb_both:
                if (checked)
                    gameSelected = Constants.NAME_BOTH;
                break;
        }
    }

    public void onSortTypeClicked(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rb_asc:
                if (checked)
                    sortType = Constants.NAME_ASC;
                break;
            case R.id.rb_desc:
                if (checked)
                    sortType = Constants.NAME_DESC;
                break;
        }
    }

    public void onBtShowClick(View view) {

        DbScores dbScores = new DbScores(MainSettingsActivity.this);
        ScoresListAdapter adapter = new ScoresListAdapter(dbScores.showScores(sortType));
        scoresList.setAdapter(adapter);

        if (gameSelected.equals(Constants.NAME_SENKU)) {
            adapter = new ScoresListAdapter(dbScores.showScoresSenku(sortType));
        } else if (gameSelected.equals(Constants.NAME_2048)) {
            adapter = new ScoresListAdapter(dbScores.showScores2048(sortType));
        } else if (gameSelected.equals(Constants.NAME_BOTH)) {
            adapter = new ScoresListAdapter(dbScores.showScores(sortType));
        }

        scoresList.setAdapter(adapter);

        Toast.makeText(this, gameSelected + "" + sortType, Toast.LENGTH_SHORT).show();


    }


//    private String getRbGame() {
//        rgSelectionGame.getCheckedRadioButtonId();
//        Toast.makeText(this, rgSelectionGame.getCheckedRadioButtonId() + "", Toast.LENGTH_SHORT).show();
//
//        DbScores dbScores = new DbScores(MainSettingsActivity.this);
//
////        scoresArrayList = new ArrayList<>();
//
//        ScoresListAdapter adapter = new ScoresListAdapter(dbScores.showScores2048());
//        scoresList.setAdapter(adapter);
//        // Is the button now checked?
//        boolean checked = ((RadioButton) view).isChecked();
//
//        switch (view.getId()) {
//            case R.id.rb_2048:
//                if (checked)
//                    adapter = new ScoresListAdapter(dbScores.showScores2048());
//                scoresList.setAdapter(adapter);
//                break;
//            case R.id.rb_senku:
//                if (checked)
//                    adapter = new ScoresListAdapter(dbScores.showScoresSenkuDesc());
//                scoresList.setAdapter(adapter);
//                break;
//            case R.id.rb_both:
//                if (checked)
//                    adapter = new ScoresListAdapter(dbScores.showScoresDesc());
//                scoresList.setAdapter(adapter);
//                break;
//        }
//
//    }
}