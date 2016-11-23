package com.iquest.service;

import com.iquest.model.quiz.question.Question;

import java.util.List;

public interface QuestionService {
    /**
     * Saves a given question.
     *
     * @param question question to be saved
     * @return the saved question
     */
    Question save(Question question);

    /**
     * Retrieves an question by its id.
     *
     * @param id must not be {@literal null}.
     * @return the question with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    Question findWithId(Integer id);

    /**
     * Returns whether an question with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return true if an question with the given id exists, {@literal false} otherwise
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    Boolean exists(Integer id);

    /**
     * Returns all instances of the type.
     *
     * @return all questions
     */
    List<Question> findAll();

    /**
     * Returns the number of questions available.
     *
     * @return the number of questions
     */
    Long getNumberOfQuestions();

    /**
     * Deletes the question with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void delete(Integer id);

    /**
     * Deletes a given question.
     *
     * @param question question to be deleted
     * @throws IllegalArgumentException in case the given question is {@literal null}.
     */
    void delete(Question question);

    /**
     * Deletes all questions managed by the service.
     */
    void deleteAll();
}
