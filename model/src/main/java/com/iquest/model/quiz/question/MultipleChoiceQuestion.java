package com.iquest.model.quiz.question;

import com.iquest.model.quiz.answer.MultipleChoiceAnswer;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@DiscriminatorValue(value = "MULTIPLE_CHOICE_QUESTION")
public class MultipleChoiceQuestion extends Question {

    @OneToMany(mappedBy = "question")
    private List<MultipleChoiceAnswer> answers;

    public List<MultipleChoiceAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<MultipleChoiceAnswer> answers) {
        this.answers = answers;
    }
}
