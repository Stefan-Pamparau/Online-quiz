package com.iquest.model;

import com.iquest.model.quiz.Quiz;
import com.iquest.model.user.UserLobbySession;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "lobby")
public class Lobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    Date creationDate;

    @OneToMany(mappedBy = "lobby")
    private Set<UserLobbySession> users;

    @OneToOne(optional = false)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Set<UserLobbySession> getUsers() {
        return users;
    }

    public void setUsers(Set<UserLobbySession> users) {
        this.users = users;
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

        Lobby lobby = (Lobby) o;

        if (id != null ? !id.equals(lobby.id) : lobby.id != null) return false;
        return creationDate != null ? creationDate.equals(lobby.creationDate) : lobby.creationDate == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }
}
