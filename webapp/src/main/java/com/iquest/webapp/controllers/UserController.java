package com.iquest.webapp.controllers;

import com.iquest.model.user.User;
import com.iquest.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/confirm/{token}")
    public ResponseEntity<User> confirmUser(@PathVariable("token") String token) {
        logger.info(String.format("Attempting to confirm user with token %s", token));

        User user = userService.findByToken(token);
        if (user == null) {
            logger.info(String.format("User with token %s not found", token));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        logger.info(String.format("User with token %s found. Confirming him.", token));
        user.setConfirmed(true);
        userService.save(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("delete/all")
    public ResponseEntity<Void> deleteAllUsers() {
        userService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
