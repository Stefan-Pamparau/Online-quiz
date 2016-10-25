package com.iquest.model.quiz;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "GAMIFIED_QUIZ")
public class GamifiedQuiz extends Quiz {
}
