package com.iquest.controllers;

import com.iquest.model.user.Admin;
import com.iquest.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/get/all")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.findAll();
        if (admins.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Admin> getAdminWithId(@PathVariable("id") Integer id) {
        Admin admin = adminService.findWithId(id);
        if (admin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<Admin> insertAdmin(@RequestBody Admin admin) {
        if (adminService.findByEmail(admin.getEmail()) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        adminService.save(admin);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentServletMapping().path("get/{id}").buildAndExpand(admin.getId()).toUri());

        return new ResponseEntity<>(admin, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Admin> updateAdmin(@RequestBody Admin admin) {
        Admin persistedAdmin = adminService.findByEmail(admin.getEmail());
        if (persistedAdmin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        persistedAdmin.setFirstName(admin.getFirstName());
        persistedAdmin.setSurname(admin.getSurname());
        persistedAdmin.setAge(admin.getAge());
        persistedAdmin.setEmail(admin.getEmail());
        persistedAdmin.setPassword(admin.getPassword());
        adminService.save(admin);
        return new ResponseEntity<>(persistedAdmin, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Admin> deleteAdmin(@RequestBody Admin admin) {
        Admin persistedAdmin = adminService.findByEmail(admin.getEmail());
        if (persistedAdmin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        adminService.delete(admin);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Admin> deleteAllAdmins() {
        adminService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
