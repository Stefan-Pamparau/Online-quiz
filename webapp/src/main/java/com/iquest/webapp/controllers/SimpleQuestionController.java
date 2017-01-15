package com.iquest.webapp.controllers;

import com.iquest.model.quiz.question.SimpleQuestion;
import com.iquest.service.SimpleQuestionService;
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
@RequestMapping(path = "/simpleQuestion")
@CrossOrigin(origins = "*")
public class SimpleQuestionController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(SimpleQuestionController.class);

    private final SimpleQuestionService simpleQuestionService;

    @Autowired
    public SimpleQuestionController(SimpleQuestionService simpleQuestionService) {
        this.simpleQuestionService = simpleQuestionService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<SimpleQuestion>> getAllSimpleQuestions() {
        logger.info("Retrieving all simple questions");

        List<SimpleQuestion> simpleQuestions = simpleQuestionService.findAll();
        if (simpleQuestions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(simpleQuestions, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<SimpleQuestion> getSimpleQuestionWithId(@PathVariable("id") Integer id) {
        logger.info(String.format("Retrieving simple question with id %s", id));

        SimpleQuestion simpleQuestion = simpleQuestionService.findWithId(id);
        if (simpleQuestion == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(simpleQuestion, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<SimpleQuestion> insertSimpleQuestion(@RequestBody SimpleQuestion simpleQuestion) {
        logger.info(String.format("Inserting simple question %s", simpleQuestion));

        simpleQuestionService.save(simpleQuestion);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentServletMapping().path("/get/{id}").buildAndExpand(simpleQuestion.getId()).toUri());

        return new ResponseEntity<>(simpleQuestion, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<SimpleQuestion> updateSimpleQuestion(@RequestBody SimpleQuestion simpleQuestion) {
        logger.info(String.format("Updating simple question %s", simpleQuestion));

        SimpleQuestion persistedSimpleQuestion = simpleQuestionService.findWithId(simpleQuestion.getId());
        if (persistedSimpleQuestion == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        persistedSimpleQuestion.setQuestionText(simpleQuestion.getQuestionText());
        simpleQuestionService.save(persistedSimpleQuestion);

        return new ResponseEntity<>(persistedSimpleQuestion, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteSimpleQuestion(@RequestBody SimpleQuestion simpleQuestion) {
        logger.info(String.format("Deleting simple question %s", simpleQuestion));

        SimpleQuestion persistedSimpleQuestion = simpleQuestionService.findWithId(simpleQuestion.getId());
        if (persistedSimpleQuestion == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        simpleQuestionService.delete(persistedSimpleQuestion);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllSimpleQuestions() {
        logger.info("Deleting all simple questions");
        simpleQuestionService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
