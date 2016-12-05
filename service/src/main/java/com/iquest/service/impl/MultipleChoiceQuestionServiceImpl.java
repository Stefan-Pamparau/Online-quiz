package com.iquest.service.impl;

import com.iquest.dao.MultipleChoiceQuestionDao;
import com.iquest.model.quiz.answer.MultipleChoiceAnswer;
import com.iquest.model.quiz.question.MultipleChoiceQuestion;
import com.iquest.service.MultipleChoiceAnswerService;
import com.iquest.service.MultipleChoiceQuestionService;
import com.iquest.service.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("multipleChoiceQuestionService")
@Transactional
public class MultipleChoiceQuestionServiceImpl implements MultipleChoiceQuestionService {

    private MultipleChoiceQuestionDao multipleChoiceQuestionDao;
    private MultipleChoiceAnswerService multipleChoiceAnswerService;

    @Autowired
    public MultipleChoiceQuestionServiceImpl(MultipleChoiceQuestionDao multipleChoiceQuestionDao, MultipleChoiceAnswerService multipleChoiceAnswerService) {
        this.multipleChoiceQuestionDao = multipleChoiceQuestionDao;
        this.multipleChoiceAnswerService = multipleChoiceAnswerService;
    }

    @Override
    public MultipleChoiceQuestion save(MultipleChoiceQuestion multipleChoiceQuestion) {
        if (multipleChoiceQuestion.getAnswers() != null) {
           // saveAnswers(multipleChoiceQuestion.getAnswers());
        }
        return multipleChoiceQuestionDao.save(multipleChoiceQuestion);
    }

    private void saveAnswers(List<MultipleChoiceAnswer> answers) {
        for (MultipleChoiceAnswer multipleChoiceAnswer : answers) {
            multipleChoiceAnswerService.save(multipleChoiceAnswer);
        }
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
