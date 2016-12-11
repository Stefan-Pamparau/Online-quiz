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

    private static final String CONFIRMATION_MAIL_SUBJECT = "Online quiz confirmation";
    private static final String USER_CONFIRM_LINK = "<form action=\"http://localhost:8080/user/confirm/%s\" method=\"post\"><input type=\"submit\" value=\"Confirm email\"/></form>";

    private UserService userService;
    private MailService mailService;

    @Autowired
    public UserController(UserService userService, MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (userService.save(user) == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        mailService.sendMail(user.getEmail(), CONFIRMATION_MAIL_SUBJECT, String.format(USER_CONFIRM_LINK, user.getEmail()));
        return new ResponseEntity<>(user, httpHeaders, HttpStatus.CREATED);
    }

    @PostMapping("/confirm/{email}")
    public ResponseEntity<User> confirmUser(@PathVariable("email") String userEmail) {
        User user = userService.findByEmail(userEmail);
        HttpHeaders httpHeaders = new HttpHeaders();

        if (user == null) {
            return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
        }

        user.setConfirmed(true);
        userService.save(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
