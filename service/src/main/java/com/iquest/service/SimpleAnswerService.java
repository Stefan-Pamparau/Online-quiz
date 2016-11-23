package com.iquest.service;

import com.iquest.model.quiz.answer.SimpleAnswer;

import java.util.List;

public interface SimpleAnswerService {
    /**
     * Saves a given simple answer.
     *
     * @param simpleAnswer simple answer to be saved
     * @return the saved simple answer
     */
    SimpleAnswer save(SimpleAnswer simpleAnswer);

    /**
     * Retrieves an simple answer by its id.
     *
     * @param id must not be {@literal null}.
     * @return the simple answer with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    SimpleAnswer findWithId(Integer id);

    /**
     * Returns whether an simple answer with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return true if an simple answer with the given id exists, {@literal false} otherwise
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    Boolean exists(Integer id);

    /**
     * Returns all instances of the type.
     *
     * @return all simple answers
     */
    List<SimpleAnswer> findAll();

    /**
     * Returns the number of simple answers available.
     *
     * @return the number of simple answers
     */
    Long getNumberOfSimpleAnswers();

    /**
     * Deletes the simple answer with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void delete(Integer id);

    /**
     * Deletes a given simple answer.
     *
     * @param simpleAnswer simple answer to be deleted
     * @throws IllegalArgumentException in case the given simple answer is {@literal null}.
     */
    void delete(SimpleAnswer simpleAnswer);

    /**
     * Deletes all simple answers managed by the service.
     */
    void deleteAll();
}
