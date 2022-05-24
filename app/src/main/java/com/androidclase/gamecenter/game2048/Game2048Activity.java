package com.androidclase.gamecenter.game2048;

import static com.androidclase.gamecenter.Constants.BOARD_CELLS;
import static com.androidclase.gamecenter.Constants.BOARD_SIZE;
import static com.androidclase.gamecenter.Constants.CELL_BG;
import static com.androidclase.gamecenter.Constants.CELL_VALUES;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import com.androidclase.gamecenter.Constants;
import com.androidclase.gamecenter.R;

import java.util.Random;

public class Game2048Activity extends AppCompatActivity {


    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private static TextView moveLyView;
    private static TextView scoreLyView;
    private static int movesValue = 1;
    private static TextView bestScoreLyView;
    private static Game2048Activity context;
    private static String lastCombined = "";
    private static int impossibleMoves = 0;
    private static int bestScoreValue;
    private static Chronometer timer;
    private static Animation pulse;
    private static Animation spawn;
    private static int scoreValue = 0;
    private GestureDetectorCompat detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: 18/05/2022 remove context variable
        context = this;

        setupFullscreen();
        setContentView(R.layout.activity_game_2048);

        TextView comment2048 = findViewById(R.id.comment);
        String recoveredUsername = getIntent().getStringExtra(Constants.USERNAME);
        comment2048.setText(recoveredUsername + "! " + getString(R.string.phrase_2048));

        setupSharedPreferences();
        setupAnimations();
        initScoreViews();
        initBestScore();
        createUI();
        generateCell(Constants.STARTING_CELLS);
        setupTimer();

