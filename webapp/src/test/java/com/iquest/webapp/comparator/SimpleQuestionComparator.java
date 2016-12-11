package com.iquest.webapp.comparator;

import com.iquest.model.quiz.question.SimpleQuestion;

import java.util.Comparator;

public class SimpleQuestionComparator implements Comparator<SimpleQuestion> {

    @Override
    public int compare(SimpleQuestion o1, SimpleQuestion o2) {
        return o1.getId().compareTo(o2.getId());
    }
}
