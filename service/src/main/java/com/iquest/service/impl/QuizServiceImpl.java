package com.iquest.service.impl;

import com.iquest.dao.QuizDao;
import com.iquest.model.quiz.Quiz;
import com.iquest.service.QuizService;
import com.iquest.service.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("quizService")
@Transactional
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizDao quizDao;

    @Override
    public Quiz save(Quiz quiz) {
        return quizDao.save(quiz);
    }

    @Override
    public Quiz findWithId(Integer id) {
        return quizDao.findOne(id);
    }

    @Override
    public Boolean exists(Integer id) {
        return quizDao.exists(id);
    }

    @Override
    public List<Quiz> findAll() {
        return ServiceUtil.convertFromIterableToList(quizDao.findAll());
    }

    @Override
    public Long getNumberOfAdmins() {
        return quizDao.count();
    }

    @Override
    public void delete(Integer id) {
        quizDao.delete(id);
    }

    @Override
    public void delete(Quiz quiz) {
        quizDao.delete(quiz);
    }

    @Override
    public void deleteAll() {
        quizDao.deleteAll();
    }

    public QuizDao getQuizDao() {
        return quizDao;
    }

    public void setQuizDao(QuizDao quizDao) {
        this.quizDao = quizDao;
    }
}
