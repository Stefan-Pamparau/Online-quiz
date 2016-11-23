package com.iquest.webapp.controllers;

import com.iquest.model.user.Admin;
import com.iquest.model.user.Client;
import com.iquest.model.user.User;
import com.iquest.model.user.UserType;
import com.iquest.service.AdminService;
import com.iquest.service.ClientService;
import com.iquest.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class LoginController extends AbstractController {

    private UserService userService;
    private AdminService adminService;
    private ClientService clientService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ResponseEntity<User> login(HttpServletRequest request, @RequestParam(name = "email", required = true) String email, @RequestParam(name = "password", required = true) String password) {
        User user = userService.findByEmailAndPassword(email, password);
        if (user != null) {
            if (UserType.ADMIN == user.getUserType()) {
                Admin admin = adminService.findByEmail(email);
                request.getSession(true).setAttribute(LOGGED_USER_KEY, admin);
            } else {
                Client client = clientService.findByEmail(email);
                request.getSession(true).setAttribute(LOGGED_USER_KEY, client);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}