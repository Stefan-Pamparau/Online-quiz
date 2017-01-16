package com.iquest.webapp.util;

import com.iquest.model.Lobby;
import com.iquest.model.quiz.ExamQuiz;
import com.iquest.model.quiz.GamefiedQuiz;
import com.iquest.model.quiz.Quiz;
import com.iquest.model.quiz.answer.MultipleChoiceAnswer;
import com.iquest.model.quiz.answer.SimpleAnswer;
import com.iquest.model.quiz.question.MultipleChoiceQuestion;
import com.iquest.model.quiz.question.SimpleQuestion;
import com.iquest.model.user.Admin;
import com.iquest.model.user.Client;
import com.iquest.model.user.User;
import com.iquest.model.user.UserType;
import com.iquest.webapp.dto.frommodel.ClientDto;
import com.iquest.webapp.dto.frommodel.ExamQuizDto;
import com.iquest.webapp.dto.frommodel.GamefiedQuizDto;
import com.iquest.webapp.dto.frommodel.LobbyDto;
import com.iquest.webapp.dto.frommodel.MultipleChoiceAnswerDto;
import com.iquest.webapp.dto.frommodel.MultipleChoiceQuestionDto;
import com.iquest.webapp.dto.frommodel.QuizDto;
import com.iquest.webapp.dto.frommodel.SimpleAnswerDto;
import com.iquest.webapp.dto.frommodel.SimpleQuestionDto;
import com.iquest.webapp.dto.frommodel.UserDto;

public class DtoToModelConverter {

    public static Quiz convertToQuiz(QuizDto quizDto) {
        Quiz quiz = new Quiz();

        quiz.setId(quizDto.getId());
        quiz.setQuizType(quizDto.getQuizType());
        quiz.setTitle(quizDto.getTitle());
        quiz.setDescription(quizDto.getDescription());

        return quiz;
    }

    public static ExamQuiz convertToExamQuiz(ExamQuizDto examQuizDto) {
        ExamQuiz examQuiz = new ExamQuiz();

        examQuiz.setId(examQuizDto.getId());
        examQuiz.setQuizType(examQuizDto.getQuizType());
        examQuiz.setTitle(examQuizDto.getTitle());
        examQuiz.setDescription(examQuizDto.getDescription());

        return examQuiz;
    }

    public static GamefiedQuiz convertToGamefiedQuiz(GamefiedQuizDto gamefiedQuizDto) {
        GamefiedQuiz gamefiedQuiz = new GamefiedQuiz();

        gamefiedQuiz.setId(gamefiedQuizDto.getId());
        gamefiedQuiz.setQuizType(gamefiedQuizDto.getQuizType());
        gamefiedQuiz.setTitle(gamefiedQuizDto.getTitle());
        gamefiedQuiz.setDescription(gamefiedQuizDto.getDescription());

        return gamefiedQuiz;
    }

    public static Lobby convertToLobby(LobbyDto lobbyDto) {
        Lobby lobby = new Lobby();

        lobby.setId(lobbyDto.getId());
        lobby.setCreationDate(lobbyDto.getCreationDate());

        return lobby;
    }

    public static MultipleChoiceAnswer convertToMultipleChoiceAnswer(MultipleChoiceAnswerDto multipleChoiceAnswerDto) {
        MultipleChoiceAnswer multipleChoiceAnswer = new MultipleChoiceAnswer();

        multipleChoiceAnswer.setId(multipleChoiceAnswerDto.getId());
        multipleChoiceAnswer.setAnswerText(multipleChoiceAnswerDto.getAnswerText());
        multipleChoiceAnswer.setAnswerType(multipleChoiceAnswerDto.getAnswerType());
        multipleChoiceAnswer.setCorrect(multipleChoiceAnswerDto.getCorrect());

        return multipleChoiceAnswer;
    }

    public static MultipleChoiceQuestion convertToMultipleChoiceQuestion(MultipleChoiceQuestionDto multipleChoiceQuestionDto) {
        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion();

        multipleChoiceQuestion.setId(multipleChoiceQuestionDto.getId());
        multipleChoiceQuestion.setQuestionText(multipleChoiceQuestionDto.getQuestionText());
        multipleChoiceQuestion.setQuestionType(multipleChoiceQuestionDto.getQuestionType());

        return multipleChoiceQuestion;
    }

    public static SimpleAnswer convertToSimpleAnswer(SimpleAnswerDto simpleAnswerDto) {
        SimpleAnswer simpleAnswer = new SimpleAnswer();

        simpleAnswer.setId(simpleAnswerDto.getId());
        simpleAnswer.setAnswerText(simpleAnswerDto.getAnswerText());
        simpleAnswer.setAnswerType(simpleAnswerDto.getAnswerType());

        return simpleAnswer;
    }

    public static SimpleQuestion convertToSimpleQuestion(SimpleQuestionDto simpleQuestionDto) {
        SimpleQuestion simpleQuestion = new SimpleQuestion();

        simpleQuestion.setId(simpleQuestionDto.getId());
        simpleQuestion.setQuestionText(simpleQuestionDto.getQuestionText());
        simpleQuestion.setQuestionType(simpleQuestionDto.getQuestionType());

        return simpleQuestion;
    }

    public static User convertToUser(UserDto userDto) {
        User user = new User();

        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setSurname(userDto.getSurname());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setConfirmed(userDto.getConfirmed());
        user.setToken(userDto.getToken());
        user.setUserType(userDto.getUserType());

        return user;
    }

    public static Client convertToClient(ClientDto clientDto) {
        Client client = new Client();

        test(client, clientDto.getId(), clientDto.getFirstName(), clientDto.getSurname(), clientDto.getAge(), clientDto.getEmail(), clientDto.getPassword(), clientDto.getConfirmed(), clientDto.getToken(), clientDto.getUserType());

        return client;
    }

    private static void test(Client client, Integer id, String firstName, String surname, Integer age, String email, String password, Boolean confirmed, String token, UserType userType) {
        client.setId(id);
        client.setFirstName(firstName);
        client.setSurname(surname);
        client.setAge(age);
        client.setEmail(email);
        client.setPassword(password);
        client.setConfirmed(confirmed);
        client.setToken(token);
        client.setUserType(userType);
    }

    public static Client convertToClient(UserDto userDto) {
        Client client = new Client();

        client.setId(userDto.getId());
        client.setFirstName(userDto.getFirstName());
        client.setSurname(userDto.getSurname());
        client.setAge(userDto.getAge());
        client.setEmail(userDto.getEmail());
        client.setPassword(userDto.getPassword());
        client.setConfirmed(userDto.getConfirmed());
        client.setToken(userDto.getToken());
        client.setUserType(userDto.getUserType());

        return client;
    }

    public static Admin convertToAdmin(UserDto userDto) {
        Admin admin = new Admin();

        admin.setId(userDto.getId());
        admin.setFirstName(userDto.getFirstName());
        admin.setSurname(userDto.getSurname());
        admin.setAge(userDto.getAge());
        admin.setEmail(userDto.getEmail());
        admin.setPassword(userDto.getPassword());
        admin.setConfirmed(userDto.getConfirmed());
        admin.setToken(userDto.getToken());
        admin.setUserType(userDto.getUserType());

        return admin;
    }
}
