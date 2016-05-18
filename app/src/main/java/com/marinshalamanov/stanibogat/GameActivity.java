package com.marinshalamanov.stanibogat;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private int NUM_QUESTIONS_TO_SHOW = 3;

    private Question[] questions = null;

    private Random random = new Random();

    private int numberOfQuestionsShown = 0;
    private Question currentQuestion;

    private int numCorrectAnswers = 0;

    private TextView answ1TV, answ2TV, answ3TV, answ4TV;

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

        updateStat();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRandomQuestion();
    }

    private void loadRandomQuestion() {
        if (numberOfQuestionsShown < NUM_QUESTIONS_TO_SHOW) {
            int randomIndex;

            while(true ) {
                randomIndex = random.nextInt(questions.length);
                if (!questions[randomIndex].isShown()) {
                    break;
                }
            }

            loadQuestion(randomIndex);

        } else {
            Intent intent = new Intent(this, GameOverActivity.class);
            intent.putExtra(GameOverActivity.EXTRA_TOTAL_QUESTIONS, NUM_QUESTIONS_TO_SHOW);
            intent.putExtra(GameOverActivity.EXTRA_CORRECT_QUESTIONS, numCorrectAnswers);

            startActivity(intent);
        }
    }


    private void loadQuestion(int questionIndex) {
        currentQuestion = questions[questionIndex];
        currentQuestion.setShow();

        TextView questionTextTV = (TextView) findViewById(R.id.question);
        answ1TV = (TextView) findViewById(R.id.answ1);
        answ2TV = (TextView) findViewById(R.id.answ2);
        answ3TV = (TextView) findViewById(R.id.answ3);
        answ4TV = (TextView) findViewById(R.id.answ4);

        // load the question
        String questionText = currentQuestion.getText();
        questionTextTV.setText(questionText);

        // load the answers
        Answer[] anwers = currentQuestion.getAnwers();
        answ1TV.setText(anwers[0].getText());
        answ2TV.setText(anwers[1].getText());
        answ3TV.setText(anwers[2].getText());
        answ4TV.setText(anwers[3].getText());

        answ1TV.setVisibility(View.VISIBLE);
        answ2TV.setVisibility(View.VISIBLE);
        answ3TV.setVisibility(View.VISIBLE);
        answ4TV.setVisibility(View.VISIBLE);

        numberOfQuestionsShown++;
        updateStat();
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

        updateStat();

        loadRandomQuestion();
    }

    public void joker5050(View v) {
        v.setEnabled(false);

        int wrongAnswerIdx;

        int randomIndex;
        Answer[] answers = currentQuestion.getAnwers();

        while(true) {
            randomIndex = random.nextInt(answers.length);
            if (answers[randomIndex].getScore() == 0) {
                wrongAnswerIdx = randomIndex;
                break;
            }
        }

        if (answers[0].getScore() == 0 && wrongAnswerIdx != 0) {
            answ1TV.setVisibility(View.INVISIBLE);
        }
        if (answers[1].getScore() == 0 && wrongAnswerIdx != 1) {
            answ2TV.setVisibility(View.INVISIBLE);
        }
        if (answers[2].getScore() == 0 && wrongAnswerIdx != 2) {
            answ3TV.setVisibility(View.INVISIBLE);
        }
        if (answers[3].getScore() == 0 && wrongAnswerIdx != 3) {
            answ4TV.setVisibility(View.INVISIBLE);
        }
    }

    public void jokerChange(View v) {
        v.setEnabled(false);

        loadRandomQuestion();
        numberOfQuestionsShown--;
    }

    public void jokerAudience(View v) {
        v.setEnabled(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Публиката смята, че отговор B е правилен.");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateStat() {
        TextView stat = (TextView) findViewById(R.id.questionsCounts);
        stat.setText(numberOfQuestionsShown + " / " + NUM_QUESTIONS_TO_SHOW);
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
