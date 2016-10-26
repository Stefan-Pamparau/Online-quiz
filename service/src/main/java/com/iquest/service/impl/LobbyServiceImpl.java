package com.iquest.service.impl;

import com.iquest.dao.LobbyDao;
import com.iquest.model.Lobby;
import com.iquest.service.LobbyService;
import com.iquest.service.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("lobbyService")
@Transactional
public class LobbyServiceImpl implements LobbyService {

    @Autowired
    private LobbyDao lobbyDao;

    @Override
    public List<Lobby> findByCreationDateGreaterThan(Date date) {
        return lobbyDao.findByCreationDateGreaterThan(date);
    }

    @Override
    public List<Lobby> findByCreationDateLessThan(Date date) {
        return lobbyDao.findByCreationDateLessThan(date);
    }

    @Override
    public List<Lobby> findByCreationDateBetween(Date min, Date max) {
        return findByCreationDateBetween(min, max);
    }

    @Override
    public Lobby save(Lobby lobby) {
        return lobbyDao.save(lobby);
    }

    @Override
    public Lobby findWithId(Integer id) {
        return lobbyDao.findOne(id);
    }

    @Override
    public Boolean exists(Integer id) {
        return lobbyDao.exists(id);
    }

    @Override
    public List<Lobby> findAll() {
        return ServiceUtil.convertFromIterableToList(lobbyDao.findAll());
    }

    @Override
    public Long getNumberOfAdmins() {
        return lobbyDao.count();
    }

    @Override
    public void delete(Integer id) {
        lobbyDao.delete(id);
    }

    @Override
    public void delete(Lobby lobby) {
        lobbyDao.delete(lobby);
    }

    @Override
    public void deleteAll() {
        lobbyDao.deleteAll();
    }

    public LobbyDao getLobbyDao() {
        return lobbyDao;
    }

    public void setLobbyDao(LobbyDao lobbyDao) {
        this.lobbyDao = lobbyDao;
    }
}
