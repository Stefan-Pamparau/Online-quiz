package com.iquest.dao;

import com.iquest.model.quiz.ExamQuiz;
import org.springframework.data.repository.CrudRepository;

public interface ExamQuizDao extends CrudRepository<ExamQuiz, Integer> {
}
