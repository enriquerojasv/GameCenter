package com.androidclase.gamecenter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getSupportActionBar().hide();

        ListView menuList = (ListView) findViewById(R.id.listView_menu);

        String[] items = {getResources().getString(R.string.game2048),
                getResources().getString(R.string.senku), getResources().getString(R.string.help)};

        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this, R.layout.menu_item, items);

        menuList.setAdapter(adapt);


    }

    public void OnClick(View v) {
        Toast.makeText(this, "Actividad pendiente", Toast.LENGTH_SHORT).show();
    }
}
