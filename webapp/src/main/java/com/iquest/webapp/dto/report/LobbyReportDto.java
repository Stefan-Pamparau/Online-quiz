package com.iquest.webapp.dto.report;

import com.iquest.model.Lobby;
import com.iquest.model.user.User;
import com.iquest.model.user.UserLobbySession;

import java.util.List;

public class LobbyReportDto {
    private Lobby lobby;
    private List<UserLobbySession> users;

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public List<UserLobbySession> getUsers() {
        return users;
    }

    public void setUsers(List<UserLobbySession> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LobbyReportDto that = (LobbyReportDto) o;

        if (lobby != null ? !lobby.equals(that.lobby) : that.lobby != null) return false;
        return users != null ? users.equals(that.users) : that.users == null;

    }

    @Override
    public int hashCode() {
        int result = lobby != null ? lobby.hashCode() : 0;
        result = 31 * result + (users != null ? users.hashCode() : 0);
        return result;
    }
}
