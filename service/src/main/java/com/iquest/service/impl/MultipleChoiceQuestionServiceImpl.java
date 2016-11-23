package com.iquest.service.impl;

import com.iquest.dao.MultipleChoiceQuestionDao;
import com.iquest.model.quiz.question.MultipleChoiceQuestion;
import com.iquest.service.MultipleChoiceQuestionService;
import com.iquest.service.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("multipleChoiceQuestionService")
@Transactional
public class MultipleChoiceQuestionServiceImpl implements MultipleChoiceQuestionService {

    @Autowired
    private MultipleChoiceQuestionDao multipleChoiceQuestionDao;

    @Override
    public MultipleChoiceQuestion save(MultipleChoiceQuestion multipleChoiceQuestion) {
        return multipleChoiceQuestionDao.save(multipleChoiceQuestion);
    }

    @Override
    public MultipleChoiceQuestion findWithId(Integer id) {
        return multipleChoiceQuestionDao.findOne(id);
    }

    @Override
    public Boolean exists(Integer id) {
        return multipleChoiceQuestionDao.exists(id);
    }

    @Override
    public List<MultipleChoiceQuestion> findAll() {
        return ServiceUtil.convertFromIterableToList(multipleChoiceQuestionDao.findAll());
    }

    @Override
    public Long getNumberOfMultipleChoiceQuestions() {
        return multipleChoiceQuestionDao.count();
    }

    @Override
    public void delete(Integer id) {
        multipleChoiceQuestionDao.delete(id);
    }

    @Override
    public void delete(MultipleChoiceQuestion multipleChoiceQuestion) {
        multipleChoiceQuestionDao.delete(multipleChoiceQuestion);
    }

    @Override
    public void deleteAll() {
        multipleChoiceQuestionDao.deleteAll();
    }

    public MultipleChoiceQuestionDao getMultipleChoiceQuestionDao() {
        return multipleChoiceQuestionDao;
    }

    public void setMultipleChoiceQuestionDao(MultipleChoiceQuestionDao multipleChoiceQuestionDao) {
        this.multipleChoiceQuestionDao = multipleChoiceQuestionDao;
    }
}
