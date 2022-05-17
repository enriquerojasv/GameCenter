package com.androidclase.gamecenter.game2048;

import android.os.Bundle;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidclase.gamecenter.R;

public class Game2048Activity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_2048);

        mTextView = (TextView) findViewById(R.id.text);

    }
}