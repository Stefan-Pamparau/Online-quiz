package com.iquest.webapp.comparator;

import com.iquest.model.quiz.question.MultipleChoiceQuestion;

import java.util.Comparator;

public class MultipleChoiceQuestionComparator implements Comparator<MultipleChoiceQuestion> {

    @Override
    public int compare(MultipleChoiceQuestion o1, MultipleChoiceQuestion o2) {
        return o1.getId().compareTo(o2.getId());
    }
}
