package com.iquest.webapp.controllers;

import com.iquest.model.Lobby;
import com.iquest.model.user.Client;
import com.iquest.model.user.UserLobbySession;
import com.iquest.service.ClientService;
import com.iquest.service.LobbyService;
import com.iquest.webapp.dto.report.ClientReportDto;
import com.iquest.webapp.dto.report.LobbyReportDto;
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

    private ClientService clientService;
    private LobbyService lobbyService;

    @Autowired
    public ReportController(ClientService clientService, LobbyService lobbyService) {
        this.clientService = clientService;
        this.lobbyService = lobbyService;
    }

    @GetMapping("/clientReport")
    public ResponseEntity<ClientReportDto> generateClientReport() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientService.findByEmail(email);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ClientReportDto clientReportDto = constructClientReportDto(client);

        return new ResponseEntity<>(clientReportDto, HttpStatus.ACCEPTED);
    }

    private ClientReportDto constructClientReportDto(Client client) {
        ClientReportDto clientReportDto = new ClientReportDto();
        clientReportDto.setClientDto(ModelToDtoConverter.convertToClientDto(client));

        initializeClientReportQuizzesPerMonth(clientReportDto);

        setClientReportQuizzesPerMonth(client, clientReportDto);
        return clientReportDto;
    }

    private void initializeClientReportQuizzesPerMonth(ClientReportDto clientReportDto) {
        clientReportDto.setQuizzesPerMonth(new ArrayList<>());
        for(int month = 0; month < 12; month++) {
            clientReportDto.getQuizzesPerMonth().add(0);
        }
    }

    private void setClientReportQuizzesPerMonth(Client client, ClientReportDto clientReportDto) {
        List<UserLobbySession> quizzesPlayed = client.getLobbies();
        for(UserLobbySession userLobbySession : quizzesPlayed) {
            int quizMonth = userLobbySession.getLobby().getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue() - 1;
            clientReportDto.getQuizzesPerMonth().set(quizMonth, clientReportDto.getQuizzesPerMonth().get(quizMonth) + 1);
        }
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
