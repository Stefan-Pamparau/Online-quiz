package com.iquest.webapp.sessionmanagement;

import com.iquest.model.Lobby;
import com.iquest.model.quiz.Quiz;
import com.iquest.model.user.User;

public class Session {

    private User user;
    private Lobby lobby;
    private Quiz quiz;

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

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Session session = (Session) o;

        if (user != null ? !user.equals(session.user) : session.user != null) return false;
        if (lobby != null ? !lobby.equals(session.lobby) : session.lobby != null) return false;
        return quiz != null ? quiz.equals(session.quiz) : session.quiz == null;

    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (lobby != null ? lobby.hashCode() : 0);
        result = 31 * result + (quiz != null ? quiz.hashCode() : 0);
        return result;
    }
}
