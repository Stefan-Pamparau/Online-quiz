package com.iquest.model.quiz;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.iquest.model.Lobby;
import com.iquest.model.quiz.question.MultipleChoiceQuestion;
import com.iquest.model.quiz.question.SingleChoiceQuestion;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "quiz")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "quiz_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "SIMPLE_QUIZ")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@quizId")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(mappedBy = "quiz", optional = false)
    private Lobby lobby;

    @OneToMany(mappedBy = "quiz")
    private Set<SingleChoiceQuestion> singleChoiceQuestions;

    @OneToMany(mappedBy = "quiz")
    private Set<MultipleChoiceQuestion> multipleChoiceQuestions;

    @Column(name = "quiz_type", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private QuizType quizType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public Set<SingleChoiceQuestion> getSingleChoiceQuestions() {
        return singleChoiceQuestions;
    }

    public void setSingleChoiceQuestions(Set<SingleChoiceQuestion> singleChoiceQuestions) {
        this.singleChoiceQuestions = singleChoiceQuestions;
    }

    public Set<MultipleChoiceQuestion> getMultipleChoiceQuestions() {
        return multipleChoiceQuestions;
    }

    public void setMultipleChoiceQuestions(Set<MultipleChoiceQuestion> multipleChoiceQuestions) {
        this.multipleChoiceQuestions = multipleChoiceQuestions;
    }

    public QuizType getQuizType() {
        return quizType;
    }

    public void setQuizType(QuizType quizType) {
        this.quizType = quizType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quiz quiz = (Quiz) o;

        if (id != null ? !id.equals(quiz.id) : quiz.id != null) return false;
        return quizType == quiz.quizType;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (quizType != null ? quizType.hashCode() : 0);
        return result;
    }
}
