package com.iquest.webapp.controllers;

import com.iquest.model.quiz.answer.SimpleAnswer;
import com.iquest.model.quiz.question.SimpleQuestion;
import com.iquest.service.SimpleAnswerService;
import com.iquest.service.SimpleQuestionService;
import com.iquest.webapp.dto.ClientSimpleAnswerDto;
import com.iquest.webapp.dto.frommodel.SimpleQuestionDto;
import com.iquest.webapp.sessionmanagement.Session;
import com.iquest.webapp.sessionmanagement.SessionMap;
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

import java.util.List;

@RestController
@RequestMapping(path = "/simpleAnswer")
@CrossOrigin(origins = "*")
public class SimpleAnswerController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(SimpleAnswerController.class);

    private SimpleAnswerService simpleAnswerService;
    private SimpleQuestionService simpleQuestionService;

    @Autowired
    public SimpleAnswerController(SimpleAnswerService simpleAnswerService, SimpleQuestionService simpleQuestionService) {
        this.simpleAnswerService = simpleAnswerService;
        this.simpleQuestionService = simpleQuestionService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<SimpleAnswer>> getAllSimpleAnswers() {
        logger.info("Retrieving all simple answers");

        List<SimpleAnswer> simpleAnswers = simpleAnswerService.findAll();
        if (simpleAnswers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(simpleAnswers, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<SimpleAnswer> getSimpleAnswerWithId(@PathVariable("id") Integer id) {
        logger.info(String.format("Retrieving simple answer with id %d", id));

        SimpleAnswer simpleAnswer = simpleAnswerService.findWithId(id);
        if (simpleAnswer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(simpleAnswer, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<SimpleAnswer> insertSimpleAnswer(@RequestBody SimpleAnswer simpleAnswer) {
        logger.info(String.format("Inserting simple answer %s", simpleAnswer));

        simpleAnswerService.save(simpleAnswer);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentServletMapping().path("/get/{id}").buildAndExpand(simpleAnswer.getId()).toUri());

        return new ResponseEntity<>(simpleAnswer, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<SimpleAnswer> updateSimpleAnswer(@RequestBody SimpleAnswer simpleAnswer) {
        logger.info(String.format("Updating simple answer %s", simpleAnswer));

        SimpleAnswer persistedSimpleAnswer = simpleAnswerService.findWithId(simpleAnswer.getId());
        if (persistedSimpleAnswer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        persistedSimpleAnswer.setAnswerText(simpleAnswer.getAnswerText());
        simpleAnswerService.save(persistedSimpleAnswer);

        return new ResponseEntity<>(persistedSimpleAnswer, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteSimpleAnswer(@RequestBody SimpleAnswer simpleAnswer) {
        logger.info(String.format("Deleting simple answer %s", simpleAnswer));

        SimpleAnswer persistedSimpleAnswer = simpleAnswerService.findWithId(simpleAnswer.getId());
        if (persistedSimpleAnswer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        simpleAnswerService.delete(persistedSimpleAnswer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllSimpleAnswers() {
        logger.info("Deleting all simple answers");
        simpleAnswerService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/verifyAnswer")
    public ResponseEntity<Void> verifyAnswer(@RequestBody ClientSimpleAnswerDto clientSimpleAnswerDto) {
        SimpleQuestionDto simpleQuestionDto = clientSimpleAnswerDto.getSimpleQuestionDto();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Session session = SessionMap.getInstance().get(email);
        SimpleQuestion simpleQuestion = simpleQuestionService.findWithId(clientSimpleAnswerDto.getSimpleQuestionDto().getId());

        checkAnswer(clientSimpleAnswerDto, email, session, simpleQuestion);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void checkAnswer(ClientSimpleAnswerDto clientSimpleAnswerDto, String email, Session session, SimpleQuestion simpleQuestion) {
        if (simpleQuestion.getAnswer().getAnswerText().equals(clientSimpleAnswerDto.getAnswerText())) {
            session.getLobby().getUsers().stream()
                    .filter(userLobbySession -> userLobbySession.getUser().getEmail().equals(email))
                    .forEach(userLobbySession -> userLobbySession.setScore(userLobbySession.getScore() + 5));
        }
    }
}
