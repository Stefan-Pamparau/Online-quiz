package com.iquest.model.quiz.answer;

import com.iquest.model.quiz.question.MultipleChoiceQuestion;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "MULTIPLE_CHOICE_ANSWER")
public class MultipleChoiceAnswer extends Answer {

    private Boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "multiple_choice_question_id")
    private MultipleChoiceQuestion question;

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public MultipleChoiceQuestion getQuestion() {
        return question;
    }

    public void setQuestion(MultipleChoiceQuestion question) {
        this.question = question;
    }
}
