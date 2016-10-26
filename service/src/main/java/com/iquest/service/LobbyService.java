package com.iquest.service;

import com.iquest.model.Lobby;

import java.util.Date;
import java.util.List;

public interface LobbyService {
    /**
     * Returns lobbies with a creation date greater than a given date.
     *
     * @param date reference date
     * @return lobbies with a creation date greater than a given date.
     */
    List<Lobby> findByCreationDateGreaterThan(Date date);

    /**
     * Returns lobbies with a creation date less than a given date.
     *
     * @param date reference date
     * @return lobbies with a creation date less than a given date.
     */
    List<Lobby> findByCreationDateLessThan(Date date);

    /**
     * Returns lobbies with a creation date between given dates.
     *
     * @param min min date
     * @param max max date
     * @return lobbies with a creation date between given dates.
     */
    List<Lobby> findByCreationDateBetween(Date min, Date max);

    /**
     * Saves a given lobby.
     *
     * @param lobby lobby to be saved
     * @return the saved lobby
     */
    Lobby save(Lobby lobby);

    /**
     * Retrieves an lobby by its id.
     *
     * @param id must not be {@literal null}.
     * @return the lobby with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    Lobby findWithId(Integer id);

    /**
     * Returns whether an lobby with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return true if an lobby with the given id exists, {@literal false} otherwise
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    Boolean exists(Integer id);

    /**
     * Returns all instances of the type.
     *
     * @return all lobbies
     */
    List<Lobby> findAll();

    /**
     * Returns the number of lobbies available.
     *
     * @return the number of lobbies
     */
    Long getNumberOfAdmins();

    /**
     * Deletes the lobby with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void delete(Integer id);

    /**
     * Deletes a given lobby.
     *
     * @param lobby lobby to be deleted
     * @throws IllegalArgumentException in case the given lobby is {@literal null}.
     */
    void delete(Lobby lobby);

    /**
     * Deletes all lobbies managed by the service.
     */
    void deleteAll();
}
