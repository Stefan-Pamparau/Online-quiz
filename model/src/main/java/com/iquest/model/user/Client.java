package com.iquest.model.user;

import com.iquest.model.quiz.Quiz;
import com.iquest.model.quiz.question.Question;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@DiscriminatorValue(value = "CLIENT")
public class Client extends User {

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Quiz> quizzes;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Question> questions;

    @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL)
    private List<Friendship> friendships;

    public void addFriendship(Friendship friendship) {
        friendships.add(friendship);
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Friendship> getFriendships() {
        return friendships;
    }

    public void setFriendships(List<Friendship> friendships) {
        this.friendships = friendships;
    }
}
