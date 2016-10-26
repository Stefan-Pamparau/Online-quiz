package com.iquest.service;

import com.iquest.model.quiz.question.SimpleQuestion;

import java.util.List;

public interface SimpleQuestionService {
    /**
     * Saves a given simple question.
     *
     * @param simpleQuestion simple question to be saved
     * @return the saved simple question
     */
    SimpleQuestion save(SimpleQuestion simpleQuestion);

    /**
     * Retrieves an simple question by its id.
     *
     * @param id must not be {@literal null}.
     * @return the simple question with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    SimpleQuestion findWithId(Integer id);

    /**
     * Returns whether an simple question with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return true if an simple question with the given id exists, {@literal false} otherwise
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    Boolean exists(Integer id);

    /**
     * Returns all instances of the type.
     *
     * @return all simple questions
     */
    List<SimpleQuestion> findAll();

    /**
     * Returns the number of simple questions available.
     *
     * @return the number of simple questions
     */
    Long getNumberOfAdmins();

    /**
     * Deletes the simple question with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void delete(Integer id);

    /**
     * Deletes a given simple question.
     *
     * @param simpleQuestion simple question to be deleted
     * @throws IllegalArgumentException in case the given simple question is {@literal null}.
     */
    void delete(SimpleQuestion simpleQuestion);

    /**
     * Deletes all simple questions managed by the service.
     */
    void deleteAll();
}
