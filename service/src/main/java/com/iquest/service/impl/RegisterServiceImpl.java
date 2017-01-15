package com.iquest.service.impl;

import com.iquest.model.user.Admin;
import com.iquest.model.user.Client;
import com.iquest.model.user.User;
import com.iquest.model.user.UserType;
import com.iquest.service.AdminService;
import com.iquest.service.ClientService;
import com.iquest.service.RegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigInteger;
import java.security.SecureRandom;

@Service("registerService")
@Transactional
public class RegisterServiceImpl implements RegisterService {

    private static final Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);

    private ClientService clientService;
    private AdminService adminService;
    private SecureRandom tokenGenerator;

    @Autowired
    public RegisterServiceImpl(ClientService clientService, AdminService adminService) {
        this.clientService = clientService;
        this.adminService = adminService;
        tokenGenerator = new SecureRandom();
    }

    @Override
    public User register(User user) {
        if (UserType.ADMIN == user.getUserType()) {
            logger.info(String.format("Registering admin %s", user));
            if (registerAdmin(user)) {
                return null;
            }
        } else {
            logger.info(String.format("Registering client %s", user));
            if (registerClient(user)) {
                return null;
            }
        }

        return user;
    }

    private boolean registerAdmin(@RequestBody User user) {
        user.setToken(generateToken());
        Admin admin = convertUserToAdmin(user);
        return adminService.save(admin) == null;
    }

    private boolean registerClient(@RequestBody User user) {
        user.setToken(generateToken());
        Client client = convertUserToClient(user);
        return clientService.save(client) == null;
    }


    private String generateToken() {
        logger.info("Generating token used for confirmation");
        return new BigInteger(130, tokenGenerator).toString(32);
    }

    private Admin convertUserToAdmin(User user) {
        Admin admin = new Admin();

        admin.setFirstName(user.getFirstName());
        admin.setSurname(user.getSurname());
        admin.setEmail(user.getEmail());
        admin.setPassword(user.getPassword());
        admin.setAge(user.getAge());
        admin.setUserType(UserType.ADMIN);
        admin.setConfirmed(user.getConfirmed());
        admin.setToken(user.getToken());

        return admin;
    }

    private Client convertUserToClient(User user) {
        Client client = new Client();

        client.setFirstName(user.getFirstName());
        client.setSurname(user.getSurname());
        client.setEmail(user.getEmail());
        client.setPassword(user.getPassword());
        client.setAge(user.getAge());
        client.setUserType(UserType.CLIENT);
        client.setConfirmed(user.getConfirmed());
        client.setToken(user.getToken());

        return client;
    }
}
