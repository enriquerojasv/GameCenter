package com.androidclase.gamecenter.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.androidclase.gamecenter.settings.Scores;

import java.util.ArrayList;

public class DbScores extends DbHelper {

    Context context;

    public DbScores(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertScore(String user, String game, int score) {
        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("user", user);
            values.put("game", game);
            values.put("score", score);

            id = db.insert(TABLE_SCORES, null, values);
        } catch (Exception e) {
            e.toString();
        }
        return id;
    }

    public ArrayList<Scores> showScores() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Scores> scoresList = new ArrayList<>();
        Scores score = null;
        Cursor scoresCursor = null;

        scoresCursor = db.rawQuery("SELECT * FROM " + TABLE_SCORES, null);
        if (scoresCursor.moveToFirst()) {
            do {
                score = new Scores();
                score.setId(scoresCursor.getInt(0));
                score.setUser(scoresCursor.getString(1));
                score.setGame(scoresCursor.getString(2));
                score.setScore(scoresCursor.getString(3));
                scoresList.add(score);
            } while (scoresCursor.moveToNext());
        }

        scoresCursor.close();

        return scoresList;
    }
}
