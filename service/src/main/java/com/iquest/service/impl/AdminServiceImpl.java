package com.iquest.service.impl;

import com.iquest.dao.AdminDao;
import com.iquest.model.user.Admin;
import com.iquest.service.AdminService;
import com.iquest.service.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {

    private AdminDao adminDao;

    @Autowired
    public AdminServiceImpl(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @Override
    public Admin save(Admin admin) {
        return adminDao.save(admin);
    }

    @Override
    public Admin findWithId(Integer id) {
        return adminDao.findOne(id);
    }

    @Override
    public Admin findByEmail(String email) {
        return adminDao.findByEmail(email);
    }

    @Override
    public Boolean exists(Integer id) {
        return adminDao.exists(id);
    }

    @Override
    public List<Admin> findAll() {
        return ServiceUtil.convertFromIterableToList(adminDao.findAll());
    }

    @Override
    public Long getNumberOfAdmins() {
        return adminDao.count();
    }

    @Override
    public void delete(Integer id) {
        adminDao.delete(id);
    }

    @Override
    public void delete(Admin admin) {
        adminDao.delete(admin);
    }

    @Override
    public void deleteAll() {
        adminDao.deleteAll();
    }

    public AdminDao getAdminDao() {
        return adminDao;
    }

    public void setAdminDao(AdminDao adminDao) {
        this.adminDao = adminDao;
    }
}
