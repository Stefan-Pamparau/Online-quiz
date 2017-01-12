package com.iquest.webapp.controllers;

import com.iquest.model.user.Admin;
import com.iquest.model.user.Client;
import com.iquest.model.user.User;
import com.iquest.model.user.UserType;
import com.iquest.service.AdminService;
import com.iquest.service.ClientService;
import com.iquest.service.MailService;
import com.iquest.webapp.dto.frommodel.UserDto;
import com.iquest.webapp.util.DtoToModelConverter;
import com.iquest.webapp.util.ModelToDtoConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.security.SecureRandom;

@RestController
@CrossOrigin(origins = "*")
public class RegisterController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    private static final String CONFIRMATION_MAIL_SUBJECT = "Online quiz confirmation";
    private static final String USER_CONFIRM_LINK = "<form action=\"http://localhost:8080/user/confirm/%s\" method=\"post\"><input type=\"submit\" value=\"Confirm email\"/></form>";

    private AdminService adminService;
    private ClientService clientService;
    private MailService mailService;
    private SecureRandom tokenGenerator;

    @Autowired
    public RegisterController(AdminService adminService, ClientService clientService, MailService mailService) {
        this.adminService = adminService;
        this.clientService = clientService;
        this.mailService = mailService;
        tokenGenerator = new SecureRandom();
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        User user = DtoToModelConverter.convertUserDto(userDto);
        user.setToken(generateToken());

        if (UserType.ADMIN == user.getUserType()) {
            logger.info(String.format("Registering admin %s", user));
            if (registerAdmin(user)) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } else {
            logger.info(String.format("Registering client %s", user));
            if (registerClient(user)) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }

        logger.info(String.format("Sending confirmation email to %s", user.getEmail()));
        mailService.sendMail(user.getEmail(), CONFIRMATION_MAIL_SUBJECT, String.format(USER_CONFIRM_LINK, user.getToken()));
        return new ResponseEntity<>(ModelToDtoConverter.convertToUserDto(user), HttpStatus.CREATED);
    }

    private boolean registerAdmin(@RequestBody User user) {
        Admin admin = convertUserToAdmin(user);
        return adminService.save(admin) == null;
    }

    private boolean registerClient(@RequestBody User user) {
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
