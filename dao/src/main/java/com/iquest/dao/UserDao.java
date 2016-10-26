package com.iquest.dao;

import com.iquest.model.user.Admin;
import com.iquest.model.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDao extends CrudRepository<User, Integer> {
    List<Admin> findByEmailAndPassword(String email, String password);
}
