package com.iquest.controllers;

import com.iquest.model.quiz.GamefiedQuiz;
import com.iquest.service.GamefiedQuizService;
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
@RequestMapping(path = "/gamefiedQuiz")
public class GamefiedQuizController {

    private final GamefiedQuizService gamefiedQuizService;

    @Autowired
    public GamefiedQuizController(GamefiedQuizService gamefiedQuizService) {
        this.gamefiedQuizService = gamefiedQuizService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<GamefiedQuiz>> getAllGamefiedQuizzes() {
        List<GamefiedQuiz> gamefiedQuizzes = gamefiedQuizService.findAll();
        if (gamefiedQuizzes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(gamefiedQuizzes, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<GamefiedQuiz> getGamefiedQuizWithId(@PathVariable("id") Integer id) {
        GamefiedQuiz gamefiedQuiz = gamefiedQuizService.findWithId(id);
        if (gamefiedQuiz == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(gamefiedQuiz, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<GamefiedQuiz> insertGamefiedQuiz(@RequestBody GamefiedQuiz gamefiedQuiz) {
        gamefiedQuizService.save(gamefiedQuiz);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentServletMapping().path("/get/{id}").buildAndExpand(gamefiedQuiz.getId()).toUri());

        return new ResponseEntity<>(gamefiedQuiz, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<GamefiedQuiz> updateGamefiedQuiz(@RequestBody GamefiedQuiz gamefiedQuiz) {
        GamefiedQuiz persistedGamefiedQuiz = gamefiedQuizService.findWithId(gamefiedQuiz.getId());
        if (persistedGamefiedQuiz == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        gamefiedQuizService.save(persistedGamefiedQuiz);
        return new ResponseEntity<>(persistedGamefiedQuiz, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteGamefiedQuiz(@RequestBody GamefiedQuiz gamefiedQuiz) {
        GamefiedQuiz persistedGamefiedQuiz = gamefiedQuizService.findWithId(gamefiedQuiz.getId());
        if (persistedGamefiedQuiz == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        gamefiedQuizService.delete(persistedGamefiedQuiz);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllGamefiedQuizzes() {
        gamefiedQuizService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
