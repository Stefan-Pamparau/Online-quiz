package com.iquest.service.impl;

import com.iquest.dao.ExamQuizDao;
import com.iquest.model.quiz.ExamQuiz;
import com.iquest.model.quiz.question.MultipleChoiceQuestion;
import com.iquest.model.quiz.question.Question;
import com.iquest.model.quiz.question.QuestionType;
import com.iquest.model.quiz.question.SimpleQuestion;
import com.iquest.service.ExamQuizService;
import com.iquest.service.MultipleChoiceQuestionService;
import com.iquest.service.SimpleQuestionService;
import com.iquest.service.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("examQuizService")
@Transactional
public class ExamQuizServiceImpl implements ExamQuizService {

    private ExamQuizDao examQuizDao;
    private SimpleQuestionService simpleQuestionService;
    private MultipleChoiceQuestionService multipleChoiceQuestionService;

    @Autowired
    public ExamQuizServiceImpl(ExamQuizDao examQuizDao, SimpleQuestionService simpleQuestionService, MultipleChoiceQuestionService multipleChoiceQuestionService) {
        this.examQuizDao = examQuizDao;
        this.simpleQuestionService = simpleQuestionService;
        this.multipleChoiceQuestionService = multipleChoiceQuestionService;
    }

    @Override
    public ExamQuiz save(ExamQuiz examQuiz) {
        if (examQuiz.getQuestions() != null) {
            //saveQuestions(examQuiz.getQuestions());
        }
        return examQuizDao.save(examQuiz);
    }

    private void saveQuestions(List<Question> questions) {
        for (Question question : questions) {
            if (QuestionType.SIMPLE_QUESTION == question.getQuestionType()) {
                simpleQuestionService.save((SimpleQuestion) question);
            } else {
                multipleChoiceQuestionService.save((MultipleChoiceQuestion) question);
            }
        }
    }

    @Override
    public ExamQuiz findWithId(Integer id) {
        return examQuizDao.findOne(id);
    }

    @Override
    public Boolean exists(Integer id) {
        return examQuizDao.exists(id);
    }

    @Override
    public List<ExamQuiz> findAll() {
        return ServiceUtil.convertFromIterableToList(examQuizDao.findAll());
    }

    @Override
    public Long getNumberOfExamQuizzes() {
        return examQuizDao.count();
    }

    @Override
    public void delete(Integer id) {
        examQuizDao.delete(id);
    }

    @Override
    public void delete(ExamQuiz examQuiz) {
        examQuizDao.delete(examQuiz);
    }

    @Override
    public void deleteAll() {
        examQuizDao.deleteAll();
    }

    public ExamQuizDao getExamQuizDao() {
        return examQuizDao;
    }

    public void setExamQuizDao(ExamQuizDao examQuizDao) {
        this.examQuizDao = examQuizDao;
    }
}
