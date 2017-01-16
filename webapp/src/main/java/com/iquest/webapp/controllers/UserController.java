package com.iquest.webapp.controllers;

import com.iquest.model.user.User;
import com.iquest.model.user.UserType;
import com.iquest.service.UserService;
import com.iquest.webapp.dto.UsersDto;
import com.iquest.webapp.dto.frommodel.UserDto;
import com.iquest.webapp.util.DtoToModelConverter;
import com.iquest.webapp.util.ModelToDtoConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/find/email/pattern/{pattern}")
    public ResponseEntity<UsersDto> findUsersByEmailPattern(@PathVariable("pattern") String pattern) {
        List<User> users = userService.findByEmailContaining(pattern);
        UsersDto usersDto = new UsersDto();
        usersDto.setUserDtos(new ArrayList<>());
        users.forEach(user -> usersDto.getUserDtos().add(ModelToDtoConverter.convertToUserDto(user)));

        return new ResponseEntity<UsersDto>(usersDto, HttpStatus.OK);
    }

    @PutMapping("/add/friend")
    public ResponseEntity<Void> addFriend(@RequestBody UserDto friend) {
        logger.info(String.format("Adding friend %s", friend));
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User requester = userService.findByEmail(email);
        if (UserType.ADMIN == friend.getUserType()) {
            userService.addFriend(requester, DtoToModelConverter.convertToAdmin(friend));
        } else {
            userService.addFriend(requester, DtoToModelConverter.convertToClient(friend));
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/remove/friend")
    public ResponseEntity<Void> removeFriend(@RequestBody UserDto friend) {
        logger.info(String.format("Removing friend %s", friend));
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User requester = userService.findByEmail(email);
        userService.removeFriend(requester, DtoToModelConverter.convertToUser(friend));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("delete/all")
    public ResponseEntity<Void> deleteAllUsers() {
        userService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
