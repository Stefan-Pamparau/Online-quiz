package com.iquest.webapp.controllers;

import com.iquest.model.Lobby;
import com.iquest.model.user.Client;
import com.iquest.service.ClientService;
import com.iquest.service.LobbyService;
import com.iquest.webapp.dto.report.ClientReportDto;
import com.iquest.webapp.dto.report.LobbyReportDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/clientReport/{id}")
    public ResponseEntity<ClientReportDto> generateClientReport(@PathVariable Integer id) {
        logger.info(String.format("Generating client report for client with id %s", id));

        Client client = clientService.findWithId(id);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ClientReportDto clientReportDto = new ClientReportDto();
        clientReportDto.setClient(client);
        clientReportDto.setLobbies(client.getLobbies());
        clientReportDto.setQuestions(client.getQuestions());

        return new ResponseEntity<>(clientReportDto, HttpStatus.ACCEPTED);
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
