package com.iquest.webapp.controllers;

import com.iquest.model.quiz.ExamQuiz;
import com.iquest.model.quiz.answer.SimpleAnswer;
import com.iquest.model.quiz.question.QuestionType;
import com.iquest.model.quiz.question.SimpleQuestion;
import com.iquest.model.user.Client;
import com.iquest.service.ClientService;
import com.iquest.service.ExamQuizService;
import com.iquest.webapp.dto.ExamQuizWithQuestionsDto;
import com.iquest.webapp.dto.SimpleQuestionAndAnswerDto;
import com.iquest.webapp.dto.frommodel.ExamQuizDto;
import com.iquest.webapp.sessionmanagement.Session;
import com.iquest.webapp.sessionmanagement.SessionMap;
import com.iquest.webapp.util.DtoToModelConverter;
import com.iquest.webapp.util.ModelToDtoConverter;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/examQuiz")
@CrossOrigin(origins = "*")
public class ExamQuizController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(ExamQuizController.class);

    private ExamQuizService examQuizService;
    private ClientService clientService;

    @Autowired
    public ExamQuizController(ExamQuizService examQuizService, ClientService clientService) {
        this.examQuizService = examQuizService;
        this.clientService = clientService;
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
    public ResponseEntity<ExamQuizDto> insertExamQuiz(@RequestBody ExamQuizDto examQuizDto) {
        ExamQuiz examQuiz = DtoToModelConverter.convertToExamQuiz(examQuizDto);
        logger.info(String.format("Inserting exam quiz %s", examQuiz));
        examQuizService.save(examQuiz);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentContextPath().path("examQuiz/get/{id}").buildAndExpand(examQuiz.getId()).toUri());

        return new ResponseEntity<>(ModelToDtoConverter.convertToExamQuizDto(examQuiz), httpHeaders, HttpStatus.CREATED);
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

    @PostMapping("/startExamQuizCreation")
    public ResponseEntity<Void> startExamQuizCreation(@RequestBody ExamQuizDto examQuizDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Session session = getUserSession(email);
        ExamQuiz examQuiz = DtoToModelConverter.convertToExamQuiz(examQuizDto);
        updateSessionWithExamQuiz(email, session, examQuiz);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void updateSessionWithExamQuiz(String email, Session session, ExamQuiz examQuiz) {
        session.setExamQuiz(examQuiz);
        SessionMap.getInstance().put(email, session);
    }

    @PutMapping("/addQuestionAndAnswer")
    public ResponseEntity<Void> addQuestionAndAnswer(@RequestBody SimpleQuestionAndAnswerDto simpleQuestionAndAnswerDto) {
        addSessionQuestionAndAnswer(simpleQuestionAndAnswerDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/finishExamQuizCreation")
    public ResponseEntity<Void> finishExamQuizCreation(@RequestBody SimpleQuestionAndAnswerDto simpleQuestionAndAnswerDto) {
        addSessionQuestionAndAnswer(simpleQuestionAndAnswerDto);
        saveSessionExamQuiz();
        removeUserSession();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void addSessionQuestionAndAnswer(@RequestBody SimpleQuestionAndAnswerDto simpleQuestionAndAnswerDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        ExamQuiz examQuiz = SessionMap.getInstance().get(email).getExamQuiz();
        Client client = clientService.findByEmail(email);
        SimpleQuestion simpleQuestion = prepareQuestionAndAnswerForInsertion(simpleQuestionAndAnswerDto, examQuiz, client);

        updateSessionExamQuiz(examQuiz, client, simpleQuestion);
    }

    private SimpleQuestion prepareQuestionAndAnswerForInsertion(SimpleQuestionAndAnswerDto simpleQuestionAndAnswerDto, ExamQuiz examQuiz, Client client) {
        SimpleQuestion simpleQuestion = DtoToModelConverter.convertToSimpleQuestion(simpleQuestionAndAnswerDto.getSimpleQuestionDto());
        SimpleAnswer simpleAnswer = DtoToModelConverter.convertToSimpleAnswer(simpleQuestionAndAnswerDto.getSimpleAnswerDto());
        simpleAnswer.setQuestion(simpleQuestion);
        simpleQuestion.setAnswer(simpleAnswer);
        simpleQuestion.setQuiz(examQuiz);
        simpleQuestion.setClient(client);
        return simpleQuestion;
    }

    private void updateSessionExamQuiz(ExamQuiz examQuiz, Client client, SimpleQuestion simpleQuestion) {
        examQuiz.setClient(client);
        if (examQuiz.getQuestions() == null) {
            examQuiz.setQuestions(new ArrayList<>());
        }
        examQuiz.getQuestions().add(simpleQuestion);
    }

    private void saveSessionExamQuiz() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        ExamQuiz examQuiz = SessionMap.getInstance().get(email).getExamQuiz();
        examQuizService.save(examQuiz);
    }

    private void removeUserSession() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        SessionMap.getInstance().remove(email);
    }

    @GetMapping("/get/withQuestions/{quizId}")
    public ResponseEntity<ExamQuizWithQuestionsDto> getExamQuizWithQuestions(@PathVariable("quizId") Integer quizId) {
        ExamQuiz examQuiz = examQuizService.findWithId(quizId);
        if (examQuiz == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ExamQuizWithQuestionsDto examQuizWithQuestionsDto = contructExamQuizWithQuestionsDto(examQuiz);

        return new ResponseEntity<ExamQuizWithQuestionsDto>(examQuizWithQuestionsDto, HttpStatus.OK);
    }

    private ExamQuizWithQuestionsDto contructExamQuizWithQuestionsDto(ExamQuiz examQuiz) {
        ExamQuizWithQuestionsDto examQuizWithQuestionsDto = new ExamQuizWithQuestionsDto();
        examQuizWithQuestionsDto.setExamQuizDto(ModelToDtoConverter.convertToExamQuizDto(examQuiz));
        examQuizWithQuestionsDto.setSimpleQuestionDtos(new ArrayList<>());

        examQuiz.getQuestions().stream()
                .filter(question -> QuestionType.SIMPLE_QUESTION == question.getQuestionType())
                .forEach(question -> examQuizWithQuestionsDto.getSimpleQuestionDtos().add(ModelToDtoConverter.convertToSimpleQuestionDto(question)));
        return examQuizWithQuestionsDto;
    }
}
