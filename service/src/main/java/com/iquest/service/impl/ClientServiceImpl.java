package com.iquest.service.impl;

import com.iquest.dao.ClientDao;
import com.iquest.model.user.Client;
import com.iquest.model.user.Friendship;
import com.iquest.service.ClientService;
import com.iquest.service.FriendshipService;
import com.iquest.service.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("clientService")
@Transactional
public class ClientServiceImpl implements ClientService {

    private ClientDao clientDao;

    private FriendshipService friendshipService;

    @Autowired
    public ClientServiceImpl(ClientDao clientDao, FriendshipService friendshipService) {
        this.clientDao = clientDao;
        this.friendshipService = friendshipService;
    }

    @Override
    public Client save(Client client) {
        if(findByEmail(client.getEmail()) != null) {
            return null;
        }
        return clientDao.save(client);
    }

    @Override
    public Client findWithId(Integer id) {
        return clientDao.findOne(id);
    }

    @Override
    public Client findByEmail(String email) {
        return clientDao.findByEmail(email);
    }

    @Override
    public Boolean exists(Integer id) {
        return clientDao.exists(id);
    }

    @Override
    public List<Client> findAll() {
        return ServiceUtil.convertFromIterableToList(clientDao.findAll());
    }

    @Override
    public Long getNumberOfClients() {
        return clientDao.count();
    }

    @Override
    public void delete(Integer id) {
        clientDao.delete(id);
    }

    @Override
    public void delete(Client client) {
        clientDao.delete(client);
    }

    @Override
    public void deleteAll() {
        clientDao.deleteAll();
    }

    @Override
    public void addFriend(Client requester, Client friend) {
        Friendship friendship = new Friendship();
        friendship.setRequester(requester);
        friendship.setFriend(friend);
//        friendshipService.save(friendship);
        requester.addFriendship(friendship);
        friend.addFriendship(friendship);
        save(requester);
        save(friend);
    }

    public ClientDao getClientDao() {
        return clientDao;
    }

    public void setClientDao(ClientDao clientDao) {
        this.clientDao = clientDao;
    }
}
