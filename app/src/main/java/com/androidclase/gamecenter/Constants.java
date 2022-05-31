package com.androidclase.gamecenter;

import android.widget.TextView;

public class Constants {

    public static final String USERNAME = "username";
    public static final String NAME_SENKU = "SENKU";
    public static final String NAME_2048 = "2048";
    public static final String NAME_BOTH = "BOTH";
    public static final String NAME_ASC = "ASC";
    public static final String NAME_DESC = "DESC";
    public static final int BOARD_SIZE = 4;
    public static final int[] CELL_BG = {R.drawable.g2048_cell_empty, R.drawable.g2048_cell_2,
            R.drawable.g2048_cell_4, R.drawable.g2048_cell_8, R.drawable.g2048_cell_16,
            R.drawable.g2048_cell_32, R.drawable.g2048_cell_64, R.drawable.g2048_cell_128,
            R.drawable.g2048_cell_256, R.drawable.g2048_cell_512, R.drawable.g2048_cell_1024,
            R.drawable.g2048_cell_2048};
    public static final TextView[][] BOARD_CELLS = new TextView[BOARD_SIZE][BOARD_SIZE];
    public static final String[] CELL_VALUES = {"", "2", "4", "8", "16", "32", "64",
            "128", "256", "512", "1024", "2048"};
    public static final int STARTING_CELLS = 2;
    public static final int OPT_GAME2048 = 0;
    public static final int OPT_SENKU = 1;
    public static final int OPT_SETTINGS = 2;

    public static final int[][] GRID_1 = {
            {0, 0, 1, 1, 1, 0, 0},
            {0, 0, 1, 1, 1, 0, 0},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 2, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {0, 0, 1, 1, 1, 0, 0},
            {0, 0, 1, 1, 1, 0, 0}
    };
    public static final int[][] GRID_2 = {
            {0, 0, 2, 2, 2, 0, 0},
            {0, 0, 2, 1, 2, 0, 0},
            {2, 2, 2, 1, 2, 2, 2},
            {2, 1, 1, 1, 1, 1, 2},
            {2, 2, 2, 1, 2, 2, 2},
            {0, 0, 2, 1, 2, 0, 0},
            {0, 0, 2, 2, 2, 0, 0}
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
}
