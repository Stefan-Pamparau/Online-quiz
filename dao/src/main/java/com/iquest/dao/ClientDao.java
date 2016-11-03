package com.iquest.dao;

import com.iquest.model.user.Admin;
import com.iquest.model.user.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientDao extends CrudRepository<Client, Integer> {

    List<Client> findByEmail(String email);
}
