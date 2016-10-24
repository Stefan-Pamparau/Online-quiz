package com.iquest.model.user;

import com.iquest.model.quiz.Quiz;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@DiscriminatorValue(value = "CLIENT")
public class Client extends User {

    @OneToMany(mappedBy = "client")
    private List<Quiz> quizzes;
}
