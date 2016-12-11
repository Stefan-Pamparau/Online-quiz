package com.iquest.webapp;

import com.iquest.model.quiz.GamefiedQuiz;
import com.iquest.model.quiz.QuizType;
import com.iquest.model.user.Client;
import com.iquest.webapp.comparator.GamefiedQuizComparator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
public class GamefiedQuizControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static GamefiedQuiz createGamefiedQuiz() {
        GamefiedQuiz gamefiedQuiz = new GamefiedQuiz();
        gamefiedQuiz.setQuizType(QuizType.GAMEFIED_QUIZ);
        return gamefiedQuiz;
    }

    @After
    public void tearDown() {
        testRestTemplate.delete("/gamefiedQuiz/delete/all");
    }

    @Test
    public void testGetAll() throws IOException {
        GamefiedQuiz gamefiedQuiz1 = createGamefiedQuiz();
        GamefiedQuiz gamefiedQuiz2 = createGamefiedQuiz();
        HttpEntity<GamefiedQuiz> gamefiedQuizHttpEntity1 = new HttpEntity<>(gamefiedQuiz1);
        HttpEntity<GamefiedQuiz> gamefiedQuizHttpEntity2 = new HttpEntity<>(gamefiedQuiz2);
        gamefiedQuiz1 = testRestTemplate.postForObject("/gamefiedQuiz/insert", gamefiedQuizHttpEntity1, GamefiedQuiz.class);
        gamefiedQuiz2 = testRestTemplate.postForObject("/gamefiedQuiz/insert", gamefiedQuizHttpEntity2, GamefiedQuiz.class);
        List<GamefiedQuiz> expectedList = new ArrayList<>();
        expectedList.add(gamefiedQuiz1);
        expectedList.add(gamefiedQuiz2);

        ResponseEntity<GamefiedQuiz[]> receivedArray = testRestTemplate.getForEntity("/gamefiedQuiz/get/all", GamefiedQuiz[].class);
        List<GamefiedQuiz> sortedGamefiedQuizs = Arrays.asList(receivedArray.getBody());
        Collections.sort(sortedGamefiedQuizs, new GamefiedQuizComparator());
        Assert.assertEquals(expectedList, sortedGamefiedQuizs);
    }

    @Test
    public void testGetAllNoGamefiedQuizsExist() throws IOException {
        ResponseEntity<GamefiedQuiz[]> receivedArray = testRestTemplate.getForEntity("/gamefiedQuiz/get/all", GamefiedQuiz[].class);
        Assert.assertEquals(HttpStatus.NO_CONTENT, receivedArray.getStatusCode().NO_CONTENT);
    }

    @Test
    public void testGetById() {
        GamefiedQuiz gamefiedQuiz = createGamefiedQuiz();
        HttpEntity<GamefiedQuiz> gamefiedQuizHttpEntity = new HttpEntity<>(gamefiedQuiz);
        gamefiedQuiz = testRestTemplate.postForObject("/gamefiedQuiz/insert", gamefiedQuizHttpEntity, GamefiedQuiz.class);

        GamefiedQuiz receivedGamefiedQuiz = testRestTemplate.getForObject("/gamefiedQuiz/get/" + gamefiedQuiz.getId(), GamefiedQuiz.class);
        Assert.assertEquals(gamefiedQuiz, receivedGamefiedQuiz);
    }

    @Test
    public void testGetByIdGamefiedQuizDoesNotExist() {
        ResponseEntity<Client> receivedClient = testRestTemplate.getForEntity("/gamefiedQuiz/get/1", Client.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, receivedClient.getStatusCode().NOT_FOUND);
    }

    @Test
    public void testInsert() throws IOException {
        GamefiedQuiz gamefiedQuiz = createGamefiedQuiz();
        HttpEntity<GamefiedQuiz> gamefiedQuizHttpEntity = new HttpEntity<>(gamefiedQuiz);
        gamefiedQuiz = testRestTemplate.postForObject("/gamefiedQuiz/insert", gamefiedQuizHttpEntity, GamefiedQuiz.class);

        GamefiedQuiz receivedGamefiedQuiz = testRestTemplate.getForObject("/gamefiedQuiz/get/" + gamefiedQuiz.getId(), GamefiedQuiz.class);
        Assert.assertEquals(gamefiedQuiz, receivedGamefiedQuiz);
    }

    @Test
    public void testDeleteAll() throws IOException {
        GamefiedQuiz gamefiedQuiz = createGamefiedQuiz();
        HttpEntity<GamefiedQuiz> gamefiedQuizHttpEntity = new HttpEntity<>(gamefiedQuiz);
        gamefiedQuiz = testRestTemplate.postForObject("/gamefiedQuiz/insert", gamefiedQuizHttpEntity, GamefiedQuiz.class);

        GamefiedQuiz receivedGamefiedQuiz = testRestTemplate.getForObject("/gamefiedQuiz/get/" + gamefiedQuiz.getId(), GamefiedQuiz.class);
        Assert.assertEquals(gamefiedQuiz, receivedGamefiedQuiz);

        testRestTemplate.delete("/gamefiedQuiz/delete/all", gamefiedQuizHttpEntity);
        Client deletedGamefiedQuiz = testRestTemplate.getForObject("/gamefiedQuiz/get/" + gamefiedQuiz.getId(), Client.class);
        Assert.assertNull(deletedGamefiedQuiz);
    }
}
