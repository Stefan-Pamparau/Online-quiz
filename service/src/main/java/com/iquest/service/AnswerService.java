package com.iquest.service;

import com.iquest.model.quiz.answer.Answer;

import java.util.List;

public interface AnswerService {
    /**
     * Saves a given answer.
     *
     * @param answer answer to be saved
     * @return the saved answer
     */
    Answer save(Answer answer);

    /**
     * Retrieves an answer by its id.
     *
     * @param id must not be {@literal null}.
     * @return the answer with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    Answer findWithId(Integer id);

    /**
     * Returns whether an answer with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return true if an answer with the given id exists, {@literal false} otherwise
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    Boolean exists(Integer id);

    /**
     * Returns all instances of the type.
     *
     * @return all answers
     */
    List<Answer> findAll();

    /**
     * Returns the number of answers available.
     *
     * @return the number of answers
     */
    Long getNumberOfAnswers();

    /**
     * Deletes the answer with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void delete(Integer id);

    /**
     * Deletes a given answer.
     *
     * @param answer answer to be deleted
     * @throws IllegalArgumentException in case the given answer is {@literal null}.
     */
    void delete(Answer answer);

    /**
     * Deletes all answers managed by the service.
     */
    void deleteAll();
}
