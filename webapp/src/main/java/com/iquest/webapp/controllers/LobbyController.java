package com.iquest.webapp.controllers;

import com.iquest.model.Lobby;
import com.iquest.model.quiz.Quiz;
import com.iquest.model.user.UserLobbySession;
import com.iquest.service.ClientService;
import com.iquest.service.LobbyService;
import com.iquest.service.QuizService;
import com.iquest.webapp.dto.frommodel.LobbyDto;
import com.iquest.webapp.sessionmanagement.Session;
import com.iquest.webapp.sessionmanagement.SessionMap;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/lobby")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class LobbyController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(LobbyController.class);

    private LobbyService lobbyService;
    private QuizService quizService;
    private ClientService clientService;

    @Autowired
    public LobbyController(LobbyService lobbyService, QuizService quizService, ClientService clientService) {
        this.lobbyService = lobbyService;
        this.quizService = quizService;
        this.clientService = clientService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Lobby>> getAllLobbies() {
        logger.info("Retrieving all lobbies");

        List<Lobby> lobbies = lobbyService.findAll();
        if (lobbies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(lobbies, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Lobby> getLobbyWithId(@PathVariable("id") Integer id) {
        logger.info(String.format("Retrieving lobby with id %s", id));

        Lobby lobby = lobbyService.findWithId(id);
        if (lobby == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(lobby, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<Lobby> insertLobby(@RequestBody Lobby lobby) {
        logger.info(String.format("Inserting lobby %s", lobby));

        lobbyService.save(lobby);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentContextPath().path("lobby/get/{id}").buildAndExpand(lobby.getId()).toUri());

        return new ResponseEntity<>(lobby, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Lobby> updateLobby(@RequestBody Lobby lobby) {
        logger.info(String.format("Updating lobby %s", lobby));

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
        logger.info(String.format("Deleting lobby %s", lobby));

        Lobby persistedLobby = lobbyService.findWithId(lobby.getId());
        if (persistedLobby == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        lobbyService.delete(persistedLobby);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteLobbies() {
        logger.info("Deleting all lobbies");
        lobbyService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("startSessionLobby/{quizId}")
    public ResponseEntity<Void> createSessionLobby(@PathVariable("quizId") Integer quizId) {
        Quiz quiz = quizService.findWithId(quizId);
        if (quiz == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Lobby lobby = new Lobby();
        lobby.setUsers(new ArrayList<>());
        UserLobbySession userLobbySession = new UserLobbySession();
        userLobbySession.setLobby(lobby);
        userLobbySession.setUser(clientService.findByEmail(email));
        userLobbySession.setScore(0);
        lobby.getUsers().add(userLobbySession);
        lobby.setCreationDate(new Date());
        lobby.setQuiz(quiz);
        Session session = getUserSession(email);
        session.setLobby(lobby);
        SessionMap.getInstance().put(email, session);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getSessionLobby")
    public ResponseEntity<LobbyDto> getSessionLobby() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Lobby lobby = SessionMap.getInstance().get(email).getLobby();

        return new ResponseEntity<>(ModelToDtoConverter.convertToLobbyDto(lobby), HttpStatus.OK);
    }

    @PostMapping("/updateSessionLobby")
    public ResponseEntity<LobbyDto> updateSessionLobby(@RequestBody LobbyDto lobbyDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Lobby lobby = SessionMap.getInstance().get(email).getLobby();
        lobby.setSecondsUntilStart(lobbyDto.getSecondsUntilStart());

        return new ResponseEntity<>(ModelToDtoConverter.convertToLobbyDto(lobby), HttpStatus.OK);
    }

    @GetMapping("/finishSessionLobby")
    public ResponseEntity<Void> finishSessionLobby() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Session session = getUserSession(email);
        Lobby lobby = session.getLobby();
        lobbyService.save(lobby);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
