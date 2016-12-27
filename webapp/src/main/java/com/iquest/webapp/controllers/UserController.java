package com.iquest.webapp.controllers;

import com.iquest.model.user.User;
import com.iquest.service.MailService;
import com.iquest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private MailService mailService;

    @Autowired
    public UserController(UserService userService, MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }

    @PostMapping("/confirm/{token}")
    public ResponseEntity<User> confirmUser(@PathVariable("token") String token) {
        User user = userService.findByToken(token);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

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
