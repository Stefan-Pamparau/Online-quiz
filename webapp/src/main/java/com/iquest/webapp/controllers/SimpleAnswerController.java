package com.iquest.webapp.controllers;

import com.iquest.model.quiz.answer.SimpleAnswer;
import com.iquest.service.SimpleAnswerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class SimpleAnswerController {

    private static final Logger logger = LoggerFactory.getLogger(SimpleAnswerController.class);

    private final SimpleAnswerService simpleAnswerService;

    @Autowired
    public SimpleAnswerController(SimpleAnswerService simpleAnswerService) {
        this.simpleAnswerService = simpleAnswerService;
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
}
