package com.iquest.dao;

import com.iquest.model.quiz.answer.Answer;
import org.springframework.data.repository.CrudRepository;

public interface AnswerDao extends CrudRepository<Answer, Integer> {
}
