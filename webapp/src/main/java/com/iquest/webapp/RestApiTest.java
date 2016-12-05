package com.iquest.webapp;

import com.iquest.model.Lobby;
import com.iquest.model.quiz.GamefiedQuiz;
import com.iquest.model.quiz.Quiz;
import com.iquest.model.quiz.QuizType;
import com.iquest.model.quiz.answer.AnswerType;
import com.iquest.model.quiz.answer.MultipleChoiceAnswer;
import com.iquest.model.quiz.answer.SimpleAnswer;
import com.iquest.model.quiz.question.MultipleChoiceQuestion;
import com.iquest.model.quiz.question.Question;
import com.iquest.model.quiz.question.QuestionType;
import com.iquest.model.quiz.question.SimpleQuestion;
import com.iquest.model.user.Client;
import com.iquest.model.user.UserType;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class RestApiTest {

    private static final String SERVER_URL = "http://localhost:8080";
    private static int lastCreatedUser = 1;
    private static int lastCreatedLobby = 1;
    private static int lastCreatedQuestion = 1;
    private static int lastCreatedAnswer = 1;

    private static Client createClient() {
        Client client = new Client();
        client.setFirstName("First name " + lastCreatedUser);
        client.setSurname("Surname " + lastCreatedUser);
        client.setAge(25 + lastCreatedUser);
        client.setEmail("example@gmail.com");
        client.setPassword("password");
        client.setUserType(UserType.CLIENT);
        lastCreatedUser++;
        return client;
    }

    private static Lobby createLobby() {
        Lobby lobby = new Lobby();

        return lobby;
    }

    private static GamefiedQuiz createGamefiedQuiz(Client client, List<Question> questions) {
        GamefiedQuiz gamefiedQuiz = new GamefiedQuiz();
        gamefiedQuiz.setClient(client);
        gamefiedQuiz.setQuestions(questions);
        gamefiedQuiz.setQuizType(QuizType.GAMEFIED_QUIZ);
        return gamefiedQuiz;
    }

    private static SimpleQuestion createSimpleQuestion(Quiz quiz) {
        SimpleAnswer simpleAnswer = new SimpleAnswer();
        simpleAnswer.setAnswerText("Answer text " + lastCreatedAnswer);
        simpleAnswer.setAnswerType(AnswerType.SIMPLE_ANSWER);
        SimpleQuestion simpleQuestion = new SimpleQuestion();
        simpleAnswer.setQuestion(simpleQuestion);
        simpleQuestion.setQuestionText("Question text " + lastCreatedQuestion);
        simpleQuestion.setAnswer(simpleAnswer);
        simpleQuestion.setQuiz(quiz);
        simpleQuestion.setQuestionType(QuestionType.SIMPLE_QUESTION);
        lastCreatedQuestion++;
        lastCreatedAnswer++;
        return simpleQuestion;
    }

    private static MultipleChoiceQuestion createMultipleChoiceQuestion(Quiz quiz) {
        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion();
        multipleChoiceQuestion.setQuestionText("Question text " + lastCreatedQuestion);
        multipleChoiceQuestion.setQuiz(quiz);
        multipleChoiceQuestion.setQuestionType(QuestionType.MULTIPLE_CHOICE_QUESTION);
        return multipleChoiceQuestion;
    }

    private static MultipleChoiceAnswer createMultipleChoiceAnswer() {
        MultipleChoiceAnswer multipleChoiceAnswer = new MultipleChoiceAnswer();
        multipleChoiceAnswer.setAnswerType(AnswerType.MULTIPLE_CHOICE_ANSWER);
        multipleChoiceAnswer.setAnswerText("Answer text " + lastCreatedAnswer++);
        return multipleChoiceAnswer;
    }

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        Client client = createClient();
        HttpEntity<Client> clientHttpEntity = new HttpEntity<>(client);
        client = restTemplate.postForObject(SERVER_URL + "/client/insert", clientHttpEntity, Client.class);

        GamefiedQuiz gamefiedQuiz = createGamefiedQuiz(client, null);
        HttpEntity<GamefiedQuiz> gamefiedQuizHttpEntity = new HttpEntity<>(gamefiedQuiz);
        gamefiedQuiz = restTemplate.postForObject(SERVER_URL + "gamefiedQuiz/insert", gamefiedQuizHttpEntity, GamefiedQuiz.class);

        SimpleQuestion simpleQuestion = createSimpleQuestion(gamefiedQuiz);
        MultipleChoiceQuestion multipleChoiceQuestion = createMultipleChoiceQuestion(gamefiedQuiz);
        List<Question> questions1 = new ArrayList<>();
        questions1.add(simpleQuestion);
        questions1.add(multipleChoiceQuestion);
        gamefiedQuiz.setQuestions(questions1);
        HttpEntity<SimpleQuestion> simpleQuestionHttpEntity = new HttpEntity<>(simpleQuestion);
        restTemplate.postForObject(SERVER_URL + "/simpleQuestion/insert", simpleQuestionHttpEntity, SimpleQuestion.class);

        HttpEntity<MultipleChoiceQuestion> multipleChoiceQuestionHttpEntity = new HttpEntity<>(multipleChoiceQuestion);
        multipleChoiceQuestion = restTemplate.postForObject(SERVER_URL + "/multipleChoiceQuestion/insert", multipleChoiceQuestionHttpEntity, MultipleChoiceQuestion.class);

        MultipleChoiceAnswer multipleChoiceAnswer1 = createMultipleChoiceAnswer();
        MultipleChoiceAnswer multipleChoiceAnswer2 = createMultipleChoiceAnswer();
        HttpEntity<MultipleChoiceAnswer> multipleChoiceAnswerHttpEntity1 = new HttpEntity<>(multipleChoiceAnswer1);
        HttpEntity<MultipleChoiceAnswer> multipleChoiceAnswerHttpEntity2 = new HttpEntity<>(multipleChoiceAnswer2);
        multipleChoiceAnswer1.setQuestion(multipleChoiceQuestion);
        multipleChoiceAnswer2.setQuestion(multipleChoiceQuestion);
        multipleChoiceAnswer1 =  restTemplate.postForObject(SERVER_URL + "/multipleChoiceAnswer/insert", multipleChoiceAnswerHttpEntity1, MultipleChoiceAnswer.class);
        multipleChoiceAnswer2 =  restTemplate.postForObject(SERVER_URL + "/multipleChoiceAnswer/insert", multipleChoiceAnswerHttpEntity2, MultipleChoiceAnswer.class);

        List<MultipleChoiceAnswer> answers = new ArrayList<>();
        answers.add(multipleChoiceAnswer1);
        answers.add(multipleChoiceAnswer2);
        restTemplate.postForObject(SERVER_URL + "/multipleChoiceQuestion/insert", multipleChoiceQuestionHttpEntity, MultipleChoiceQuestion.class);
    }
}
