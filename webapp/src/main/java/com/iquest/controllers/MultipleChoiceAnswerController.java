package com.iquest.controllers;

import com.iquest.model.quiz.answer.MultipleChoiceAnswer;
import com.iquest.service.MultipleChoiceAnswerService;
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
@RequestMapping(path = "/multipleChoiceAnswer")
public class MultipleChoiceAnswerController {

    private final MultipleChoiceAnswerService multipleChoiceAnswerService;

    @Autowired
    public MultipleChoiceAnswerController(MultipleChoiceAnswerService multipleChoiceAnswerService) {
        this.multipleChoiceAnswerService = multipleChoiceAnswerService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<MultipleChoiceAnswer>> getAllMultipleChoiceAnswers() {
        List<MultipleChoiceAnswer> multipleChoiceAnswers = multipleChoiceAnswerService.findAll();
        if (multipleChoiceAnswers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(multipleChoiceAnswers, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<MultipleChoiceAnswer> getMultipleChoiceAnswerWithId(@PathVariable("id") Integer id) {
        MultipleChoiceAnswer multipleChoiceAnswer = multipleChoiceAnswerService.findWithId(id);
        if (multipleChoiceAnswer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(multipleChoiceAnswer, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<MultipleChoiceAnswer> insertMultipleChoiceAnswer(@RequestBody MultipleChoiceAnswer multipleChoiceAnswer) {
        multipleChoiceAnswerService.save(multipleChoiceAnswer);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentServletMapping().path("/get/{id}").buildAndExpand(multipleChoiceAnswer.getId()).toUri());

        return new ResponseEntity<>(multipleChoiceAnswer, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<MultipleChoiceAnswer> updateMultipleChoiceAnswer(@RequestBody MultipleChoiceAnswer multipleChoiceAnswer) {
        MultipleChoiceAnswer persistedMultipleChoiceAnswer = multipleChoiceAnswerService.findWithId(multipleChoiceAnswer.getId());
        if (persistedMultipleChoiceAnswer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        multipleChoiceAnswerService.save(persistedMultipleChoiceAnswer);
        return new ResponseEntity<>(persistedMultipleChoiceAnswer, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteMultipleChoiceAnswer(@RequestBody MultipleChoiceAnswer multipleChoiceAnswer) {
        MultipleChoiceAnswer persistedMultipleChoiceAnswer = multipleChoiceAnswerService.findWithId(multipleChoiceAnswer.getId());
        if (persistedMultipleChoiceAnswer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        multipleChoiceAnswerService.delete(persistedMultipleChoiceAnswer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllMultipleChoiceAnswers() {
        multipleChoiceAnswerService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
