package com.iquest.webapp.dto.report;

import com.iquest.model.quiz.question.Question;
import com.iquest.model.user.Client;
import com.iquest.model.user.UserLobbySession;

import java.util.List;

public class ClientReportDto {
    private Client client;
    private List<Question> questions;
    private List<UserLobbySession> lobbies;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<UserLobbySession> getLobbies() {
        return lobbies;
    }

    public void setLobbies(List<UserLobbySession> lobbies) {
        this.lobbies = lobbies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientReportDto that = (ClientReportDto) o;

        if (client != null ? !client.equals(that.client) : that.client != null) return false;
        if (questions != null ? !questions.equals(that.questions) : that.questions != null) return false;
        return lobbies != null ? lobbies.equals(that.lobbies) : that.lobbies == null;

    }

    @Override
    public int hashCode() {
        int result = client != null ? client.hashCode() : 0;
        result = 31 * result + (questions != null ? questions.hashCode() : 0);
        result = 31 * result + (lobbies != null ? lobbies.hashCode() : 0);
        return result;
    }
}
