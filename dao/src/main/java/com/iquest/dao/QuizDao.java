package com.iquest.dao;

import com.iquest.model.quiz.Quiz;
import org.springframework.data.repository.CrudRepository;

public interface QuizDao extends CrudRepository<Quiz, Integer> {
}
