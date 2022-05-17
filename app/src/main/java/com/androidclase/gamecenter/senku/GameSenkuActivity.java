package com.androidclase.gamecenter.senku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.TextView;

import com.androidclase.gamecenter.R;

import java.util.ArrayList;
import java.util.Random;

public class GameSenkuActivity extends AppCompatActivity {

    public static final int[][] GRID_1 = {
            {0, 0, 2, 2, 2, 0, 0},
            {0, 0, 2, 1, 2, 0, 0},
            {2, 2, 2, 1, 2, 2, 2},
            {2, 1, 1, 1, 1, 1, 2},
            {2, 2, 2, 1, 2, 2, 2},
            {0, 0, 2, 1, 2, 0, 0},
            {0, 0, 2, 2, 2, 0, 0}
    };
    public static final int[][] GRID_2 = {
            {0, 0, 2, 1, 1, 0, 0},
            {0, 1, 1, 1, 1, 1, 0},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {0, 1, 1, 1, 1, 1, 0},
            {0, 0, 1, 1, 1, 0, 0}
    };
    public static final int[][] GRID_3 = {
            {0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 1, 1, 1, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 2, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 1, 1, 1, 0, 0, 0}
    };
    public static final int[][] GRID_4 = {
            {0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 2, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 0, 0, 0}
    };
    public static final int[][] GRID_5 = {

            {0, 0, 0, 2, 2, 2, 0, 0, 0},
            {0, 0, 0, 2, 2, 2, 0, 0, 0},
            {0, 0, 0, 2, 2, 2, 0, 0, 0},
            {2, 2, 2, 2, 2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2, 2, 2, 2, 2},
            {2, 1, 1, 2, 2, 2, 2, 2, 2},
            {0, 0, 0, 2, 2, 2, 0, 0, 0},
            {0, 0, 0, 2, 2, 2, 0, 0, 0},
            {0, 0, 0, 2, 2, 2, 0, 0, 0}
    };

    private GridView gridViewBoard;
    private TextView moveCounter;
    private int[][] selectedGrid = GRID_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_game_senku);

        gridViewBoard = findViewById(R.id.grid_view_board);
        moveCounter = findViewById(R.id.move_counter);

        ArrayList<SenkuBoxModel> boxModelArrayList = new ArrayList<SenkuBoxModel>();

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


        Chronometer timer = (Chronometer) findViewById(R.id.timer);

        SenkuBoxAdapter boxAdapter = new SenkuBoxAdapter(this, boxModelArrayList,
                selectedGrid, moveCounter, timer);
        gridViewBoard.setAdapter(boxAdapter);

    }
}