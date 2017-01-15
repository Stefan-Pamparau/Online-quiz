package com.iquest.webapp.dto.frommodel;

import com.iquest.model.quiz.QuizType;

public class QuizDto {

    private Integer id;
    private QuizType quizType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public QuizType getQuizType() {
        return quizType;
    }

    public void setQuizType(QuizType quizType) {
        this.quizType = quizType;
    }

}
