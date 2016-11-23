package com.iquest.service;

import com.iquest.model.quiz.question.MultipleChoiceQuestion;

import java.util.List;

public interface MultipleChoiceQuestionService {
    /**
     * Saves a given multiple choice question.
     *
     * @param multipleChoiceQuestion multiple choice question to be saved
     * @return the saved multiple choice question
     */
    MultipleChoiceQuestion save(MultipleChoiceQuestion multipleChoiceQuestion);

    /**
     * Retrieves an multiple choice question by its id.
     *
     * @param id must not be {@literal null}.
     * @return the multiple choice question with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    MultipleChoiceQuestion findWithId(Integer id);

    /**
     * Returns whether an multiple choice question with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return true if an multiple choice question with the given id exists, {@literal false} otherwise
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    Boolean exists(Integer id);

    /**
     * Returns all instances of the type.
     *
     * @return all multiple choice questions
     */
    List<MultipleChoiceQuestion> findAll();

    /**
     * Returns the number of multiple choice questions available.
     *
     * @return the number of multiple choice questions
     */
    Long getNumberOfMultipleChoiceQuestions();

    /**
     * Deletes the multiple choice question with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void delete(Integer id);

    /**
     * Deletes a given multiple choice question.
     *
     * @param multipleChoiceQuestion multiple choice question to be deleted
     * @throws IllegalArgumentException in case the given multiple choice question is {@literal null}.
     */
    void delete(MultipleChoiceQuestion multipleChoiceQuestion);

    /**
     * Deletes all multiple choice questions managed by the service.
     */
    void deleteAll();
}
