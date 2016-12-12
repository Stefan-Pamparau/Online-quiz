package com.iquest.dao;

import com.iquest.model.user.UserLobbySession;
import org.springframework.data.repository.CrudRepository;

public interface UserLobbySessionDao extends CrudRepository<UserLobbySession, Integer> {
}
