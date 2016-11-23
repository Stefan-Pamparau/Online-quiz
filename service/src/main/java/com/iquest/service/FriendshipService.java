package com.iquest.service;

import com.iquest.model.user.Friendship;

import java.util.List;

public interface FriendshipService {
    /**
     * Saves a given friendship.
     *
     * @param friendship friendship to be saved
     * @return the saved friendship
     */
    Friendship save(Friendship friendship);

    /**
     * Retrieves a friendship by its id.
     *
     * @param id must not be {@literal null}.
     * @return the friendship with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    Friendship findWithId(Integer id);

    /**
     * Returns whether an friendship with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return true if an friendship with the given id exists, {@literal false} otherwise
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    Boolean exists(Integer id);

    /**
     * Returns all instances of the type.
     *
     * @return all friendships
     */
    List<Friendship> findAll();

    /**
     * Returns the number of friendships available.
     *
     * @return the number of friendships
     */
    Long getNumberOfFriendships();

    /**
     * Deletes the friendship with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void delete(Integer id);

    /**
     * Deletes a given friendship.
     *
     * @param friendship friendship to be deleted
     * @throws IllegalArgumentException in case the given friendship is {@literal null}.
     */
    void delete(Friendship friendship);

    /**
     * Deletes all friendships managed by the service.
     */
    void deleteAll();
}
