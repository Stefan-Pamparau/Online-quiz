package com.iquest.dao;

import com.iquest.model.quiz.question.SimpleQuestion;
import org.springframework.data.repository.CrudRepository;

public interface SimpleQuestionDao extends CrudRepository<SimpleQuestion, Integer> {
}
