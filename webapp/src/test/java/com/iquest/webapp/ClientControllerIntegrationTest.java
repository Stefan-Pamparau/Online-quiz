package com.iquest.webapp;

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
public class ClientControllerIntegrationTest {

    private static int lastCreatedClient = 1;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static Client createClient() {
        Client client = new Client();
        client.setFirstName("First name " + lastCreatedClient);
        client.setSurname("Surname " + lastCreatedClient);
        client.setAge(25 + lastCreatedClient);
        client.setEmail("example@gmail.com " + lastCreatedClient);
        client.setPassword("password");
        client.setUserType(UserType.CLIENT);
        lastCreatedClient++;
        return client;
    }

    @After
    public void tearDown() {
        testRestTemplate.delete("/client/delete/all");
    }

    @Test
    public void testGetAll() throws IOException {
        Client client1 = createClient();
        Client client2 = createClient();
        HttpEntity<Client> clientHttpEntity1 = new HttpEntity<>(client1);
        HttpEntity<Client> clientHttpEntity2 = new HttpEntity<>(client2);
        client1 = testRestTemplate.postForObject("/client/insert", clientHttpEntity1, Client.class);
        client2 = testRestTemplate.postForObject("/client/insert", clientHttpEntity2, Client.class);
        List<Client> expectedList = new ArrayList<>();
        expectedList.add(client1);
        expectedList.add(client2);

        ResponseEntity<Client[]> receivedArray = testRestTemplate.getForEntity("/client/get/all", Client[].class);

        List<Client> sortedClients = Arrays.asList(receivedArray.getBody());
        Collections.sort(sortedClients, new UserComparator());
        Assert.assertEquals(expectedList, sortedClients);
    }

    @Test
    public void testGetAllNoClientsExist() throws IOException {
        ResponseEntity<Client[]> receivedArray = testRestTemplate.getForEntity("/client/get/all", Client[].class);
        Assert.assertEquals(HttpStatus.NO_CONTENT, receivedArray.getStatusCode().NO_CONTENT);
    }

    @Test
    public void testGetById() {
        Client client = createClient();
        HttpEntity<Client> clientHttpEntity = new HttpEntity<>(client);
        client = testRestTemplate.postForObject("/client/insert", clientHttpEntity, Client.class);

        Client receivedClient = testRestTemplate.getForObject("/client/get/" + client.getId(), Client.class);
        Assert.assertEquals(client, receivedClient);
    }

    @Test
    public void testGetByIdClientDoesNotExist() {
        ResponseEntity<Client> receivedClient = testRestTemplate.getForEntity("/client/get/1", Client.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, receivedClient.getStatusCode().NOT_FOUND);
    }

    @Test
    public void testInsert() throws IOException {
        Client client = createClient();
        HttpEntity<Client> clientHttpEntity = new HttpEntity<>(client);
        client = testRestTemplate.postForObject("/client/insert", clientHttpEntity, Client.class);

        Client receivedClient = testRestTemplate.getForObject("/client/get/" + client.getId(), Client.class);
        Assert.assertEquals(client, receivedClient);
    }

    @Test
    public void testInsertConflict() throws IOException {
        Client client1 = createClient();
        Client client2 = createClient();
        client2.setEmail(client1.getEmail());
        HttpEntity<Client> client1HttpEntity = new HttpEntity<>(client1);
        HttpEntity<Client> client2HttpEntity = new HttpEntity<>(client2);
        client1 = testRestTemplate.postForObject("/client/insert", client1HttpEntity, Client.class);
        client2 = testRestTemplate.postForObject("/client/insert", client2HttpEntity, Client.class);

        Client receivedClient = testRestTemplate.getForObject("/client/get/" + client1.getId(), Client.class);
        Assert.assertEquals(client1, receivedClient);
        Assert.assertNull(client2);
    }

    @Test
    public void testUpdate() throws IOException {
        Client client = createClient();
        HttpEntity<Client> clientHttpEntity = new HttpEntity<>(client);
        client = testRestTemplate.postForObject("/client/insert", clientHttpEntity, Client.class);
        client.setPassword("updatedPassword");
        HttpEntity<Client> updatedClientEntity = new HttpEntity<>(client);
        testRestTemplate.put("/client/update", updatedClientEntity, Client.class);

        Client receivedClient = testRestTemplate.getForObject("/client/get/" + client.getId(), Client.class);
        Assert.assertEquals(client, receivedClient);
    }

    @Test
    public void testUpdateClientDoesNotExist() throws IOException {
        Client client = createClient();
        client.setPassword("updatedPassword");
        HttpEntity<Client> updatedClientEntity = new HttpEntity<>(client);
        testRestTemplate.put("/client/update", updatedClientEntity, Client.class);

        ResponseEntity<Client> receivedClient = testRestTemplate.getForEntity("/client/get/" + client.getId(), Client.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, receivedClient.getStatusCode().NOT_FOUND);
    }

    @Test
    public void testDeleteAll() throws IOException {
        Client client = createClient();
        HttpEntity<Client> clientHttpEntity = new HttpEntity<>(client);
        client = testRestTemplate.postForObject("/client/insert", clientHttpEntity, Client.class);

        Client receivedClient = testRestTemplate.getForObject("/client/get/" + client.getId(), Client.class);
        Assert.assertEquals(client, receivedClient);

        testRestTemplate.delete("/client/delete/all", clientHttpEntity);
        Client deletedClient = testRestTemplate.getForObject("/client/get/" + client.getId(), Client.class);
        Assert.assertNull(deletedClient);
    }
}
