package com.iquest.webapp.controllers;

import com.iquest.model.user.Admin;
import com.iquest.model.user.Friendship;
import com.iquest.service.AdminService;
import com.iquest.webapp.dto.CompleteAdminDto;
import com.iquest.webapp.dto.frommodel.FriendshipDto;
import com.iquest.webapp.util.ModelToDtoConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admin")
@CrossOrigin(origins = "*")
public class AdminController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        logger.info("Retrieving all admins from the database");

        List<Admin> admins = adminService.findAll();
        if (admins.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Admin> getAdminWithId(@PathVariable("id") Integer id) {
        logger.info(String.format("Retrieving admin with id %s", id));

        Admin admin = adminService.findWithId(id);
        if (admin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<Admin> insertAdmin(@RequestBody Admin admin) {
        logger.info(String.format("Inserting admin %s", admin));
        HttpHeaders httpHeaders = new HttpHeaders();

        if (adminService.save(admin) == null) {
            return new ResponseEntity<>(null, httpHeaders, HttpStatus.CONFLICT);
        }

        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentContextPath().path("admin/get/{id}").buildAndExpand(admin.getId()).toUri());
        return new ResponseEntity<>(admin, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Admin> updateAdmin(@RequestBody Admin admin) {
        logger.info(String.format("Updating admin %s", admin));

        Admin persistedAdmin = adminService.findByEmail(admin.getEmail());
        if (persistedAdmin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        prepareAdminForUpdate(admin, persistedAdmin);
        adminService.save(persistedAdmin);
        return new ResponseEntity<>(persistedAdmin, HttpStatus.OK);
    }

    private void prepareAdminForUpdate(Admin admin, Admin persistedAdmin) {
        persistedAdmin.setFirstName(admin.getFirstName());
        persistedAdmin.setSurname(admin.getSurname());
        persistedAdmin.setAge(admin.getAge());
        persistedAdmin.setEmail(admin.getEmail());
        persistedAdmin.setPassword(admin.getPassword());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAdmin(@RequestBody Admin admin) {
        logger.info(String.format("Deleting admin %s", admin));

        Admin persistedAdmin = adminService.findByEmail(admin.getEmail());
        if (persistedAdmin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        adminService.delete(persistedAdmin);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllAdmins() {
        logger.info("Deleting all admins");
        adminService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/completeAdmin")
    public ResponseEntity<CompleteAdminDto> getClientWithQuizzes() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Admin admin = adminService.findByEmail(email);
        if (admin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CompleteAdminDto completeAdminDto = constructCompleteAdminDto(admin);

        return new ResponseEntity<>(completeAdminDto, HttpStatus.OK);
    }

    private CompleteAdminDto constructCompleteAdminDto(Admin admin) {
        CompleteAdminDto completeAdminDto = new CompleteAdminDto();
        completeAdminDto.setAdminDto(ModelToDtoConverter.convertToAdminDto(admin));
        completeAdminDto.setFriendshipDtos(new ArrayList<>());

        for (Friendship friendship : admin.getFriendships()) {
            FriendshipDto friendshipDto = new FriendshipDto();
            friendshipDto.setRequester(ModelToDtoConverter.convertToUserDto(friendship.getRequester()));
            friendshipDto.setFriend(ModelToDtoConverter.convertToUserDto(friendship.getFriend()));
            completeAdminDto.getFriendshipDtos().add(friendshipDto);
        }

        return completeAdminDto;
    }
}
