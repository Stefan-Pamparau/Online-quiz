package com.iquest.service.impl;

import com.iquest.dao.AnswerDao;
import com.iquest.model.quiz.answer.Answer;
import com.iquest.service.AnswerService;
import com.iquest.service.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("answerService")
@Transactional
public class AnswerServiceImpl implements AnswerService {

    private AnswerDao answerDao;

    @Autowired
    public AnswerServiceImpl(AnswerDao answerDao) {
        this.answerDao = answerDao;
    }


    @Override
    public Answer save(Answer answer) {
        return answerDao.save(answer);
    }

    @Override
    public Answer findWithId(Integer id) {
        return answerDao.findOne(id);
    }

    @Override
    public Boolean exists(Integer id) {
        return answerDao.exists(id);
    }

    @Override
    public List<Answer> findAll() {
        return ServiceUtil.convertFromIterableToList(answerDao.findAll());
    }

    @Override
    public Long getNumberOfAnswers() {
        return answerDao.count();

    }

    @Override
    public void delete(Integer id) {
        answerDao.delete(id);
    }

    @Override
    public void delete(Answer answer) {
        answerDao.delete(answer);
    }

    @Override
    public void deleteAll() {
        answerDao.deleteAll();
    }

    public AnswerDao getAnswerDao() {
        return answerDao;
    }

    public void setAnswerDao(AnswerDao answerDao) {
        this.answerDao = answerDao;
    }
}
