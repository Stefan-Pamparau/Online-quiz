package com.iquest.service.impl;

import com.iquest.model.user.Admin;
import com.iquest.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Override
    public Admin save(Admin admin) {
        return null;
    }

    @Override
    public Admin findWithId(Integer id) {
        return null;
    }

    @Override
    public Boolean exists(Integer id) {
        return null;
    }

    @Override
    public List<Admin> findAll() {
        return null;
    }

    @Override
    public Long getNumberOfAdmins() {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void delete(Admin admin) {

    }

    @Override
    public void deleteAll() {

    }
}
