package com.iquest.model.quiz.answer;

import com.iquest.model.quiz.question.SimpleQuestion;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue(value = "SIMPLE_ANSWER")
public class SimpleAnswer extends Answer {

    @OneToOne(optional = false)
    private SimpleQuestion question;

    public SimpleQuestion getQuestion() {
        return question;
    }

    public void setQuestion(SimpleQuestion question) {
        this.question = question;
    }
}
