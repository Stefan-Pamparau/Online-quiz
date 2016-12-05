package com.iquest.dao;

import com.iquest.model.user.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientDao extends CrudRepository<Client, Integer> {

    Client findByEmail(String email);
}
