package com.iquest.webapp.controllers;

import com.iquest.model.quiz.ExamQuiz;
import com.iquest.service.ExamQuizService;
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
@RequestMapping(path = "/examQuiz")
@CrossOrigin(origins = "*")
public class ExamQuizController {

    private static final Logger logger = LoggerFactory.getLogger(ExamQuizController.class);

    private ExamQuizService examQuizService;

    @Autowired
    public ExamQuizController(ExamQuizService examQuizService) {
        this.examQuizService = examQuizService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<ExamQuiz>> getAllExamQuizzes() {
        logger.info("Retrieving all exam quizzes from database");

        List<ExamQuiz> examQuizzes = examQuizService.findAll();
        if (examQuizzes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(examQuizzes, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ExamQuiz> getExamQuizWithId(@PathVariable("id") Integer id) {
        logger.info(String.format("Retrieving exam quiz with id %s", id));

        ExamQuiz examQuiz = examQuizService.findWithId(id);

        if (examQuiz == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(examQuiz, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<ExamQuiz> insertExamQuiz(@RequestBody ExamQuiz examQuiz) {
        logger.info(String.format("Inserting exam quiz %s", examQuiz));

        examQuizService.save(examQuiz);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentContextPath().path("examQuiz/get/{id}").buildAndExpand(examQuiz.getId()).toUri());

        return new ResponseEntity<>(examQuiz, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ExamQuiz> updateExamQuiz(@RequestBody ExamQuiz examQuiz) {
        logger.info(String.format("Updating exam quiz %s", examQuiz));

        ExamQuiz persistedExamQuiz = examQuizService.findWithId(examQuiz.getId());

        if (persistedExamQuiz == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        examQuizService.save(persistedExamQuiz);

        return new ResponseEntity<>(persistedExamQuiz, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteExamQuiz(@RequestBody ExamQuiz examQuiz) {
        logger.info(String.format("Deleting admin %s", examQuiz));

        ExamQuiz persistedExamQuiz = examQuizService.findWithId(examQuiz.getId());
        if (persistedExamQuiz == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        examQuizService.delete(persistedExamQuiz);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllExamQuizzes() {
        logger.info("Deleting all exam quizzes");
        examQuizService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
