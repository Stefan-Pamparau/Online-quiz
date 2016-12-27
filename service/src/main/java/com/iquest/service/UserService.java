package com.iquest.service;

import com.iquest.model.user.User;

import java.util.List;

public interface UserService {

    /**
     * Returns an user with a given email and password.
     *
     * @param email    email of user
     * @param password password of user
     * @return an user with a given email and password or null if not found
     */
    User findByEmailAndPassword(String email, String password);


    /**
     * Returns an user with a given email.
     *
     * @param email email of user
     * @return user with a given email or null if not found
     */
    User findByEmail(String email);


    /**
     * Returns an user with a given token.
     *
     * @param token token of the user
     * @return user with a given token or null if not found
     */
    User findByToken(String token);

    /**
     * Saves a given user.
     *
     * @param user user to be saved
     * @return the saved user
     */
    User save(User user);

    /**
     * Retrieves an user by its id.
     *
     * @param id must not be {@literal null}.
     * @return the user with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    User findWithId(Integer id);


    /**
     * Returns whether an user with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return true if an user with the given id exists, {@literal false} otherwise
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    Boolean exists(Integer id);

    /**
     * Returns all instances of the type.
     *
     * @return all users
     */
    List<User> findAll();

    /**
     * Returns the number of users available.
     *
     * @return the number of users
     */
    Long getNumberOfUsers();

    /**
     * Deletes the user with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void delete(Integer id);

    /**
     * Deletes a given user.
     *
     * @param user user to be deleted
     * @throws IllegalArgumentException in case the given user is {@literal null}.
     */
    void delete(User user);

    /**
     * Deletes all users managed by the service.
     */
    void deleteAll();
}
