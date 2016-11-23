package com.iquest.webapp.dto;

import com.iquest.model.quiz.answer.Answer;
import com.iquest.model.quiz.question.Question;

public class QuestionAnswerDto {
    private Question question;
    private Answer answer;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
