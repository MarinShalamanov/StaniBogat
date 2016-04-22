package com.marinshalamanov.stanibogat;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestionsParser {

    protected Question[] parseXml(XmlPullParser parser) throws IOException,
            XmlPullParserException {
        List<Question> questions = null;
        int eventType = parser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_TAG) {
                String name = parser.getName();
                if("questions".equals(name)){
                    questions = parseQuestions(parser);
                }
                else {
                    skip(parser);
                }
            }
            eventType = parser.next();
        }

        Question[] questionsArray = new Question[ questions.size()];
        questions.toArray(questionsArray);

        return questionsArray;
    }

    public List<Question> parseQuestions(XmlPullParser parser) throws IOException,
            XmlPullParserException {
        List<Question> questions = new ArrayList<>();
        parser.require(XmlPullParser.START_TAG, null, "questions");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("question")) {
                questions.add(parseQuestion(parser));
            } else {
                skip(parser);
            }
        }
        parser.require(XmlPullParser.END_TAG, null, "questions");
        return questions;
    }

    public Question parseQuestion(XmlPullParser parser) throws IOException,
            XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "question");
        String id = parser.getAttributeValue(null, "id");
        String text = "";
        Answer answers[] = new Answer[4];
        int answersRead = 0;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("description")) {
                text = readTextTag(parser,"description");
            } else if (name.equals("answer")) {
                Answer answer = parseAnswer(parser);
                answers[answersRead++] = answer;
            } else {
                skip(parser);
            }
        }
        parser.require(XmlPullParser.END_TAG, null, "question");
        Question question = new Question(text, answers);
        return question;
    }

    public Answer parseAnswer(XmlPullParser parser) throws IOException,
            XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "answer");
        String id = parser.getAttributeValue(null, "id");
        int score = 0;
        try{
            score = Integer.parseInt(parser.getAttributeValue(null, "score"));
        }catch (Throwable ignored){}
        String text = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "answer");
        Answer answer = new Answer(score,text);
        return answer;
    }

    private String readTextTag(XmlPullParser parser, String tagName) throws IOException,
            XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, tagName);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, tagName);
        return title;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
