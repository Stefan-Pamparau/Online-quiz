package com.iquest.webapp.util;

import com.iquest.model.Lobby;
import com.iquest.model.quiz.ExamQuiz;
import com.iquest.model.quiz.GamefiedQuiz;
import com.iquest.model.quiz.Quiz;
import com.iquest.model.quiz.answer.MultipleChoiceAnswer;
import com.iquest.model.quiz.answer.SimpleAnswer;
import com.iquest.model.quiz.question.MultipleChoiceQuestion;
import com.iquest.model.quiz.question.Question;
import com.iquest.model.quiz.question.SimpleQuestion;
import com.iquest.model.user.Admin;
import com.iquest.model.user.Client;
import com.iquest.model.user.User;
import com.iquest.webapp.dto.frommodel.AdminDto;
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

public class ModelToDtoConverter {

    public static QuizDto convertToQuizDto(Quiz quiz) {
        QuizDto quizDto = new QuizDto();

        quizDto.setId(quiz.getId());
        quizDto.setQuizType(quiz.getQuizType());
        quizDto.setTitle(quiz.getTitle());
        quizDto.setDescription(quiz.getDescription());

        return quizDto;
    }

    public static ExamQuizDto convertToExamQuizDto(ExamQuiz examQuiz) {
        ExamQuizDto examQuizDto = new ExamQuizDto();

        examQuizDto.setId(examQuiz.getId());
        examQuizDto.setQuizType(examQuiz.getQuizType());
        examQuizDto.setTitle(examQuiz.getTitle());
        examQuizDto.setDescription(examQuiz.getDescription());

        return examQuizDto;
    }

    public static GamefiedQuizDto convertToGamefiedQuizDto(GamefiedQuiz gamefiedQuiz) {
        GamefiedQuizDto gamefiedQuizDto = new GamefiedQuizDto();

        gamefiedQuizDto.setId(gamefiedQuiz.getId());
        gamefiedQuizDto.setQuizType(gamefiedQuiz.getQuizType());
        gamefiedQuizDto.setTitle(gamefiedQuiz.getTitle());
        gamefiedQuizDto.setDescription(gamefiedQuiz.getDescription());

        return gamefiedQuizDto;
    }

    public static LobbyDto convertToLobbyDto(Lobby lobby) {
        LobbyDto lobbyDto = new LobbyDto();

        lobbyDto.setId(lobby.getId());
        lobbyDto.setCreationDate(lobby.getCreationDate());
        lobbyDto.setSecondsUntilStart(lobby.getSecondsUntilStart());

        return lobbyDto;
    }

    public static MultipleChoiceAnswerDto convertToMultipleChoiceAnswerDto(MultipleChoiceAnswer multipleChoiceAnswer) {
        MultipleChoiceAnswerDto multipleChoiceAnswerDto = new MultipleChoiceAnswerDto();

        multipleChoiceAnswerDto.setId(multipleChoiceAnswer.getId());
        multipleChoiceAnswerDto.setAnswerText(multipleChoiceAnswer.getAnswerText());
        multipleChoiceAnswerDto.setAnswerType(multipleChoiceAnswer.getAnswerType());
        multipleChoiceAnswerDto.setCorrect(multipleChoiceAnswer.getCorrect());

        return multipleChoiceAnswerDto;
    }

    public static MultipleChoiceQuestionDto convertToMultipleChoiceQuestionDto(MultipleChoiceQuestion multipleChoiceQuestion) {
        MultipleChoiceQuestionDto multipleChoiceQuestionDto = new MultipleChoiceQuestionDto();

        multipleChoiceQuestionDto.setId(multipleChoiceQuestion.getId());
        multipleChoiceQuestionDto.setQuestionText(multipleChoiceQuestion.getQuestionText());
        multipleChoiceQuestionDto.setQuestionType(multipleChoiceQuestion.getQuestionType());

        return multipleChoiceQuestionDto;
    }

    public static SimpleAnswerDto convertToSimpleAnswerDto(SimpleAnswer simpleAnswer) {
        SimpleAnswerDto simpleAnswerDto = new SimpleAnswerDto();

        simpleAnswerDto.setId(simpleAnswer.getId());
        simpleAnswerDto.setAnswerText(simpleAnswer.getAnswerText());
        simpleAnswerDto.setAnswerType(simpleAnswer.getAnswerType());

        return simpleAnswerDto;
    }

    public static SimpleQuestionDto convertToSimpleQuestionDto(SimpleQuestion simpleQuestion) {
        return getSimpleQuestionDto(simpleQuestion);
    }

    public static SimpleQuestionDto convertToSimpleQuestionDto(Question simpleQuestion) {
        return getSimpleQuestionDto(simpleQuestion);
    }

    private static SimpleQuestionDto getSimpleQuestionDto(Question simpleQuestion) {
        SimpleQuestionDto simpleQuestionDto = new SimpleQuestionDto();

        simpleQuestionDto.setId(simpleQuestion.getId());
        simpleQuestionDto.setQuestionText(simpleQuestion.getQuestionText());
        simpleQuestionDto.setQuestionType(simpleQuestion.getQuestionType());

        return simpleQuestionDto;
    }

    public static UserDto convertToUserDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setSurname(user.getSurname());
        userDto.setAge(user.getAge());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setConfirmed(user.getConfirmed());
        userDto.setToken(user.getToken());
        userDto.setUserType(user.getUserType());

        return userDto;
    }

    public static ClientDto convertToClientDto(Client client) {
        ClientDto clientDto = new ClientDto();

        clientDto.setId(client.getId());
        clientDto.setFirstName(client.getFirstName());
        clientDto.setSurname(client.getSurname());
        clientDto.setAge(client.getAge());
        clientDto.setEmail(client.getEmail());
        clientDto.setPassword(client.getPassword());
        clientDto.setConfirmed(client.getConfirmed());
        clientDto.setToken(client.getToken());
        clientDto.setUserType(client.getUserType());

        return clientDto;
    }

    public static AdminDto convertToAdminDto(Admin admin) {
        AdminDto adminDto = new AdminDto();

        adminDto.setId(admin.getId());
        adminDto.setFirstName(admin.getFirstName());
        adminDto.setSurname(admin.getSurname());
        adminDto.setAge(admin.getAge());
        adminDto.setEmail(admin.getEmail());
        adminDto.setPassword(admin.getPassword());
        adminDto.setConfirmed(admin.getConfirmed());
        adminDto.setToken(admin.getToken());
        adminDto.setUserType(admin.getUserType());

        return adminDto;
    }

    public static ExamQuizDto convertToExamQuizDto(Quiz quiz) {
        ExamQuizDto examQuizDto = new ExamQuizDto();

        examQuizDto.setId(quiz.getId());
        examQuizDto.setQuizType(quiz.getQuizType());
        examQuizDto.setTitle(quiz.getTitle());
        examQuizDto.setDescription(quiz.getDescription());

        return examQuizDto;
    }

    public static GamefiedQuizDto convertToGamefiedQuizDto(Quiz quiz) {
        GamefiedQuizDto gamefiedQuizDto = new GamefiedQuizDto();

        gamefiedQuizDto.setId(quiz.getId());
        gamefiedQuizDto.setQuizType(quiz.getQuizType());
        gamefiedQuizDto.setTitle(quiz.getTitle());
        gamefiedQuizDto.setDescription(quiz.getDescription());

        return gamefiedQuizDto;
    }
}
