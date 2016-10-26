package com.iquest.dao;

import com.iquest.model.quiz.answer.SimpleAnswer;
import org.springframework.data.repository.CrudRepository;

public interface SimpleAnswerDao extends CrudRepository<SimpleAnswer, Integer> {
}
