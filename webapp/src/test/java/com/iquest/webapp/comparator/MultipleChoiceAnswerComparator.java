package com.iquest.webapp.comparator;

import com.iquest.model.quiz.answer.MultipleChoiceAnswer;

import java.util.Comparator;

public class MultipleChoiceAnswerComparator implements Comparator<MultipleChoiceAnswer> {

    @Override
    public int compare(MultipleChoiceAnswer o1, MultipleChoiceAnswer o2) {
        return o1.getId().compareTo(o2.getId());
    }
}
