package com.iquest.dao;

import com.iquest.model.quiz.GamifiedQuiz;
import org.springframework.data.repository.CrudRepository;

public interface GamifiedQuizDao extends CrudRepository<GamifiedQuiz, Integer> {
}
