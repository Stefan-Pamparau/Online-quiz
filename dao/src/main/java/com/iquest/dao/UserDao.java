package com.iquest.dao;

import com.iquest.model.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Integer> {
    User findByEmailAndPassword(String email, String password);

    User findByEmail(String email);
}
