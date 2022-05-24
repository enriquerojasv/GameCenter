package com.androidclase.gamecenter.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static final String TABLE_SCORES = "t_scores";
    public static final String TABLE_FORMAT = "CREATE TABLE " + TABLE_SCORES + "(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "user TEXT NOT NULL," +
            "score INTEGER)";
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "scores.db";

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(TABLE_FORMAT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_SCORES);
        onCreate(sqLiteDatabase);

    }
}
