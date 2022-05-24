package com.androidclase.gamecenter.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DbScores extends DbHelper {

    Context context;

    public DbScores(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertScore(String user, String game, String score) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("user", user);
        values.put("game", game);
        values.put("score", score);

        long id = db.insert(TABLE_SCORES, null, values);
        return id;
    }
}
