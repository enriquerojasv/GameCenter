package com.androidclase.gamecenter.senku;

import static com.androidclase.gamecenter.Constants.GRID_1;
import static com.androidclase.gamecenter.Constants.GRID_2;
import static com.androidclase.gamecenter.Constants.GRID_3;
import static com.androidclase.gamecenter.Constants.GRID_4;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidclase.gamecenter.Constants;
import com.androidclase.gamecenter.R;

import java.util.ArrayList;
import java.util.Random;

public class GameSenkuActivity extends AppCompatActivity {

    private GridView gridViewBoard;
    private TextView moveCounter;
    private int[][] selectedGrid = GRID_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupFullscreen();

        setContentView(R.layout.activity_game_senku);
        gridViewBoard = findViewById(R.id.grid_view_board);
        moveCounter = findViewById(R.id.move_ly_view);

        TextView commentSenku = findViewById(R.id.tv_senku_welcome);
        String recoveredUsername = getIntent().getStringExtra(Constants.USERNAME);
        commentSenku.setText(getString(R.string.senku_comment) + " " + recoveredUsername + "!");

        ArrayList<SenkuBoxModel> boxModelArrayList = new ArrayList<SenkuBoxModel>();

        gridSelector();
        gridCreator(boxModelArrayList);

        Chronometer timer = (Chronometer) findViewById(R.id.timer);

        SenkuBoxAdapter boxAdapter = new SenkuBoxAdapter(this, boxModelArrayList,
                selectedGrid, moveCounter, timer, recoveredUsername);
        gridViewBoard.setAdapter(boxAdapter);
    }

    // reads and creates the grid with the corresponding pattern
    private void gridCreator(ArrayList<SenkuBoxModel> boxModelArrayList) {
        gridViewBoard.setNumColumns(selectedGrid.length);

        for (int i = 0; i < selectedGrid.length; i++) {
            for (int j = 0; j < selectedGrid.length; j++) {

                switch (selectedGrid[i][j]) {
                    case 0:
                        boxModelArrayList.add(new SenkuBoxModel(0, 0,
                                selectedGrid.length, i, j));
                        break;
                    case 1:
                        boxModelArrayList.add(new SenkuBoxModel(1, 1,
                                selectedGrid.length, i, j));
                        break;
                    case 2:
                        boxModelArrayList.add(new SenkuBoxModel(1, 0,
                                selectedGrid.length, i, j));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    // randomly selects a grid
    private void gridSelector() {
        Random rand = new Random();
        int n = rand.nextInt(3);

        switch (n) {
            case 0:
                selectedGrid = GRID_1;
                break;
            case 1:
                selectedGrid = GRID_2;
                break;
            case 2:
                selectedGrid = GRID_3;
                break;
            case 3:
                selectedGrid = GRID_4;
                break;
            default:
                break;
        }
    }

    private void setupFullscreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }
}