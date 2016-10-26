package com.iquest.service;

import com.iquest.model.user.Admin;

import java.util.List;

public interface AdminService {
    /**
     * Saves a given admin.
     *
     * @param admin admin to be saved
     * @return the saved admin
     */
    Admin save(Admin admin);

    /**
     * Retrieves an admin by its id.
     *
     * @param id must not be {@literal null}.
     * @return the admin with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    Admin findWithId(Integer id);

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
     * @return all admins
     */
    List<Admin> findAll();

    /**
     * Returns the number of admins available.
     *
     * @return the number of admins
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
    void delete(Admin admin);

    /**
     * Deletes all admins managed by the service.
     */
    void deleteAll();
}
