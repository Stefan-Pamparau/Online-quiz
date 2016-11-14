package com.iquest.controllers;

import com.iquest.model.user.Client;
import com.iquest.service.ClientService;
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
@RequestMapping(path = "/client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.findAll();
        if (clients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Client> getClientWithId(@PathVariable("id") Integer id) {
        Client client = clientService.findWithId(id);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<Client> insertClient(@RequestBody Client client) {
        clientService.save(client);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentServletMapping().path("/get/{id}").buildAndExpand(client.getId()).toUri());

        return new ResponseEntity<>(client, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Client> updateClient(@RequestBody Client client) {
        Client persistedClient = clientService.findByEmail(client.getEmail());
        if (persistedClient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        persistedClient.setFirstName(client.getFirstName());
        persistedClient.setSurname(client.getSurname());
        persistedClient.setAge(client.getAge());
        persistedClient.setEmail(client.getEmail());
        persistedClient.setPassword(client.getPassword());

        clientService.save(persistedClient);
        return new ResponseEntity<>(persistedClient, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteClient(@RequestBody Client client) {
        Client persistedClient = clientService.findByEmail(client.getEmail());
        if (persistedClient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        clientService.delete(persistedClient);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllClients() {
        clientService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}