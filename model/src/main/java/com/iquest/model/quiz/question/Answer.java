package com.iquest.model.quiz.question;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "multiple_choice_answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private MultipleChoiceQuestion question;

    private String answerText;

    private Boolean isCorrect;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MultipleChoiceQuestion getQuestion() {
        return question;
    }

    public void setQuestion(MultipleChoiceQuestion question) {
        this.question = question;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer that = (Answer) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (answerText != null ? !answerText.equals(that.answerText) : that.answerText != null) return false;
        return isCorrect != null ? isCorrect.equals(that.isCorrect) : that.isCorrect == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (answerText != null ? answerText.hashCode() : 0);
        result = 31 * result + (isCorrect != null ? isCorrect.hashCode() : 0);
        return result;
    }
}
