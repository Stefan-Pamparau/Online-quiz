package com.iquest.dao;

import com.iquest.model.quiz.question.MultipleChoiceQuestion;
import org.springframework.data.repository.CrudRepository;

public interface MultipleChoiceQuestionDao extends CrudRepository<MultipleChoiceQuestion, Integer> {
}
