package com.iquest.service;


import com.iquest.model.quiz.answer.MultipleChoiceAnswer;

import java.util.List;

public interface MultipleChoiceAnswerService {
    /**
     * Saves a given multiple choice answer.
     *
     * @param multipleChoiceAnswer multiple choice answer to be saved
     * @return the saved multiple choice answer
     */
    MultipleChoiceAnswer save(MultipleChoiceAnswer multipleChoiceAnswer);

    /**
     * Retrieves an multiple choice answer by its id.
     *
     * @param id must not be {@literal null}.
     * @return the multiple choice answer with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    MultipleChoiceAnswer findWithId(Integer id);

    /**
     * Returns whether an multiple choice answer with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return true if an multiple choice answer with the given id exists, {@literal false} otherwise
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    Boolean exists(Integer id);

    /**
     * Returns all instances of the type.
     *
     * @return all multiple choice answers
     */
    List<MultipleChoiceAnswer> findAll();

    /**
     * Returns the number of multiple choice answers available.
     *
     * @return the number of multiple choice answers
     */
    Long getNumberOfMultipleChoiceAnswers();

    /**
     * Deletes the multiple choice answer with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void delete(Integer id);

    /**
     * Deletes a given multiple choice answer.
     *
     * @param multipleChoiceAnswer multiple choice answer to be deleted
     * @throws IllegalArgumentException in case the given multiple choice answer is {@literal null}.
     */
    void delete(MultipleChoiceAnswer multipleChoiceAnswer);

    /**
     * Deletes all multiple choice answers managed by the service.
     */
    void deleteAll();
}
