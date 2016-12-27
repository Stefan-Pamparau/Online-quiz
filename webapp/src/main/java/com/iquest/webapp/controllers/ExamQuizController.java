package com.iquest.webapp.controllers;

import com.iquest.model.quiz.ExamQuiz;
import com.iquest.service.ExamQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(path = "/examQuiz")
public class ExamQuizController {

    private final ExamQuizService examQuizService;

    @Autowired
    public ExamQuizController(ExamQuizService examQuizService) {
        this.examQuizService = examQuizService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<ExamQuiz>> getAllExamQuizzes() {
        List<ExamQuiz> examQuizzes = examQuizService.findAll();
        if (examQuizzes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(examQuizzes, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ExamQuiz> getExamQuizWithId(@PathVariable("id") Integer id) {
        ExamQuiz examQuiz = examQuizService.findWithId(id);
        if (examQuiz == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(examQuiz, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<ExamQuiz> insertExamQuiz(@RequestBody ExamQuiz examQuiz) {
        examQuizService.save(examQuiz);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentServletMapping().path("/get/{id}").buildAndExpand(examQuiz.getId()).toUri());

        return new ResponseEntity<>(examQuiz, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ExamQuiz> updateExamQuiz(@RequestBody ExamQuiz examQuiz) {
        ExamQuiz persistedExamQuiz = examQuizService.findWithId(examQuiz.getId());
        if (persistedExamQuiz == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        examQuizService.save(persistedExamQuiz);
        return new ResponseEntity<>(persistedExamQuiz, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteExamQuiz(@RequestBody ExamQuiz examQuiz) {
        ExamQuiz persistedExamQuiz = examQuizService.findWithId(examQuiz.getId());
        if (persistedExamQuiz == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        examQuizService.delete(persistedExamQuiz);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllExamQuizzes() {
        examQuizService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
