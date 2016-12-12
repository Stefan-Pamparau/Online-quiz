package com.iquest.service.impl;

import com.iquest.dao.LobbyDao;
import com.iquest.dao.UserLobbySessionDao;
import com.iquest.model.Lobby;
import com.iquest.model.user.UserLobbySession;
import com.iquest.service.LobbyService;
import com.iquest.service.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("lobbyService")
@Transactional
public class LobbyServiceImpl implements LobbyService {

    private LobbyDao lobbyDao;
    private UserLobbySessionDao userLobbySessionDao;

    @Autowired
    public LobbyServiceImpl(LobbyDao lobbyDao, UserLobbySessionDao userLobbySessionDao) {
        this.lobbyDao = lobbyDao;
        this.userLobbySessionDao = userLobbySessionDao;
    }

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
        if (lobby.getUsers() != null) {
            List<UserLobbySession> users = new ArrayList<>(lobby.getUsers());
            lobby.setUsers(null);
            lobby = lobbyDao.save(lobby);
            saveUserLobbySessions(lobby, users);
        } else {
            lobby = lobbyDao.save(lobby);
        }
        return lobby;
    }

    private void saveUserLobbySessions(Lobby lobby, List<UserLobbySession> users) {
        for (UserLobbySession userLobbySession : users) {
            userLobbySession.getId().setLobbyId(lobby.getId());
            userLobbySession.getId().setUserId(userLobbySession.getUser().getId());
            userLobbySessionDao.save(userLobbySession);
        }
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
    public Long getNumberOfLobbies() {
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
