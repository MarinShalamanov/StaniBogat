package com.marinshalamanov.stanibogat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
    }

    public void restart(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
