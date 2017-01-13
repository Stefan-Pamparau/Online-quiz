package com.iquest.webapp.util;

import com.iquest.model.Lobby;
import com.iquest.model.quiz.ExamQuiz;
import com.iquest.model.quiz.GamefiedQuiz;
import com.iquest.model.quiz.answer.MultipleChoiceAnswer;
import com.iquest.model.quiz.answer.SimpleAnswer;
import com.iquest.model.quiz.question.MultipleChoiceQuestion;
import com.iquest.model.quiz.question.SimpleQuestion;
import com.iquest.model.user.User;
import com.iquest.webapp.dto.frommodel.ExamQuizDto;
import com.iquest.webapp.dto.frommodel.GamefiedQuizDto;
import com.iquest.webapp.dto.frommodel.LobbyDto;
import com.iquest.webapp.dto.frommodel.MultipleChoiceAnswerDto;
import com.iquest.webapp.dto.frommodel.MultipleChoiceQuestionDto;
import com.iquest.webapp.dto.frommodel.SimpleAnswerDto;
import com.iquest.webapp.dto.frommodel.SimpleQuestionDto;
import com.iquest.webapp.dto.frommodel.UserDto;

public class DtoToModelConverter {

    public static ExamQuiz convertToExamQuiz(ExamQuizDto examQuizDto) {
        ExamQuiz examQuiz = new ExamQuiz();

        examQuiz.setId(examQuizDto.getId());
        examQuiz.setQuizType(examQuizDto.getQuizType());

        return examQuiz;
    }

    public static GamefiedQuiz convertToGamefiedQuiz(GamefiedQuizDto gamefiedQuizDto) {
        GamefiedQuiz gamefiedQuiz = new GamefiedQuiz();

        gamefiedQuiz.setId(gamefiedQuizDto.getId());
        gamefiedQuiz.setQuizType(gamefiedQuizDto.getQuizType());

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
}
