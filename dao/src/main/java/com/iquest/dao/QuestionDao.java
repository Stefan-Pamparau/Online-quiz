package com.iquest.dao;

import com.iquest.model.quiz.question.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionDao extends CrudRepository<Question, Integer> {
}
