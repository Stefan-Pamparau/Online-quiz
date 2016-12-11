package com.iquest.webapp.comparator;

import com.iquest.model.quiz.answer.SimpleAnswer;

import java.util.Comparator;

public class SimpleAnswerComparator implements Comparator<SimpleAnswer> {
    @Override
    public int compare(SimpleAnswer o1, SimpleAnswer o2) {
        return o1.getId().compareTo(o2.getId());
    }
}
