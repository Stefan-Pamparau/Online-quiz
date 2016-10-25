package com.iquest.model.quiz;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "EXAM_QUIZ")
public class ExamQuiz extends Quiz {
}
