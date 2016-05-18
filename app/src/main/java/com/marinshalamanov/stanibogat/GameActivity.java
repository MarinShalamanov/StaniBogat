package com.marinshalamanov.stanibogat;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private Question[] questions = null;

    private Random random = new Random();

    private int numberOfQuestionsShown = 0;
    private Question currentQuestion;

    private int numCorrectAnswers = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        try {
            XmlResourceParser parser = getResources().getXml(R.xml.easy_questions);
            QuestionsParser quizTextParser = new QuestionsParser();
            questions = quizTextParser.parseXml(parser);

        } catch (XmlPullParserException | IOException | Resources.NotFoundException e) {
            Log.e(getClass().toString(), "exception duiring parsing", e);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRandomQuestion();
    }

    private void loadRandomQuestion() {
        if (numberOfQuestionsShown < questions.length) {
            int randomIndex = random.nextInt(questions.length);
            loadQuestion(randomIndex);
        } else {
            Intent intent = new Intent(this, GameOverActivity.class);
            startActivity(intent);
        }
    }


    private void loadQuestion(int questionIndex) {
        currentQuestion = questions[questionIndex];

        TextView questionTextTV = (TextView) findViewById(R.id.question);
        TextView answ1TV = (TextView) findViewById(R.id.answ1);
        TextView answ2TV = (TextView) findViewById(R.id.answ2);
        TextView answ3TV = (TextView) findViewById(R.id.answ3);
        TextView answ4TV = (TextView) findViewById(R.id.answ4);

        // load the question
        String questionText = currentQuestion.getText();
        questionTextTV.setText(questionText);

        // load the answers
        Answer[] anwers = currentQuestion.getAnwers();
        answ1TV.setText(anwers[0].getText());
        answ2TV.setText(anwers[1].getText());
        answ3TV.setText(anwers[2].getText());
        answ4TV.setText(anwers[3].getText());

        numberOfQuestionsShown++;
    }

    private String getRightAnswerText() {
        Answer[] anwers = currentQuestion.getAnwers();
        if (anwers[0].getScore() > 0) {
            return anwers[0].getText();
        }

        if (anwers[1].getScore() > 0) {
            return anwers[1].getText();
        }

        if (anwers[2].getScore() > 0) {
            return anwers[2].getText();
        }

        if (anwers[3].getScore() > 0) {
            return anwers[3].getText();
        }

        // no right answer found up to here
        // return an invalid value
        return null;
    }

    public void onAnswerClicked(View v) {
        TextView answerTV = (TextView) v;

        String rightAnswer = getRightAnswerText();

        if (rightAnswer.equals(answerTV.getText())) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Правилен отговор!");
            builder.setPositiveButton("OK", null);
            AlertDialog dialog = builder.create();
            dialog.show();
            numCorrectAnswers++;

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Грешен отговор!");
            builder.setPositiveButton("OK", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        TextView stat = (TextView) findViewById(R.id.questionsCounts);
        stat.setText(numCorrectAnswers + " / " + questions.length);

        loadRandomQuestion();
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();


    }
}
