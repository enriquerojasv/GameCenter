package com.androidclase.gamecenter.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidclase.gamecenter.Constants;
import com.androidclase.gamecenter.R;
import com.androidclase.gamecenter.db.DbHelper;

public class SignUpActivity extends AppCompatActivity {

    private EditText etUsername;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_sign_up);

        etUsername = findViewById(R.id.et_username);
        Button btAccept = findViewById(R.id.bt_accept);

        dbHelper = new DbHelper(this);

        btAccept.setOnClickListener(view -> onButtonAcceptClick(view));
    }

    private void onButtonAcceptClick(View view) {
        String username = etUsername.getText().toString();
        if (username.isEmpty()) {
            Toast.makeText(this, "Username required", Toast.LENGTH_SHORT).show();
        } else {
            Intent menuIntent = new Intent(this, MenuActivity.class);
            menuIntent.putExtra(Constants.USERNAME, username);
            startActivity(menuIntent);
            finish();
        }
    }

    private void setupFullscreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }
}