package com.iquest.model.quiz.question;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue(value = "SINGLE_CHOICE_QUESTION")
public class SingleChoiceQuestion {

    @OneToOne(optional = false)
    private Answer answer;
}
