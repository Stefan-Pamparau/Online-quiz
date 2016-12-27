package com.iquest.webapp.controllers;

import com.iquest.model.user.User;
import com.iquest.service.UserService;
import com.iquest.webapp.dto.LoginDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginDto loginDto) {
        logger.info(String.format("Attempting to login with credentials %s", loginDto));
        User user = userService.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
        if (user != null) {
            logger.info(String.format("Login with credentials %s successful", loginDto));
            return new ResponseEntity<>(HttpStatus.OK);
        }
        logger.info(String.format("Login with credentials %s unsuccessful", loginDto));
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}