package com.iquest.service.impl;

import com.iquest.dao.UserDao;
import com.iquest.model.user.Friendship;
import com.iquest.model.user.User;
import com.iquest.service.FriendshipService;
import com.iquest.service.UserService;
import com.iquest.service.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private FriendshipService friendshipService;

    @Autowired
    public UserServiceImpl(UserDao userDao, FriendshipService friendshipService) {
        this.userDao = userDao;
        this.friendshipService = friendshipService;
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        return userDao.findByEmailAndPassword(email, password);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User findByToken(String token) {
        return userDao.findByToken(token);
    }

    @Override
    public User save(User user) {
        if (findByEmail(user.getEmail()) != null) {
            return null;
        }
        return userDao.save(user);
    }

    @Override
    public User update(User user) {
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

    @Override
    public List<User> findByEmailContaining(String pattern) {
        return userDao.findByEmailContaining(pattern);
    }

    @Override
    public void addFriend(User requester, User friend) {
        Friendship friendship = new Friendship();
        friendship.setRequester(requester);
        friendship.setFriend(friend);
        friendship.getId().setRequesterId(requester.getId());
        friendship.getId().setFriendId(friend.getId());
        friendshipService.save(friendship);
        requester.addFriendship(friendship);
        friend.addFriendship(friendship);
        update(requester);
        update(friend);
    }

    @Override
    public void removeFriend(User requester, User friend) {
        User persistedUser = findByEmail(requester.getEmail());
        Friendship toBeRemoved = null;
        for (Friendship friendship : persistedUser.getFriendships()) {
            if (friendship.getFriend().getEmail().equals(friend.getEmail())) {
                toBeRemoved = friendship;
            }
        }
        if (toBeRemoved != null) {
            requester.getFriendships().remove(toBeRemoved);
            update(requester);
            friendshipService.delete(toBeRemoved);
        }
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
