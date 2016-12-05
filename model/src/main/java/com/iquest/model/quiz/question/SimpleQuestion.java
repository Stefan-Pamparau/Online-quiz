package com.iquest.model.quiz.question;

import com.iquest.model.quiz.answer.SimpleAnswer;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue(value = "SIMPLE_QUESTION")
public class SimpleQuestion extends Question {

    @OneToOne(mappedBy = "question", cascade = CascadeType.ALL)
    private SimpleAnswer answer;

    public SimpleAnswer getAnswer() {
        return answer;
    }

    public void setAnswer(SimpleAnswer answer) {
        this.answer = answer;
    }
}
