package com.iquest.dao;

import com.iquest.model.Lobby;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface LobbyDao extends CrudRepository<Lobby, Integer> {
    List<Lobby> findByCreationDateGreaterThan(Date date);

    List<Lobby> findByCreationDateLessThan(Date date);

    List<Lobby> findByCreationDateBetween(Date min, Date max);
}
