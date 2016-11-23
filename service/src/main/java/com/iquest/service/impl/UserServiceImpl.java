package com.iquest.service.impl;

import com.iquest.dao.UserDao;
import com.iquest.model.user.User;
import com.iquest.service.UserService;
import com.iquest.service.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findByEmailAndPassword(String email, String password) {
        List<User> users = userDao.findByEmailAndPassword(email, password);

        if (users == null || users.size() != 1) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public List<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return userDao.save(user);
    }

    @Override
    public User findWithId(Integer id) {
        return userDao.findOne(id);
    }

    @Override
    public Boolean exists(Integer id) {
        return userDao.exists(id);
    }

    @Override
    public List<User> findAll() {
        return ServiceUtil.convertFromIterableToList(userDao.findAll());
    }

    @Override
    public Long getNumberOfUsers() {
        return userDao.count();
    }

    @Override
    public void delete(Integer id) {
        userDao.delete(id);
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    public void deleteAll() {
        userDao.deleteAll();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
