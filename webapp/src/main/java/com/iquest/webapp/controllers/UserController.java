package com.iquest.webapp.controllers;

import com.iquest.model.user.User;
import com.iquest.service.MailService;
import com.iquest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/confirm/{email}")
    public ResponseEntity<User> confirmUser(@PathVariable("email") String userEmail) {
        User user = userService.findByEmail(appendEmailDomain(userEmail));
        HttpHeaders httpHeaders = new HttpHeaders();

        if (user == null) {
            return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
        }

        user.setConfirmed(true);
        userService.save(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    private String appendEmailDomain(String userEmail) {
        return userEmail + ".com";
    }

    @DeleteMapping("delete/all")
    public ResponseEntity<Void> deleteAllUsers() {
        userService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
