package com.androidclase.gamecenter.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidclase.gamecenter.R;
import com.androidclase.gamecenter.game2048.Game2048Activity;
import com.androidclase.gamecenter.senku.GameSenkuActivity;
import com.androidclase.gamecenter.settings.MainSettingsActivity;

public class MenuActivity extends AppCompatActivity {

    final int GAME2048 = 0;
    final int SENKU = 1;
    final int SETTINGS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getSupportActionBar().hide();

        ListView menuList = (ListView) findViewById(R.id.listView_menu);

        String[] items = {getResources().getString(R.string.game2048), getResources().getString(R.string.senku), getResources().getString(R.string.settings)};

        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this, R.layout.menu_item, items);

        menuList.setAdapter(adapt);

        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                switch (position) {
                    case GAME2048:
                        launch2048(view);
                        break;
                    case SENKU:
                        launchSenku(view);
                        break;
                    case SETTINGS:
                        launchSettings(view);
                }
            }
        });
    }

    public void launch2048(View v) {
        Intent intent2048 = new Intent(this, Game2048Activity.class);
        startActivity(intent2048);
    }
    
    public void launchSenku(View v) {
        Intent senkuIntent = new Intent(this, GameSenkuActivity.class);
        startActivity(senkuIntent);
    }

    public void launchSettings(View v) {
        Intent settingsIntent = new Intent(this, MainSettingsActivity.class);
        startActivity(settingsIntent);
    }
}
