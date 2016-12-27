package com.iquest.webapp.controllers;

import com.iquest.model.Lobby;
import com.iquest.service.LobbyService;
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
@RequestMapping(path = "/lobby")
public class LobbyController {

    private final LobbyService lobbyService;

    @Autowired
    public LobbyController(LobbyService lobbyService) {
        this.lobbyService = lobbyService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Lobby>> getAllLobbies() {
        List<Lobby> lobbies = lobbyService.findAll();
        if (lobbies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lobbies, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Lobby> getLobbyWithId(@PathVariable("id") Integer id) {
        Lobby lobby = lobbyService.findWithId(id);
        if (lobby == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(lobby, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<Lobby> insertLobby(@RequestBody Lobby lobby) {
        lobbyService.save(lobby);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentServletMapping().path("/get/{id}").buildAndExpand(lobby.getId()).toUri());

        return new ResponseEntity<>(lobby, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Lobby> updateLobby(@RequestBody Lobby lobby) {
        Lobby persistedLobby = lobbyService.findWithId(lobby.getId());
        if (persistedLobby == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        persistedLobby.setCreationDate(lobby.getCreationDate());

        lobbyService.save(persistedLobby);
        return new ResponseEntity<>(persistedLobby, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteLobby(@RequestBody Lobby lobby) {
        Lobby persistedLobby = lobbyService.findWithId(lobby.getId());
        if (persistedLobby == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        lobbyService.delete(persistedLobby);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteLobbies() {
        lobbyService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
