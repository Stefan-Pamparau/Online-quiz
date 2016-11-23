package com.iquest.webapp.controllers;

import com.iquest.model.quiz.answer.SimpleAnswer;
import com.iquest.model.quiz.question.SimpleQuestion;
import com.iquest.service.SimpleQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(path = "/simpleQuestion")
public class SimpleQuestionController extends AbstractController {

    private final SimpleQuestionService simpleQuestionService;

    @Autowired
    public SimpleQuestionController(SimpleQuestionService simpleQuestionService) {
        this.simpleQuestionService = simpleQuestionService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<SimpleQuestion>> getAllSimpleQuestions() {
        List<SimpleQuestion> simpleQuestions = simpleQuestionService.findAll();
        if (simpleQuestions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(simpleQuestions, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<SimpleQuestion> getSimpleQuestionWithId(@PathVariable("id") Integer id) {
        SimpleQuestion simpleQuestion = simpleQuestionService.findWithId(id);
        if (simpleQuestion == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(simpleQuestion, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<SimpleQuestion> insertSimpleQuestion(@RequestBody SimpleQuestion simpleQuestion) {
        simpleQuestionService.save(simpleQuestion);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentServletMapping().path("/get/{id}").buildAndExpand(simpleQuestion.getId()).toUri());

        return new ResponseEntity<>(simpleQuestion, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<SimpleQuestion> updateSimpleQuestion(@RequestBody SimpleQuestion simpleQuestion) {
        SimpleQuestion persistedSimpleQuestion = simpleQuestionService.findWithId(simpleQuestion.getId());
        if (persistedSimpleQuestion == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        persistedSimpleQuestion.setQuestionText(simpleQuestion.getQuestionText());

        simpleQuestionService.save(persistedSimpleQuestion);
        return new ResponseEntity<>(persistedSimpleQuestion, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteSimpleQuestion(@RequestBody SimpleAnswer simpleAnswer) {
        SimpleQuestion persistedSimpleQuestion = simpleQuestionService.findWithId(simpleAnswer.getId());
        if (persistedSimpleQuestion == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        simpleQuestionService.delete(persistedSimpleQuestion);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllSimpleQuestions() {
        simpleQuestionService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
