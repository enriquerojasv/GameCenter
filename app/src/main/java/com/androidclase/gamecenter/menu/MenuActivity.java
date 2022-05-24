package com.androidclase.gamecenter.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidclase.gamecenter.Constants;
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

        setupFullscreen();
        setContentView(R.layout.activity_menu);

        TextView welcome = findViewById(R.id.tv_welcome);
        String recoveredUsername = getIntent().getStringExtra(Constants.USERNAME);
        welcome.setText(getString(R.string.welcome_username) + " " + recoveredUsername + "!");

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
                        launch2048(view, recoveredUsername);
                        break;
                    case SENKU:
                        launchSenku(view, recoveredUsername);
                        break;
                    case SETTINGS:
                        launchSettings(view, recoveredUsername);
                }
            }
        });
    }

    public void launch2048(View v, String recoveredUsername) {
        Intent intent2048 = new Intent(this, Game2048Activity.class);
        intent2048.putExtra(Constants.USERNAME, recoveredUsername);
        startActivity(intent2048);
    }

    public void launchSenku(View v, String recoveredUsername) {
        Intent senkuIntent = new Intent(this, GameSenkuActivity.class);
        senkuIntent.putExtra(Constants.USERNAME, recoveredUsername);
        startActivity(senkuIntent);
    }

    public void launchSettings(View v, String recoveredUsername) {
        Intent settingsIntent = new Intent(this, MainSettingsActivity.class);
        settingsIntent.putExtra(Constants.USERNAME, recoveredUsername);
        startActivity(settingsIntent);
    }

    private void setupFullscreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }
}
