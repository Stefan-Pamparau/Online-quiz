package com.iquest.service.impl;

import com.iquest.dao.FriendshipDao;
import com.iquest.model.user.Friendship;
import com.iquest.service.FriendshipService;
import com.iquest.service.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("friendshipService")
@Transactional
public class DefaultFriendshipService implements FriendshipService {

    @Autowired
    private FriendshipDao friendshipDao;

    @Override
    public Friendship save(Friendship friendship) {
        return friendshipDao.save(friendship);
    }

    @Override
    public Friendship findWithId(Integer id) {
        return friendshipDao.findOne(id);
    }

    @Override
    public Boolean exists(Integer id) {
        return friendshipDao.exists(id);
    }

    @Override
    public List<Friendship> findAll() {
        return ServiceUtil.convertFromIterableToList(friendshipDao.findAll());
    }

    @Override
    public Long getNumberOfFriendships() {
        return friendshipDao.count();
    }

    @Override
    public void delete(Integer id) {
        friendshipDao.delete(id);
    }

    @Override
    public void delete(Friendship friendship) {
        friendshipDao.delete(friendship);
    }

    @Override
    public void deleteAll() {
        friendshipDao.deleteAll();
    }
}
