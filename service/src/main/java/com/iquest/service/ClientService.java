package com.iquest.service;

import com.iquest.model.user.Client;

import java.util.List;

public interface ClientService {
    /**
     * Saves a given client.
     *
     * @param client client to be saved
     * @return the saved client
     */
    Client save(Client client);

    /**
     * Retrieves an client by its id.
     *
     * @param id must not be {@literal null}.
     * @return the client with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    Client findWithId(Integer id);

    /**
     * Returns whether an client with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return true if an client with the given id exists, {@literal false} otherwise
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    Boolean exists(Integer id);

    /**
     * Returns all instances of the type.
     *
     * @return all clients
     */
    List<Client> findAll();

    /**
     * Returns the number of clients available.
     *
     * @return the number of clients
     */
    Long getNumberOfAdmins();

    /**
     * Deletes the client with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void delete(Integer id);

    /**
     * Deletes a given client.
     *
     * @param client client to be deleted
     * @throws IllegalArgumentException in case the given client is {@literal null}.
     */
    void delete(Client client);

    /**
     * Deletes all clients managed by the service.
     */
    void deleteAll();
}
