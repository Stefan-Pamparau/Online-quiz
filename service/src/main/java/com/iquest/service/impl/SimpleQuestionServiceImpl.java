package com.iquest.service.impl;

import com.iquest.dao.SimpleQuestionDao;
import com.iquest.model.quiz.question.SimpleQuestion;
import com.iquest.service.SimpleAnswerService;
import com.iquest.service.SimpleQuestionService;
import com.iquest.service.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("simpleQuestionService")
@Transactional
public class SimpleQuestionServiceImpl implements SimpleQuestionService {

    private SimpleQuestionDao simpleQuestionDao;
    private SimpleAnswerService simpleAnswerService;

    @Autowired
    public SimpleQuestionServiceImpl(SimpleQuestionDao simpleQuestionDao, SimpleAnswerService simpleAnswerService) {
        this.simpleQuestionDao = simpleQuestionDao;
        this.simpleAnswerService = simpleAnswerService;
    }

    @Override
    public SimpleQuestion save(SimpleQuestion simpleQuestion) {
        if (simpleQuestion.getAnswer() != null) {
            //simpleAnswerService.save(simpleQuestion.getAnswer());
        }
        return simpleQuestionDao.save(simpleQuestion);
    }

    @Override
    public SimpleQuestion findWithId(Integer id) {
        return simpleQuestionDao.findOne(id);
    }

    @Override
    public Boolean exists(Integer id) {
        return simpleQuestionDao.exists(id);
    }

    @Override
    public List<SimpleQuestion> findAll() {
        return ServiceUtil.convertFromIterableToList(simpleQuestionDao.findAll());
    }

    @Override
    public Long getNumberOfSimpleQuestions() {
        return simpleQuestionDao.count();
    }

    @Override
    public void delete(Integer id) {
        simpleQuestionDao.delete(id);
    }

    @Override
    public void delete(SimpleQuestion simpleQuestion) {
        simpleQuestionDao.delete(simpleQuestion);
    }

    @Override
    public void deleteAll() {
        simpleQuestionDao.deleteAll();
    }

    public SimpleQuestionDao getSimpleQuestionDao() {
        return simpleQuestionDao;
    }

    public void setSimpleQuestionDao(SimpleQuestionDao simpleQuestionDao) {
        this.simpleQuestionDao = simpleQuestionDao;
    }
}
