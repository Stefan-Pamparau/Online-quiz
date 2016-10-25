package com.iquest.model.quiz.question;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "MULTIPLE_CHOICE_QUESTION")
public class MultipleChoiceQuestion extends Question {

    @OneToMany(mappedBy = "question")
    private Set<Answer> answers;

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }
}
