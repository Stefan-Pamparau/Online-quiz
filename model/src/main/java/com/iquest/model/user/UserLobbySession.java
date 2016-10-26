package com.iquest.model.user;

import com.iquest.model.Lobby;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

public class UserLobbySession {
    @EmbeddedId
    private UserLobbyId id = new UserLobbyId();

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "lobby_id", insertable = false, updatable = false)
    private Lobby lobby;

    public UserLobbyId getId() {
        return id;
    }

    public void setId(UserLobbyId id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserLobbySession that = (UserLobbySession) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Embeddable
    public static class UserLobbyId implements Serializable {
        @Column(name = "user_id")
        private Integer userId;

        @Column(name = "lobby_id")
        private Integer lobbyId;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getLobbyId() {
            return lobbyId;
        }

        public void setLobbyId(Integer lobbyId) {
            this.lobbyId = lobbyId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            UserLobbyId that = (UserLobbyId) o;

            if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
            return lobbyId != null ? lobbyId.equals(that.lobbyId) : that.lobbyId == null;

        }

        @Override
        public int hashCode() {
            int result = userId != null ? userId.hashCode() : 0;
            result = 31 * result + (lobbyId != null ? lobbyId.hashCode() : 0);
            return result;
        }
    }
}
