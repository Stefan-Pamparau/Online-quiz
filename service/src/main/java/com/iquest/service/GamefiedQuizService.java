package com.iquest.service;


import com.iquest.model.quiz.GamefiedQuiz;

import java.util.List;

public interface GamefiedQuizService {
    /**
     * Saves a given gamified quiz.
     *
     * @param gamefiedQuiz gamified quiz to be saved
     * @return the saved gamified quiz
     */
    GamefiedQuiz save(GamefiedQuiz gamefiedQuiz);

    /**
     * Retrieves an gamified quiz by its id.
     *
     * @param id must not be {@literal null}.
     * @return the gamified quiz with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    GamefiedQuiz findWithId(Integer id);

    /**
     * Returns whether an gamified quiz with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return true if an gamified quiz with the given id exists, {@literal false} otherwise
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    Boolean exists(Integer id);

    /**
     * Returns all instances of the type.
     *
     * @return all gamified quizzes
     */
    List<GamefiedQuiz> findAll();

    /**
     * Returns the number of gamified quizzes available.
     *
     * @return the number of gamified quizzes
     */
    Long getNumberOfAdmins();

    /**
     * Deletes the gamified quiz with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void delete(Integer id);

    /**
     * Deletes a given gamified quiz.
     *
     * @param gamefiedQuiz gamified quiz to be deleted
     * @throws IllegalArgumentException in case the given gamified quiz is {@literal null}.
     */
    void delete(GamefiedQuiz gamefiedQuiz);

    /**
     * Deletes all gamified quizzes managed by the service.
     */
    void deleteAll();
}
