package com.iquest.service.impl;

import com.iquest.dao.MultipleChoiceAnswerDao;
import com.iquest.model.quiz.answer.MultipleChoiceAnswer;
import com.iquest.service.MultipleChoiceAnswerService;
import com.iquest.service.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("multipleChoiceAnswerService")
@Transactional
public class MultipleChoiceAnswerServiceImpl implements MultipleChoiceAnswerService {

    @Autowired
    private MultipleChoiceAnswerDao multipleChoiceAnswerDao;

    @Override
    public MultipleChoiceAnswer save(MultipleChoiceAnswer multipleChoiceAnswer) {
        return multipleChoiceAnswerDao.save(multipleChoiceAnswer);
    }

    @Override
    public MultipleChoiceAnswer findWithId(Integer id) {
        return multipleChoiceAnswerDao.findOne(id);
    }

    @Override
    public Boolean exists(Integer id) {
        return multipleChoiceAnswerDao.exists(id);
    }

    @Override
    public List<MultipleChoiceAnswer> findAll() {
        return ServiceUtil.convertFromIterableToList(multipleChoiceAnswerDao.findAll());
    }

    @Override
    public Long getNumberOfAdmins() {
        return multipleChoiceAnswerDao.count();
    }

    @Override
    public void delete(Integer id) {
        multipleChoiceAnswerDao.delete(id);
    }

    @Override
    public void delete(MultipleChoiceAnswer multipleChoiceAnswer) {
        multipleChoiceAnswerDao.delete(multipleChoiceAnswer);
    }

    @Override
    public void deleteAll() {
        multipleChoiceAnswerDao.deleteAll();
    }

    public MultipleChoiceAnswerDao getMultipleChoiceAnswerDao() {
        return multipleChoiceAnswerDao;
    }

    public void setMultipleChoiceAnswerDao(MultipleChoiceAnswerDao multipleChoiceAnswerDao) {
        this.multipleChoiceAnswerDao = multipleChoiceAnswerDao;
    }
}
