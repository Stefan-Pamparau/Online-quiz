package com.iquest.webapp.dto.frommodel;

import com.iquest.model.quiz.answer.AnswerType;

public class SimpleAnswerDto {

    private Integer id;
    private String answerText;
    private AnswerType answerType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public AnswerType getAnswerType() {
        return answerType;
    }

    public void setAnswerType(AnswerType answerType) {
        this.answerType = answerType;
    }
}
