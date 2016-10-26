package com.iquest.service;

import com.iquest.model.quiz.ExamQuiz;

import java.util.List;

public interface ExamQuizService {
    /**
     * Saves a given exam quiz.
     *
     * @param examQuiz exam quiz to be saved
     * @return the saved exam quiz
     */
    ExamQuiz save(ExamQuiz examQuiz);

    /**
     * Retrieves an exam quiz by its id.
     *
     * @param id must not be {@literal null}.
     * @return the exam quiz with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    ExamQuiz findWithId(Integer id);

    /**
     * Returns whether an exam quiz with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return true if an exam quiz with the given id exists, {@literal false} otherwise
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    Boolean exists(Integer id);

    /**
     * Returns all instances of the type.
     *
     * @return all exam quizzes
     */
    List<ExamQuiz> findAll();

    /**
     * Returns the number of exam quizzes available.
     *
     * @return the number of exam quizzes
     */
    Long getNumberOfAdmins();

    /**
     * Deletes the exam quiz with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void delete(Integer id);

    /**
     * Deletes a given exam quiz.
     *
     * @param examQuiz exam quiz to be deleted
     * @throws IllegalArgumentException in case the given exam quiz is {@literal null}.
     */
    void delete(ExamQuiz examQuiz);

    /**
     * Deletes all exam quizzes managed by the service.
     */
    void deleteAll();
}
