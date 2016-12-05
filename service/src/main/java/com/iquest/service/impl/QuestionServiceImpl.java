package com.iquest.service.impl;

import com.iquest.dao.QuestionDao;
import com.iquest.model.quiz.question.Question;
import com.iquest.service.QuestionService;
import com.iquest.service.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("questionService")
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private QuestionDao questionDao;

    @Autowired
    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public Question save(Question question) {
        return questionDao.save(question);
    }

    @Override
    public Question findWithId(Integer id) {
        return questionDao.findOne(id);
    }

    @Override
    public Boolean exists(Integer id) {
        return questionDao.exists(id);
    }

    @Override
    public List<Question> findAll() {
        return ServiceUtil.convertFromIterableToList(questionDao.findAll());
    }

    @Override
    public Long getNumberOfQuestions() {
        return questionDao.count();
    }

    @Override
    public void delete(Integer id) {
        questionDao.delete(id);
    }

    @Override
    public void delete(Question question) {
        questionDao.delete(question);
    }

    @Override
    public void deleteAll() {
        questionDao.deleteAll();
    }

    public QuestionDao getQuestionDao() {
        return questionDao;
    }

    public void setQuestionDao(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }
}
