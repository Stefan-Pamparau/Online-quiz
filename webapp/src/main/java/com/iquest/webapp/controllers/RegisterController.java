package com.iquest.webapp.controllers;

import com.iquest.model.user.User;
import com.iquest.service.MailService;
import com.iquest.service.RegisterService;
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

@RestController
@CrossOrigin(origins = "*")
public class RegisterController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    private static final String CONFIRMATION_MAIL_SUBJECT = "Online quiz confirmation";
    private static final String USER_CONFIRM_LINK = "<form action=\"http://localhost:8080/user/confirm/%s\" method=\"post\"><input type=\"submit\" value=\"Confirm email\"/></form>";

    private RegisterService registerService;
    private MailService mailService;

    @Autowired
    public RegisterController(RegisterService registerService, MailService mailService) {
        this.registerService = registerService;
        this.mailService = mailService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        User user = DtoToModelConverter.convertToUser(userDto);
        user = registerService.register(user);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        logger.info(String.format("Sending confirmation email to %s", user.getEmail()));
        mailService.sendMail(user.getEmail(), CONFIRMATION_MAIL_SUBJECT, String.format(USER_CONFIRM_LINK, user.getToken()));
        return new ResponseEntity<>(ModelToDtoConverter.convertToUserDto(user), HttpStatus.CREATED);
    }
}
