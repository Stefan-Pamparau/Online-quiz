package com.iquest.model.quiz;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "GAMEFIED_QUIZ")
public class GamefiedQuiz extends Quiz {
}
