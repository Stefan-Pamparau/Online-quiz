package com.iquest.webapp;

import com.iquest.model.Lobby;
import com.iquest.model.quiz.GamefiedQuiz;
import com.iquest.model.quiz.Quiz;
import com.iquest.model.quiz.QuizType;
import com.iquest.model.quiz.answer.SimpleAnswer;
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

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        Client client = createClient();
        HttpEntity<Client> clientHttpEntity = new HttpEntity<>(client);
        client = restTemplate.postForObject(SERVER_URL + "/client/insert", clientHttpEntity, Client.class);

        GamefiedQuiz gamefiedQuiz = createGamefiedQuiz(client, null);
        HttpEntity<GamefiedQuiz> gamefiedQuizHttpEntity = new HttpEntity<>(gamefiedQuiz);
        gamefiedQuiz = restTemplate.postForObject(SERVER_URL + "gamefiedQuiz/insert", gamefiedQuizHttpEntity, GamefiedQuiz.class);

        SimpleQuestion simpleQuestion = createSimpleQuestion(gamefiedQuiz);
        List<Question> questions1 = new ArrayList<>();
        questions1.add(simpleQuestion);
        gamefiedQuiz.setQuestions(questions1);
        HttpEntity<Question> simpleQuestionHttpEntity = new HttpEntity<>(simpleQuestion);

        restTemplate.postForObject(SERVER_URL + "/simpleQuestion/insert", simpleQuestionHttpEntity, SimpleQuestion.class);
    }
}
