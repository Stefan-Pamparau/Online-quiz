package com.iquest.dao;

import com.iquest.model.user.Admin;
import org.springframework.data.repository.CrudRepository;

public interface AdminDao extends CrudRepository<Admin, Integer> {
    Admin findByEmail(String email);
}
