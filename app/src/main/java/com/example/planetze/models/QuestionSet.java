package com.example.planetze.models;

import static java.util.Arrays.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionSet {

    public String questionSetID;

    public List<Question> questions;

    public QuestionSet() {
        questions = new ArrayList<>();
    }

    public QuestionSet(String questionSetID, Question[] questionsArr) {
        this.questionSetID = questionSetID;
        questions = asList(questionsArr);
    }
}
