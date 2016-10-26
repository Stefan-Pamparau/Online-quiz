package com.iquest.service;

import com.iquest.model.quiz.Quiz;

import java.util.List;

public interface QuizService {
    /**
     * Saves a given quiz.
     *
     * @param admin admin to be saved
     * @return the saved admin
     */
    Quiz save(Quiz admin);

    /**
     * Retrieves an admin by its id.
     *
     * @param id must not be {@literal null}.
     * @return the admin with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    Quiz findWithId(Integer id);

    /**
     * Returns whether an admin with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return true if an admin with the given id exists, {@literal false} otherwise
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
     * Deletes the admin with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void delete(Integer id);

    /**
     * Deletes a given admin.
     *
     * @param admin admin to be deleted
     * @throws IllegalArgumentException in case the given admin is {@literal null}.
     */
    void delete(Quiz admin);

    /**
     * Deletes all quizzes managed by the service.
     */
    void deleteAll();
}
