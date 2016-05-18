package com.marinshalamanov.stanibogat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    public static final String EXTRA_TOTAL_QUESTIONS = "GameOverActivity.totalQuestions";
    public static final String EXTRA_CORRECT_QUESTIONS = "GameOverActivity.correctQuestions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        int totalQuestions = getIntent().getIntExtra(EXTRA_TOTAL_QUESTIONS, 0);
        int correctQuestions = getIntent().getIntExtra(EXTRA_CORRECT_QUESTIONS, 0);

        TextView result = (TextView) findViewById(R.id.resultValue);
        result.setText(correctQuestions + " / " + totalQuestions);
    }

    public void restart(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
