package com.iquest.webapp.controllers;

import com.iquest.model.Lobby;
import com.iquest.model.user.Client;
import com.iquest.model.user.User;
import com.iquest.model.user.UserLobbySession;
import com.iquest.service.ClientService;
import com.iquest.service.LobbyService;
import com.iquest.service.UserService;
import com.iquest.webapp.dto.report.ClientActivityReportDto;
import com.iquest.webapp.dto.report.ClientScoreReportDto;
import com.iquest.webapp.dto.report.LobbyReportDto;
import com.iquest.webapp.dto.report.UsersScoreReportDto;
import com.iquest.webapp.util.ModelToDtoConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reports")
@CrossOrigin(origins = "*")
public class ReportController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    private UserService userService;
    private ClientService clientService;
    private LobbyService lobbyService;

    @Autowired
    public ReportController(UserService userService, ClientService clientService, LobbyService lobbyService) {
        this.userService = userService;
        this.clientService = clientService;
        this.lobbyService = lobbyService;
    }

    @GetMapping("/clientActivityReport")
    public ResponseEntity<ClientActivityReportDto> generateClientActivityReport() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientService.findByEmail(email);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ClientActivityReportDto clientActivityReportDto = constructClientActivityReportDto(client);

        return new ResponseEntity<>(clientActivityReportDto, HttpStatus.ACCEPTED);
    }

    private ClientActivityReportDto constructClientActivityReportDto(Client client) {
        ClientActivityReportDto clientActivityReportDto = new ClientActivityReportDto();
        clientActivityReportDto.setClientDto(ModelToDtoConverter.convertToClientDto(client));
        clientActivityReportDto.setQuizzesPerMonth(initializeClientReportValues());
        setClientReportQuizzesPerMonth(client, clientActivityReportDto);

        return clientActivityReportDto;
    }

    private void setClientReportQuizzesPerMonth(Client client, ClientActivityReportDto clientActivityReportDto) {
        List<UserLobbySession> quizzesPlayed = client.getLobbies();
        for (UserLobbySession userLobbySession : quizzesPlayed) {
            int quizMonth = userLobbySession.getLobby().getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue() - 1;
            clientActivityReportDto.getQuizzesPerMonth().set(quizMonth, clientActivityReportDto.getQuizzesPerMonth().get(quizMonth) + 1);
        }
    }

    @GetMapping("/clientScoreReport")
    public ResponseEntity<ClientScoreReportDto> generateClientScoreReport() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientService.findByEmail(email);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ClientScoreReportDto clientScoreReportDto = constructClientScoreReportDto(client);

        return new ResponseEntity<>(clientScoreReportDto, HttpStatus.ACCEPTED);
    }

    private ClientScoreReportDto constructClientScoreReportDto(Client client) {
        ClientScoreReportDto clientScoreReportDto = new ClientScoreReportDto();
        clientScoreReportDto.setClientDto(ModelToDtoConverter.convertToClientDto(client));
        clientScoreReportDto.setScoresPerMonth(initializeClientReportValues());
        setClientReportScoresPerMonth(client, clientScoreReportDto);

        return clientScoreReportDto;
    }

    private void setClientReportScoresPerMonth(Client client, ClientScoreReportDto clientScoreReportDto) {
        List<UserLobbySession> quizzesPlayed = client.getLobbies();
        for (UserLobbySession userLobbySession : quizzesPlayed) {
            int quizMonth = userLobbySession.getLobby().getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue() - 1;
            if (userLobbySession.getScore() != null) {
                clientScoreReportDto.getScoresPerMonth().set(quizMonth, clientScoreReportDto.getScoresPerMonth().get(quizMonth) + userLobbySession.getScore());
            }
        }
    }

    private List<Integer> initializeClientReportValues() {
        List<Integer> values = new ArrayList<>();
        for (int month = 0; month < 12; month++) {
            values.add(0);
        }
        return values;
    }

    @GetMapping("/usersScoreReport")
    public ResponseEntity<UsersScoreReportDto> generateUserScoresReportDto() {
        UsersScoreReportDto userScoresReportDto = new UsersScoreReportDto();
        userScoresReportDto.setUserDtos(new ArrayList<>());
        userScoresReportDto.setScoresPerUser(new ArrayList<>());
        List<User> users = userService.findAll();

        for (User user : users) {
            userScoresReportDto.getUserDtos().add(ModelToDtoConverter.convertToUserDto(user));
            int score = 0;
            for (UserLobbySession userLobbySession : user.getLobbies()) {
                if (userLobbySession.getScore() != null) {
                    score += userLobbySession.getScore();
                }
            }
            userScoresReportDto.getScoresPerUser().add(score);
        }

        return new ResponseEntity<>(userScoresReportDto, HttpStatus.OK);
    }

    @GetMapping("/lobbyReport/{id}")
    public ResponseEntity<LobbyReportDto> generateLobbyReport(@PathVariable Integer id) {
        logger.info(String.format("Generating lobby report for lobby with id", id));

        Lobby lobby = lobbyService.findWithId(id);
        if (lobby == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LobbyReportDto lobbyReportDto = new LobbyReportDto();
        lobbyReportDto.setLobby(lobby);
        lobbyReportDto.setUsers(lobby.getUsers());

        return new ResponseEntity<>(lobbyReportDto, HttpStatus.ACCEPTED);
    }
}
