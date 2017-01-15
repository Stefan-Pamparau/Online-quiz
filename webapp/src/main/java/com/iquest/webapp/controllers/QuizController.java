package com.iquest.webapp.controllers;

import com.iquest.model.quiz.Quiz;
import com.iquest.service.QuizService;
import com.iquest.webapp.dto.frommodel.QuizDto;
import com.iquest.webapp.util.ModelToDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/quiz")
@CrossOrigin(origins = "*")
public class QuizController {

    private QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/get/{quizId}")
    public ResponseEntity<QuizDto> getQuizWithId(@PathVariable("quizId") Integer quizId) {
        Quiz quiz = quizService.findWithId(quizId);

        if (quiz == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(ModelToDtoConverter.convertToQuizDto(quiz), HttpStatus.OK);
    }
}
