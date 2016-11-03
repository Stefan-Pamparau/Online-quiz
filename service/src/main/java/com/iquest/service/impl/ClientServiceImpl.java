package com.iquest.service.impl;

import com.iquest.dao.ClientDao;
import com.iquest.model.user.Client;
import com.iquest.service.ClientService;
import com.iquest.service.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("clientService")
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientDao clientDao;

    @Override
    public Client save(Client client) {
        return clientDao.save(client);
    }

    @Override
    public Client findWithId(Integer id) {
        return clientDao.findOne(id);
    }

    @Override
    public Client findByEmail(String email) {
        List<Client> clients = clientDao.findByEmail(email);
        if(clients == null || clients.size() != 1) {
            return null;
        }
        return clients.get(0);
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
    public Long getNumberOfAdmins() {
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

    public ClientDao getClientDao() {
        return clientDao;
    }

    public void setClientDao(ClientDao clientDao) {
        this.clientDao = clientDao;
    }
}
