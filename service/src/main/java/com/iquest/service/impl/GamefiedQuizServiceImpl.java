package com.iquest.service.impl;

import com.iquest.dao.GamefiedQuizDao;
import com.iquest.model.quiz.GamefiedQuiz;
import com.iquest.model.quiz.question.MultipleChoiceQuestion;
import com.iquest.model.quiz.question.Question;
import com.iquest.model.quiz.question.QuestionType;
import com.iquest.model.quiz.question.SimpleQuestion;
import com.iquest.service.GamefiedQuizService;
import com.iquest.service.MultipleChoiceQuestionService;
import com.iquest.service.SimpleQuestionService;
import com.iquest.service.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("gamefiedQuizService")
@Transactional
public class GamefiedQuizServiceImpl implements GamefiedQuizService {

    private GamefiedQuizDao gamefiedQuizDao;
    private SimpleQuestionService simpleQuestionService;
    private MultipleChoiceQuestionService multipleChoiceQuestionService;

    @Autowired
    public GamefiedQuizServiceImpl(GamefiedQuizDao gamefiedQuizDao, SimpleQuestionService simpleQuestionService, MultipleChoiceQuestionService multipleChoiceQuestionService) {
        this.gamefiedQuizDao = gamefiedQuizDao;
        this.simpleQuestionService = simpleQuestionService;
        this.multipleChoiceQuestionService = multipleChoiceQuestionService;
    }

    @Override
    public GamefiedQuiz save(GamefiedQuiz gamefiedQuiz) {
        if (gamefiedQuiz.getQuestions() != null) {
            //saveQuestions(gamefiedQuiz.getQuestions());
        }
        return gamefiedQuizDao.save(gamefiedQuiz);
    }

    private void saveQuestions(List<Question> questions) {
        for (Question question : questions) {
            if (QuestionType.SIMPLE_QUESTION == question.getQuestionType()) {
                simpleQuestionService.save((SimpleQuestion) question);
            } else {
                multipleChoiceQuestionService.save((MultipleChoiceQuestion) question);
            }
        }
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
