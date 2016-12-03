package com.iquest.webapp.controllers;

import com.iquest.model.user.Admin;
import com.iquest.model.user.Client;
import com.iquest.model.user.User;
import com.iquest.model.user.UserType;
import com.iquest.service.AdminService;
import com.iquest.service.ClientService;
import com.iquest.service.UserService;
import com.iquest.webapp.dto.LoginDto;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController extends AbstractController {

    private UserService userService;
    private AdminService adminService;
    private ClientService clientService;

    @Autowired
    public LoginController(UserService userService, AdminService adminService, ClientService clientService) {
        this.userService = userService;
        this.adminService = adminService;
        this.clientService = clientService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(HttpServletRequest request, @RequestBody LoginDto loginDto) {
        User user = userService.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
        if (user != null) {
            if (UserType.ADMIN == user.getUserType()) {
                Admin admin = adminService.findByEmail(loginDto.getEmail());
                request.getSession(true).setAttribute(LOGGED_USER_KEY, admin);
            } else {
                Client client = clientService.findByEmail(loginDto.getEmail());
                request.getSession(true).setAttribute(LOGGED_USER_KEY, client);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}