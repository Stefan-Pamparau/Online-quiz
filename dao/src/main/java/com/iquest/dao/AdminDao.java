package com.iquest.dao;

import com.iquest.model.user.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdminDao extends CrudRepository<Admin, Integer> {
}
