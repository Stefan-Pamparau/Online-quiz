package com.iquest.webapp;

import com.iquest.model.user.Admin;
import com.iquest.model.user.Client;
import com.iquest.model.user.UserType;
import com.iquest.webapp.comparator.UserComparator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminControllerIntegrationTest {

    private static int lastCreatedAdmin = 1;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static Admin createAdmin() {
        Admin admin = new Admin();
        admin.setFirstName("First name " + lastCreatedAdmin);
        admin.setSurname("Surname " + lastCreatedAdmin);
        admin.setAge(25 + lastCreatedAdmin);
        admin.setEmail("example@gmail.com " + lastCreatedAdmin);
        admin.setPassword("password");
        admin.setUserType(UserType.ADMIN);
        lastCreatedAdmin++;
        return admin;
    }

    @After
    public void tearDown() {
        testRestTemplate.delete("/admin/delete/all");
    }

    @Test
    public void testGetAll() throws IOException {
        Admin admin1 = createAdmin();
        Admin admin2 = createAdmin();
        HttpEntity<Admin> adminHttpEntity1 = new HttpEntity<>(admin1);
        HttpEntity<Admin> adminHttpEntity2 = new HttpEntity<>(admin2);
        admin1 = testRestTemplate.postForObject("/admin/insert", adminHttpEntity1, Admin.class);
        admin2 = testRestTemplate.postForObject("/admin/insert", adminHttpEntity2, Admin.class);
        List<Admin> expectedList = new ArrayList<>();
        expectedList.add(admin1);
        expectedList.add(admin2);

        ResponseEntity<Admin[]> receivedArray = testRestTemplate.getForEntity("/admin/get/all", Admin[].class);
        List<Admin> sortedAdmins = Arrays.asList(receivedArray.getBody());
        Collections.sort(sortedAdmins, new UserComparator());
        Assert.assertEquals(expectedList, sortedAdmins);
    }

    @Test
    public void testGetAllNoAdminsExist() throws IOException {
        ResponseEntity<Admin[]> receivedArray = testRestTemplate.getForEntity("/admin/get/all", Admin[].class);
        Assert.assertEquals(HttpStatus.NO_CONTENT, receivedArray.getStatusCode().NO_CONTENT);
    }

    @Test
    public void testGetById() {
        Admin admin = createAdmin();
        HttpEntity<Admin> adminHttpEntity = new HttpEntity<>(admin);
        admin = testRestTemplate.postForObject("/admin/insert", adminHttpEntity, Admin.class);

        Admin receivedAdmin = testRestTemplate.getForObject("/admin/get/" + admin.getId(), Admin.class);
        Assert.assertEquals(admin, receivedAdmin);
    }

    @Test
    public void testGetByIdAdminDoesNotExist() {
        ResponseEntity<Client> receivedClient = testRestTemplate.getForEntity("/client/get/1", Client.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, receivedClient.getStatusCode().NOT_FOUND);
    }

    @Test
    public void testInsert() throws IOException {
        Admin admin = createAdmin();
        HttpEntity<Admin> adminHttpEntity = new HttpEntity<>(admin);
        admin = testRestTemplate.postForObject("/admin/insert", adminHttpEntity, Admin.class);

        Admin receivedAdmin = testRestTemplate.getForObject("/admin/get/" + admin.getId(), Admin.class);
        Assert.assertEquals(admin, receivedAdmin);
    }

    @Test
    public void testInsertConflict() throws IOException {
        Admin admin1 = createAdmin();
        Admin admin2 = createAdmin();
        admin2.setEmail(admin1.getEmail());
        HttpEntity<Admin> admin1HttpEntity = new HttpEntity<>(admin1);
        HttpEntity<Admin> admin2HttpEntity = new HttpEntity<>(admin2);
        admin1 = testRestTemplate.postForObject("/admin/insert", admin1HttpEntity, Admin.class);
        admin2 = testRestTemplate.postForObject("/admin/insert", admin2HttpEntity, Admin.class);

        Admin receivedAdmin = testRestTemplate.getForObject("/admin/get/" + admin1.getId(), Admin.class);
        Assert.assertEquals(admin1, receivedAdmin);
        Assert.assertNull(admin2);
    }

    @Test
    public void testUpdate() throws IOException {
        Admin admin = createAdmin();
        HttpEntity<Admin> adminHttpEntity = new HttpEntity<>(admin);
        admin = testRestTemplate.postForObject("/admin/insert", adminHttpEntity, Admin.class);
        admin.setPassword("updatedPassword");
        HttpEntity<Admin> updatedClientEntity = new HttpEntity<>(admin);
        testRestTemplate.put("/admin/update", updatedClientEntity, Client.class);

        Admin receivedAdmin = testRestTemplate.getForObject("/admin/get/" + admin.getId(), Admin.class);
        Assert.assertEquals(admin, receivedAdmin);
    }

    @Test
    public void testUpdateAdminDoesNotExist() throws IOException {
        Admin admin = createAdmin();
        admin.setPassword("updatedPassword");
        HttpEntity<Admin> updatedClientEntity = new HttpEntity<>(admin);
        testRestTemplate.put("/admin/update", updatedClientEntity, Admin.class);

        ResponseEntity<Client> receivedAdmin = testRestTemplate.getForEntity("/admin/get/" + admin.getId(), Client.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, receivedAdmin.getStatusCode().NOT_FOUND);
    }

    @Test
    public void testDeleteAll() throws IOException {
        Admin admin = createAdmin();
        HttpEntity<Admin> adminHttpEntity = new HttpEntity<>(admin);
        admin = testRestTemplate.postForObject("/admin/insert", adminHttpEntity, Admin.class);

        Admin receivedAdmin = testRestTemplate.getForObject("/admin/get/" + admin.getId(), Admin.class);
        Assert.assertEquals(admin, receivedAdmin);

        testRestTemplate.delete("/admin/delete/all", adminHttpEntity);
        Client deletedAdmin = testRestTemplate.getForObject("/admin/get/" + admin.getId(), Client.class);
        Assert.assertNull(deletedAdmin);
    }
}
