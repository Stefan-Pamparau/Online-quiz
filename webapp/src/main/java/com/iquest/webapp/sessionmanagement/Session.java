package com.iquest.webapp.sessionmanagement;

import com.iquest.model.Lobby;
import com.iquest.model.quiz.ExamQuiz;
import com.iquest.model.quiz.GamefiedQuiz;
import com.iquest.model.user.User;

public class Session {

    private User user;
    private Lobby lobby;
    private GamefiedQuiz gamefiedQuiz;
    private ExamQuiz examQuiz;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public GamefiedQuiz getGamefiedQuiz() {
        return gamefiedQuiz;
    }

    public void setGamefiedQuiz(GamefiedQuiz gamefiedQuiz) {
        this.gamefiedQuiz = gamefiedQuiz;
    }

    public ExamQuiz getExamQuiz() {
        return examQuiz;
    }

    public void setExamQuiz(ExamQuiz examQuiz) {
        this.examQuiz = examQuiz;
    }
}
