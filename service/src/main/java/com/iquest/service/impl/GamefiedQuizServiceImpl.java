package com.iquest.service.impl;

import com.iquest.dao.GamefiedQuizDao;
import com.iquest.model.quiz.GamefiedQuiz;
import com.iquest.service.GamefiedQuizService;
import com.iquest.service.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("gamefiedQuizService")
@Transactional
public class GamefiedQuizServiceImpl implements GamefiedQuizService {

    @Autowired
    private GamefiedQuizDao gamefiedQuizDao;

    @Override
    public GamefiedQuiz save(GamefiedQuiz gamefiedQuiz) {
        return gamefiedQuizDao.save(gamefiedQuiz);
    }

    @Override
    public GamefiedQuiz findWithId(Integer id) {
        return gamefiedQuizDao.findOne(id);
    }

    @Override
    public Boolean exists(Integer id) {
        return gamefiedQuizDao.exists(id);
    }

    @Override
    public List<GamefiedQuiz> findAll() {
        return ServiceUtil.convertFromIterableToList(gamefiedQuizDao.findAll());
    }

    @Override
    public Long getNumberOfGamefiedQuizzes() {
        return gamefiedQuizDao.count();
    }

    @Override
    public void delete(Integer id) {
        gamefiedQuizDao.delete(id);
    }

    @Override
    public void delete(GamefiedQuiz gamefiedQuiz) {
        gamefiedQuizDao.delete(gamefiedQuiz);
    }

    @Override
    public void deleteAll() {
        gamefiedQuizDao.deleteAll();
    }

    public GamefiedQuizDao getGamefiedQuizDao() {
        return gamefiedQuizDao;
    }

    public void setGamefiedQuizDao(GamefiedQuizDao gamefiedQuizDao) {
        this.gamefiedQuizDao = gamefiedQuizDao;
    }
}
