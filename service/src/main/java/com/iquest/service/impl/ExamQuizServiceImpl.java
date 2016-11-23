package com.iquest.service.impl;

import com.iquest.dao.ExamQuizDao;
import com.iquest.model.quiz.ExamQuiz;
import com.iquest.service.ExamQuizService;
import com.iquest.service.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("examQuizService")
@Transactional
public class ExamQuizServiceImpl implements ExamQuizService {

    @Autowired
    private ExamQuizDao examQuizDao;

    @Override
    public ExamQuiz save(ExamQuiz examQuiz) {
        return examQuizDao.save(examQuiz);
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