        detector = new GestureDetectorCompat(this, new MyGestureListener());
    }

    //Check if it has to update the best score
    private static void bestScoreUpdater() {
        if (scoreValue > bestScoreValue) {
            bestScoreValue = scoreValue;

            editor.putInt("best_score", scoreValue);
            editor.commit();

            bestScoreLyView.setText(String.valueOf(bestScoreValue));
        }
    }

    //Updates current score
    private static void scoreUpdater(String newValue) {
        scoreValue = Integer.parseInt(scoreLyView.getText().toString()) +
                Integer.parseInt(newValue);

        scoreLyView.setText(String.valueOf(scoreValue));
    }

    private void initBestScore() {
        // TODO: 19/05/2022 change best_score to constant
        bestScoreValue = sharedPreferences.getInt("best_score", 0);
        bestScoreLyView.setText(String.valueOf(bestScoreValue));
    }

    private void setupTimer() {
        timer = (Chronometer) findViewById(R.id.timer);
        timer.setBase(SystemClock.elapsedRealtime());

        timer.start();
    }

    private void createUI() {
        BOARD_CELLS[0][0] = findViewById(R.id.cell_0);
        BOARD_CELLS[0][1] = findViewById(R.id.cell_1);
        BOARD_CELLS[0][2] = findViewById(R.id.cell_2);
        BOARD_CELLS[0][3] = findViewById(R.id.cell_3);

        BOARD_CELLS[1][0] = findViewById(R.id.cell_4);
        BOARD_CELLS[1][1] = findViewById(R.id.cell_5);
        BOARD_CELLS[1][2] = findViewById(R.id.cell_6);
        BOARD_CELLS[1][3] = findViewById(R.id.cell_7);

        BOARD_CELLS[2][0] = findViewById(R.id.cell_8);
        BOARD_CELLS[2][1] = findViewById(R.id.cell_9);
        BOARD_CELLS[2][2] = findViewById(R.id.cell_10);
        BOARD_CELLS[2][3] = findViewById(R.id.cell_11);

        BOARD_CELLS[3][0] = findViewById(R.id.cell_12);
        BOARD_CELLS[3][1] = findViewById(R.id.cell_13);
        BOARD_CELLS[3][2] = findViewById(R.id.cell_14);
        BOARD_CELLS[3][3] = findViewById(R.id.cell_15);
    }

    private void initScoreViews() {
        // TODO: 18/05/2022 standardize id naming with variable naming
        bestScoreLyView = findViewById(R.id.best_value);
        scoreLyView = findViewById(R.id.score_value);
        moveLyView = findViewById(R.id.move_counter);
    }

    private void setupAnimations() {
        pulse = AnimationUtils.loadAnimation(this, R.anim.g2048_pulse);
        spawn = AnimationUtils.loadAnimation(this, R.anim.g2048_spawn);
    }

    private void setupSharedPreferences() {
        // TODO: 18/05/2022 review shared-preferences official setup. possible high score solution
        sharedPreferences = getApplicationContext().getSharedPreferences("g2048Records", MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void setupFullscreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }

    private static void checkPossibleMovements() {

        int movesUp = 0;
        int movesLeft = 0;
        int movesRight = 0;
        int movesDown = 0;

        int movesTotal = 4;

        //Checks all impossible moves Up and Left. If there are 48 impossible moves, you can move in
        //that direction
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                for (int k = 0; k < BOARD_SIZE; k++) {
                    impossibleMoves = 0;
                    checkMovement(i, j, -1, 0, true);
                    movesUp += impossibleMoves;
                    impossibleMoves = 0;
                    checkMovement(i, j, 0, -1, true);
                    movesLeft += impossibleMoves;
                }
            }
        }

        //Checks all impossible moves Down and Right. If there are 48 impossible moves, you can move
        //in that direction
        for (int i = BOARD_SIZE - 1; i >= 0; i--) {
            for (int j = BOARD_SIZE - 1; j >= 0; j--) {
                for (int k = BOARD_SIZE - 1; k >= 0; k--) {
                    impossibleMoves = 0;
                    checkMovement(i, j, 1, 0, true);
                    movesDown += impossibleMoves;
                    impossibleMoves = 0;
                    checkMovement(i, j, 0, 1, true);
                    movesRight += impossibleMoves;
                }
            }
        }

        if (movesDown == 48) movesTotal -= 1;
        if (movesRight == 48) movesTotal -= 1;
        if (movesUp == 48) movesTotal -= 1;
        if (movesLeft == 48) movesTotal -= 1;

        winCheck(movesTotal, false);
    }

    private static void winCheck(int movesTotal, boolean win) {

        if (movesTotal == 0 || win) {
            Intent intent = new Intent(context, GameOver2048Activity.class);

            if (movesTotal == 0) {
                intent.putExtra("title", "YOU LOSE");
                intent.putExtra("bonus", 1.0);
            } else {
                intent.putExtra("title", "YOU WIN");
                intent.putExtra("bonus", 1.5);
            }

            intent.putExtra("score", scoreValue);
            intent.putExtra("moves", movesValue);

            long elapsedMillis = SystemClock.elapsedRealtime() - timer.getBase();
            intent.putExtra("ms", elapsedMillis);

            context.startActivity(intent);
        }
    }

    //fills a new cell if possible
    private static void generateCell(int cellQuantity) {

        int emptyCells = getEmptyCells();

        if (emptyCells >= 1) {
            int counter = 0;
            Random r = new Random();

            while (counter < cellQuantity) {
                int ran1 = r.nextInt(BOARD_SIZE);
                int ran2 = r.nextInt(BOARD_SIZE);

                if (BOARD_CELLS[ran1][ran2].getText().toString().isEmpty()) {
                    int ran3 = r.nextInt(2) + 1;

                    BOARD_CELLS[ran1][ran2].setText(CELL_VALUES[ran3]);
                    BOARD_CELLS[ran1][ran2].setBackgroundResource(CELL_BG[ran3]);
                    counter += 1;

                    BOARD_CELLS[ran1][ran2].startAnimation(spawn);
                }
            }
        } else {
            checkPossibleMovements();
        }
    }

    //returns the number of empty cells
    private static int getEmptyCells() {
        int emptyCells = 0;

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (BOARD_CELLS[i][j].getText() == "") {
                    emptyCells += 1;
                }
            }
        }
        return emptyCells;
    }

    private static int getIndex(String text) {
        for (int i = 0; i < CELL_VALUES.length; i++) {
            if (CELL_VALUES[i].contentEquals(text)) {
                return i;
            }
        }
        return 0;
    }

    private static boolean checkBounds(int x, int y, int vertical, int horizontal) {

        //left
        if (horizontal == -1) {
            return y < 0;
        }
        //right
        else if (horizontal == 1) {
            return y >= BOARD_SIZE;
        }
        //up
        else if (vertical == -1) {
            return x < 0;
        }
        //down
        else if (vertical == 1) {
            return x >= BOARD_SIZE;
        }
        return false;
    }

    private static void checkMovement(int x, int y, int vertical, int horizontal, boolean check) {
        boolean canMove = true;
        int newX = x;
        int newY = y;
        String newValue;

        String cellText = (String) BOARD_CELLS[x][y].getText();

        if (cellText.equals("")) canMove = false;

        while (canMove) {
            newX += vertical;
            newY += horizontal;

            //Check if the next cell is out of bounds
            if (checkBounds(newX, newY, vertical, horizontal)) break;

            String newText = (String) BOARD_CELLS[newX][newY].getText();

            if (newText.equals("")) {
                if (!check) {
                    BOARD_CELLS[newX][newY].setText(CELL_VALUES[getIndex(cellText)]);
                    BOARD_CELLS[newX][newY].setBackgroundResource(CELL_BG[getIndex(cellText)]);

                    BOARD_CELLS[newX - vertical][newY - horizontal].setText(CELL_VALUES[0]);
                    BOARD_CELLS[newX - vertical][newY - horizontal].setBackgroundResource(CELL_BG[0]);
                }
                impossibleMoves = 0;

            } else if (newText.equals(cellText) && !cellText.equals(lastCombined)) {
                if (!check) {

                    newValue = CELL_VALUES[getIndex(cellText) + 1];

                    BOARD_CELLS[newX][newY].setText(newValue);
                    BOARD_CELLS[newX][newY].setBackgroundResource(CELL_BG[getIndex(cellText) + 1]);

                    BOARD_CELLS[newX - vertical][newY - horizontal].setText(CELL_VALUES[0]);
                    BOARD_CELLS[newX - vertical][newY - horizontal].setBackgroundResource(CELL_BG[0]);

                    lastCombined = CELL_VALUES[getIndex(cellText) + 1];

                    scoreUpdater(newValue);
                    bestScoreUpdater();

                    //Animation
                    BOARD_CELLS[newX][newY].startAnimation(pulse);

                    if (newValue == "2048") {
                        winCheck(2048, true);
                    }
                }
                canMove = false;
                impossibleMoves = 0;
            } else {
                impossibleMoves = 1;
                canMove = false;
            }
        }
    }

    public void newGame(View v) {
        timer.setBase(SystemClock.elapsedRealtime());
        timer.stop();
        timer.start();

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                BOARD_CELLS[i][j].setText(CELL_VALUES[0]);
                BOARD_CELLS[i][j].setBackgroundResource(CELL_BG[0]);
            }
        }

        scoreLyView.setText("0");
        moveLyView.setText("0 Moves");
        movesValue = 1;

        generateCell(2);
    }

    // GESTURE DETECTION
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    static class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

            float x1 = event1.getX();
            float y1 = event1.getY();

            float x2 = event2.getX();
            float y2 = event2.getY();

            Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());

            Direction direction = getDirection(x1, y1, x2, y2);
            return onSwipe(direction);
        }

        public boolean onSwipe(Direction direction) {
            return false;
        }

        public double getAngle(float x1, float y1, float x2, float y2) {

            double rad = Math.atan2(y1 - y2, x2 - x1) + Math.PI;
            return (rad * 180 / Math.PI + 180) % 360;
        }

        public Direction getDirection(float x1, float y1, float x2, float y2) {
            double angle = getAngle(x1, y1, x2, y2);
            return Direction.fromAngle(angle);
        }

        public enum Direction {
            up,
            down,
            left,
            right;

            public static Direction fromAngle(double angle) {
                if (inRange(angle, 45, 135)) {
                    Log.d(DEBUG_TAG, "onFling: up");

                    for (int i = 0; i < BOARD_SIZE; i++) {
                        for (int j = 0; j < BOARD_SIZE; j++) {
                            for (int k = 0; k < BOARD_SIZE; k++) {
                                checkMovement(i, j, -1, 0, false);
                            }
                        }
                    }

                    generateCell(1);
                    lastCombined = "";

                    moveLyView.setText(movesValue++ + " Moves");

                    return Direction.up;

                } else if (inRange(angle, 0, 45) || inRange(angle, 315, 360)) {
                    Log.d(DEBUG_TAG, "onFling: right");

                    for (int i = BOARD_SIZE - 1; i >= 0; i--) {
                        for (int j = BOARD_SIZE - 1; j >= 0; j--) {
                            for (int k = BOARD_SIZE - 1; k >= 0; k--) {
                                checkMovement(i, j, 0, 1, false);
                            }
                        }
                    }

                    generateCell(1);
                    lastCombined = "";

                    moveLyView.setText(movesValue++ + " Moves");

                    return Direction.right;

                } else if (inRange(angle, 225, 315)) {
                    Log.d(DEBUG_TAG, "onFling: down");

                    for (int i = BOARD_SIZE - 1; i >= 0; i--) {
                        for (int j = BOARD_SIZE - 1; j >= 0; j--) {
                            for (int k = BOARD_SIZE - 1; k >= 0; k--) {
                                checkMovement(i, j, 1, 0, false);
                            }
                        }
                    }

                    generateCell(1);
                    lastCombined = "";

                    moveLyView.setText(movesValue++ + " Moves");

                    return Direction.down;

                } else {
                    Log.d(DEBUG_TAG, "onFling: left");

                    for (int i = 0; i < BOARD_SIZE; i++) {
                        for (int j = 0; j < BOARD_SIZE; j++) {
                            for (int k = 0; k < BOARD_SIZE; k++) {
                                checkMovement(i, j, 0, -1, false);
                            }
                        }
                    }

                    generateCell(1);
                    lastCombined = "";
                    moveLyView.setText(movesValue++ + " Moves");

                    return Direction.left;

                }

            }

            private static boolean inRange(double angle, float init, float end) {
                return (angle >= init) && (angle < end);
            }
        }
    }
}