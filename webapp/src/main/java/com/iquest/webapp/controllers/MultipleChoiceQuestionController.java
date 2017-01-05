package com.iquest.webapp.controllers;

import com.iquest.model.quiz.question.MultipleChoiceQuestion;
import com.iquest.service.MultipleChoiceQuestionService;
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
@RequestMapping(path = "/multipleChoiceQuestion")
@CrossOrigin(origins = "*")
public class MultipleChoiceQuestionController {

    private static final Logger logger = LoggerFactory.getLogger(MultipleChoiceAnswerController.class);

    private final MultipleChoiceQuestionService multipleChoiceQuestionService;

    @Autowired
    public MultipleChoiceQuestionController(MultipleChoiceQuestionService multipleChoiceQuestionService) {
        this.multipleChoiceQuestionService = multipleChoiceQuestionService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<MultipleChoiceQuestion>> getAllMultipleChoiceQuestions() {
        logger.info("Retrieving all multiple choice questions");

        List<MultipleChoiceQuestion> multipleChoiceQuestions = multipleChoiceQuestionService.findAll();
        if (multipleChoiceQuestions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(multipleChoiceQuestions, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<MultipleChoiceQuestion> getMultipleChoiceQuestionWithId(@PathVariable("id") Integer id) {
        logger.info(String.format("Retrieving multiple choice question with id %s", id));

        MultipleChoiceQuestion multipleChoiceQuestion = multipleChoiceQuestionService.findWithId(id);
        if (multipleChoiceQuestion == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(multipleChoiceQuestion, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<MultipleChoiceQuestion> insertMultipleChoiceQuestion(@RequestBody MultipleChoiceQuestion multipleChoiceQuestion) {
        logger.info(String.format("Inserting multiple choice question %s", multipleChoiceQuestion));

        multipleChoiceQuestionService.save(multipleChoiceQuestion);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentServletMapping().path("/get/{id}").buildAndExpand(multipleChoiceQuestion.getId()).toUri());

        return new ResponseEntity<>(multipleChoiceQuestion, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<MultipleChoiceQuestion> updateMultipleChoiceQuestion(@RequestBody MultipleChoiceQuestion multipleChoiceQuestion) {
        logger.info(String.format("Updating multiple choice question %s", multipleChoiceQuestion));

        MultipleChoiceQuestion persistedMultipleChoiceQuestions = multipleChoiceQuestionService.findWithId(multipleChoiceQuestion.getId());
        if (persistedMultipleChoiceQuestions == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        persistedMultipleChoiceQuestions.setQuestionText(multipleChoiceQuestion.getQuestionText());
        multipleChoiceQuestionService.save(persistedMultipleChoiceQuestions);

        return new ResponseEntity<>(persistedMultipleChoiceQuestions, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteMultipleChoiceQuestion(@RequestBody MultipleChoiceQuestion multipleChoiceQuestion) {
        logger.info(String.format("Deleting multiple choice question %s", multipleChoiceQuestion));

        MultipleChoiceQuestion persistedMultipleChoiceQuestions = multipleChoiceQuestionService.findWithId(multipleChoiceQuestion.getId());
        if (persistedMultipleChoiceQuestions == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        multipleChoiceQuestionService.delete(persistedMultipleChoiceQuestions);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllMultipleChoiceQuestions() {
        logger.info("Deleting all multiple choice questions");
        multipleChoiceQuestionService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
