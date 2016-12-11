package com.iquest.webapp.comparator;

import com.iquest.model.quiz.ExamQuiz;

import java.util.Comparator;

public class ExamQuizComparator implements Comparator<ExamQuiz> {
    @Override
    public int compare(ExamQuiz o1, ExamQuiz o2) {
        return o1.getId().compareTo(o2.getId());
    }
}
