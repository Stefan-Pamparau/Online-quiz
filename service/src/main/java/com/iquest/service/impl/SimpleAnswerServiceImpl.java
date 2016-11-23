package com.iquest.service.impl;

import com.iquest.dao.SimpleAnswerDao;
import com.iquest.model.quiz.answer.SimpleAnswer;
import com.iquest.service.SimpleAnswerService;
import com.iquest.service.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("simpleAnswerService")
@Transactional
public class SimpleAnswerServiceImpl implements SimpleAnswerService {

    @Autowired
    private SimpleAnswerDao simpleAnswerDao;

    @Override
    public SimpleAnswer save(SimpleAnswer simpleAnswer) {
        return simpleAnswerDao.save(simpleAnswer);
    }

    @Override
    public SimpleAnswer findWithId(Integer id) {
        return simpleAnswerDao.findOne(id);
    }

    @Override
    public Boolean exists(Integer id) {
        return simpleAnswerDao.exists(id);
    }

    @Override
    public List<SimpleAnswer> findAll() {
        return ServiceUtil.convertFromIterableToList(simpleAnswerDao.findAll());
    }

    @Override
    public Long getNumberOfSimpleAnswers() {
        return simpleAnswerDao.count();
    }

    @Override
    public void delete(Integer id) {
        simpleAnswerDao.delete(id);
    }

    @Override
    public void delete(SimpleAnswer simpleAnswer) {
        simpleAnswerDao.delete(simpleAnswer);
    }

    @Override
    public void deleteAll() {
        simpleAnswerDao.deleteAll();
    }

    public SimpleAnswerDao getSimpleAnswerDao() {
        return simpleAnswerDao;
    }

    public void setSimpleAnswerDao(SimpleAnswerDao simpleAnswerDao) {
        this.simpleAnswerDao = simpleAnswerDao;
    }
}
