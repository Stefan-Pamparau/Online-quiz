package com.iquest.webapp.dto.frommodel;

import com.iquest.model.quiz.question.QuestionType;

public class MultipleChoiceQuestionDto {

    private Integer id;
    private String questionText;
    private QuestionType questionType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }
}
