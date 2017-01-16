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
import com.iquest.model.user.Admin;
import com.iquest.model.user.Client;
import com.iquest.model.user.User;
import com.iquest.model.user.UserLobbySession;
import com.iquest.model.user.UserType;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class RestApiTest {

    private static final String SERVER_URL = "http://localhost:8080";
    private static int lastCreatedUser = 1;
    private static int lastCreatedLobby = 1;
    private static int lastCreatedQuestion = 1;
    private static int lastCreatedAnswer = 1;

    private static User authorizedUser;

    private static User createAuthorizedUser() {
        Admin admin = new Admin();
        admin.setFirstName("First name " + lastCreatedUser);
        admin.setSurname("Surname " + lastCreatedUser);
        admin.setAge(25 + lastCreatedUser);
        admin.setEmail("authorized@gmail.com");
        admin.setPassword("password");
        admin.setUserType(UserType.ADMIN);
        lastCreatedUser++;
        return admin;
    }

    private static HttpHeaders getAuthorizingHeaders() {
        String plainCredentials = authorizedUser.getEmail() + ":" + authorizedUser.getPassword();
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

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
        gamefiedQuiz.setTitle("Auto created gamefied quiz");
        gamefiedQuiz.setDescription("General description");
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

    private static SimpleAnswer createSimpleAnswer(SimpleQuestion simpleQuestion) {
        SimpleAnswer simpleAnswer = new SimpleAnswer();
        simpleAnswer.setQuestion(simpleQuestion);
        simpleAnswer.setAnswerText("Answer text " + lastCreatedAnswer);
        simpleAnswer.setAnswerType(AnswerType.SIMPLE_ANSWER);
        return simpleAnswer;
    }

    private static MultipleChoiceAnswer createMultipleChoiceAnswer() {
        MultipleChoiceAnswer multipleChoiceAnswer = new MultipleChoiceAnswer();
        multipleChoiceAnswer.setAnswerType(AnswerType.MULTIPLE_CHOICE_ANSWER);
        multipleChoiceAnswer.setAnswerText("Answer text " + lastCreatedAnswer++);
        return multipleChoiceAnswer;
    }

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        authorizedUser = createAuthorizedUser();
        HttpEntity<User> authorizedUserHttpEntity = new HttpEntity<>(authorizedUser);
        authorizedUser = restTemplate.postForObject(SERVER_URL + "/register", authorizedUserHttpEntity, User.class);
        authorizedUser = restTemplate.postForObject(SERVER_URL + "/user/confirm/" + authorizedUser.getToken(), authorizedUserHttpEntity, User.class);

        Client client = createClient();
        HttpEntity<Client> clientHttpEntity = new HttpEntity<>(client, getAuthorizingHeaders());
        client = restTemplate.postForObject(SERVER_URL + "/register", clientHttpEntity, Client.class);
        client = restTemplate.postForObject(SERVER_URL + "/user/confirm/" + client.getToken(), clientHttpEntity, Client.class);

        GamefiedQuiz gamefiedQuiz = createGamefiedQuiz(client, null);
        HttpEntity<GamefiedQuiz> gamefiedQuizHttpEntity = new HttpEntity<>(gamefiedQuiz, getAuthorizingHeaders());
        gamefiedQuiz = restTemplate.postForObject(SERVER_URL + "gamefiedQuiz/insert", gamefiedQuizHttpEntity, GamefiedQuiz.class);

        SimpleQuestion simpleQuestion = createSimpleQuestion(gamefiedQuiz);
        MultipleChoiceQuestion multipleChoiceQuestion = createMultipleChoiceQuestion(gamefiedQuiz);
        List<Question> questions1 = new ArrayList<>();
        questions1.add(simpleQuestion);
        questions1.add(multipleChoiceQuestion);
        gamefiedQuiz.setQuestions(questions1);
        SimpleAnswer simpleAnswer = new SimpleAnswer();
        simpleQuestion.setAnswer(simpleAnswer);
        HttpEntity<SimpleQuestion> simpleQuestionHttpEntity = new HttpEntity<>(simpleQuestion, getAuthorizingHeaders());
        restTemplate.postForObject(SERVER_URL + "/simpleQuestion/insert", simpleQuestionHttpEntity, SimpleQuestion.class);

        MultipleChoiceAnswer multipleChoiceAnswer1 = createMultipleChoiceAnswer();
        MultipleChoiceAnswer multipleChoiceAnswer2 = createMultipleChoiceAnswer();
        List<MultipleChoiceAnswer> multipleChoiceAnswerList = new ArrayList<>();
        multipleChoiceAnswerList.add(multipleChoiceAnswer1);
        multipleChoiceAnswerList.add(multipleChoiceAnswer2);
        multipleChoiceQuestion.setAnswers(multipleChoiceAnswerList);
        HttpEntity<MultipleChoiceQuestion> multipleChoiceQuestionHttpEntity = new HttpEntity<>(multipleChoiceQuestion, getAuthorizingHeaders());
        multipleChoiceQuestion = restTemplate.postForObject(SERVER_URL + "/multipleChoiceQuestion/insert", multipleChoiceQuestionHttpEntity, MultipleChoiceQuestion.class);

//        MultipleChoiceAnswer multipleChoiceAnswer1 = createMultipleChoiceAnswer();
//        MultipleChoiceAnswer multipleChoiceAnswer2 = createMultipleChoiceAnswer();
//        HttpEntity<MultipleChoiceAnswer> multipleChoiceAnswerHttpEntity1 = new HttpEntity<>(multipleChoiceAnswer1, getAuthorizingHeaders());
//        HttpEntity<MultipleChoiceAnswer> multipleChoiceAnswerHttpEntity2 = new HttpEntity<>(multipleChoiceAnswer2, getAuthorizingHeaders());
//        multipleChoiceAnswer1.setQuestion(multipleChoiceQuestion);
//        multipleChoiceAnswer2.setQuestion(multipleChoiceQuestion);
//        multipleChoiceAnswer1 = restTemplate.postForObject(SERVER_URL + "/multipleChoiceAnswer/insert", multipleChoiceAnswerHttpEntity1, MultipleChoiceAnswer.class);
//        multipleChoiceAnswer2 = restTemplate.postForObject(SERVER_URL + "/multipleChoiceAnswer/insert", multipleChoiceAnswerHttpEntity2, MultipleChoiceAnswer.class);

        Lobby lobby = new Lobby();
        UserLobbySession userLobbySession = new UserLobbySession();
        List<UserLobbySession> userLobbySessionList = new ArrayList<>();
        lobby.setCreationDate(new Date(System.currentTimeMillis()));
        lobby.setQuiz(gamefiedQuiz);
        lobby.setUsers(userLobbySessionList);
        userLobbySession.setLobby(lobby);
        userLobbySession.setUser(client);
        userLobbySessionList.add(userLobbySession);
        HttpEntity<Lobby> lobbyHttpEntity = new HttpEntity<>(lobby, getAuthorizingHeaders());
        lobby = restTemplate.postForObject(SERVER_URL + "/lobby/insert", lobbyHttpEntity, Lobby.class);
    }
}
