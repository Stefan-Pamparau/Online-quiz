package com.iquest.dao;

import com.iquest.model.quiz.answer.MultipleChoiceAnswer;
import org.springframework.data.repository.CrudRepository;

public interface MultipleChoiceAnswerDao extends CrudRepository<MultipleChoiceAnswer, Integer> {
}
