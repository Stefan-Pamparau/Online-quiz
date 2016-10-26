package com.iquest.service;

import com.iquest.model.quiz.Quiz;

import java.util.List;

public interface QuizService {
    /**
     * Saves a given quiz.
     *
     * @param quiz quiz to be saved
     * @return the saved quiz
     */
    Quiz save(Quiz quiz);

    /**
     * Retrieves an quiz by its id.
     *
     * @param id must not be {@literal null}.
     * @return the quiz with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    Quiz findWithId(Integer id);

    /**
     * Returns whether an quiz with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return true if an quiz with the given id exists, {@literal false} otherwise
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    Boolean exists(Integer id);

    /**
     * Returns all instances of the type.
     *
     * @return all quizzes
     */
    List<Quiz> findAll();

    /**
     * Returns the number of quizzes available.
     *
     * @return the number of quizzes
     */
    Long getNumberOfAdmins();

    /**
     * Deletes the quiz with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void delete(Integer id);

    /**
     * Deletes a given quiz.
     *
     * @param quiz quiz to be deleted
     * @throws IllegalArgumentException in case the given quiz is {@literal null}.
     */
    void delete(Quiz quiz);

    /**
     * Deletes all quizzes managed by the service.
     */
    void deleteAll();
}
