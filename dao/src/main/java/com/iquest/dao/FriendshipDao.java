package com.iquest.dao;

import com.iquest.model.user.Friendship;
import org.springframework.data.repository.CrudRepository;

public interface FriendshipDao extends CrudRepository<Friendship, Integer> {
}
