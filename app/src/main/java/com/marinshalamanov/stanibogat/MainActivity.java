package com.marinshalamanov.stanibogat;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Question[] questions = null;

        try {
            XmlResourceParser parser=getResources().getXml(R.xml.easy_questions);
            QuestionsParser quizTextParser = new QuestionsParser();
            questions = quizTextParser.parseXml(parser);

        } catch (XmlPullParserException | IOException | Resources.NotFoundException e) {
            Log.e(getClass().toString(), "exception duiring parsing", e);
        }

        Log.e(getClass().toString(), questions[0].getText());
    }

    public void jokerClicked(View v) {
        v.setEnabled(false);
    }
}
