package com.iquest.webapp.comparator;

import com.iquest.model.quiz.GamefiedQuiz;

import java.util.Comparator;

public class GamefiedQuizComparator implements Comparator<GamefiedQuiz> {
    @Override
    public int compare(GamefiedQuiz o1, GamefiedQuiz o2) {
        return o1.getId().compareTo(o2.getId());
    }
}
