package com.iquest.webapp.controllers;

import com.iquest.model.quiz.Quiz;
import com.iquest.model.quiz.QuizType;
import com.iquest.model.user.Client;
import com.iquest.service.ClientService;
import com.iquest.webapp.dto.ClientWithQuizzesDto;
import com.iquest.webapp.util.ModelToDtoConverter;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
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
@RequestMapping(path = "/client")
@CrossOrigin(origins = "*")
public class ClientController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Client>> getAllClients() {
        logger.info("Retrieving all clients");

        List<Client> clients = clientService.findAll();
        if (clients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Client> getClientWithId(@PathVariable("id") Integer id) {
        logger.info(String.format("Retrieving client with id %s", id));

        Client client = clientService.findWithId(id);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<Client> insertClient(@RequestBody Client client) {
        logger.info(String.format("Inserting client %s", client));
        HttpHeaders httpHeaders = new HttpHeaders();

        if (clientService.save(client) == null) {
            return new ResponseEntity<>(null, httpHeaders, HttpStatus.CONFLICT);
        }

        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentContextPath().path("client/get/{id}").buildAndExpand(client.getId()).toUri());
        return new ResponseEntity<>(client, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Client> updateClient(@RequestBody Client client) {
        logger.info(String.format("Updating client %s", client));

        Client persistedClient = clientService.findByEmail(client.getEmail());
        if (persistedClient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        prepareClientForUpdate(client, persistedClient);
        clientService.save(persistedClient);
        return new ResponseEntity<>(persistedClient, HttpStatus.OK);
    }

    private void prepareClientForUpdate(Client client, Client persistedClient) {
        persistedClient.setFirstName(client.getFirstName());
        persistedClient.setSurname(client.getSurname());
        persistedClient.setAge(client.getAge());
        persistedClient.setEmail(client.getEmail());
        persistedClient.setPassword(client.getPassword());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteClient(@RequestBody Client client) {
        logger.info(String.format("Deleting client %s", client));

        Client persistedClient = clientService.findByEmail(client.getEmail());
        if (persistedClient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        clientService.delete(persistedClient);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllClients() {
        logger.info("Deleting all clients");
        clientService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/add/friend")
    public ResponseEntity<Void> addFriend(HttpServletRequest request, @RequestBody Client friend) {
        logger.info(String.format("Adding friend %s", friend));

        Client requester = clientService.findByEmail(request.getUserPrincipal().getName());
        clientService.addFriend(requester, friend);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/clientWithQuizzes")
    public ResponseEntity<ClientWithQuizzesDto> getClientWithQuizzes() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientService.findByEmail(email);
        if (client == null) {
            return new ResponseEntity<ClientWithQuizzesDto>(HttpStatus.NOT_FOUND);
        }

        ClientWithQuizzesDto clientWithQuizzesDto = constructClientWithQuizzesDto(client);

        return new ResponseEntity<ClientWithQuizzesDto>(clientWithQuizzesDto, HttpStatus.OK);
    }

    private ClientWithQuizzesDto constructClientWithQuizzesDto(Client client) {
        ClientWithQuizzesDto clientWithQuizzesDto = new ClientWithQuizzesDto();
        clientWithQuizzesDto.setClientDto(ModelToDtoConverter.convertToClientDto(client));
        clientWithQuizzesDto.setExamQuizDtos(new ArrayList<>());
        clientWithQuizzesDto.setGamefiedQuizDtos(new ArrayList<>());

        for (Quiz quiz : client.getQuizzes()) {
            if (QuizType.EXAM_QUIZ == quiz.getQuizType()) {
                clientWithQuizzesDto.getExamQuizDtos().add(ModelToDtoConverter.convertToExamQuizDto(quiz));
            } else {
                clientWithQuizzesDto.getGamefiedQuizDtos().add(ModelToDtoConverter.convertToGamefiedQuizDto(quiz));
            }
        }
        return clientWithQuizzesDto;
    }
}