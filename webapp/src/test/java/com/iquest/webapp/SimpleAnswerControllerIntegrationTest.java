package com.iquest.webapp;

import com.iquest.model.quiz.answer.AnswerType;
import com.iquest.model.quiz.answer.SimpleAnswer;
import com.iquest.model.user.Client;
import com.iquest.webapp.comparator.SimpleAnswerComparator;
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
public class SimpleAnswerControllerIntegrationTest {

    private static int lastCreatedAnswer = 0;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static SimpleAnswer createSimpleAnswer() {
        SimpleAnswer simpleAnswer = new SimpleAnswer();
        simpleAnswer.setAnswerText("Answer text " + lastCreatedAnswer);
        simpleAnswer.setAnswerType(AnswerType.SIMPLE_ANSWER);
        lastCreatedAnswer++;
        return simpleAnswer;
    }

    @After
    public void tearDown() {
        testRestTemplate.delete("/simpleAnswer/delete/all");
    }

    @Test
    public void testGetAll() throws IOException {
        SimpleAnswer simpleAnswer1 = createSimpleAnswer();
        SimpleAnswer simpleAnswer2 = createSimpleAnswer();
        HttpEntity<SimpleAnswer> simpleAnswerHttpEntity1 = new HttpEntity<>(simpleAnswer1);
        HttpEntity<SimpleAnswer> simpleAnswerHttpEntity2 = new HttpEntity<>(simpleAnswer2);
        simpleAnswer1 = testRestTemplate.postForObject("/simpleAnswer/insert", simpleAnswerHttpEntity1, SimpleAnswer.class);
        simpleAnswer2 = testRestTemplate.postForObject("/simpleAnswer/insert", simpleAnswerHttpEntity2, SimpleAnswer.class);
        List<SimpleAnswer> expectedList = new ArrayList<>();
        expectedList.add(simpleAnswer1);
        expectedList.add(simpleAnswer2);

        ResponseEntity<SimpleAnswer[]> receivedArray = testRestTemplate.getForEntity("/simpleAnswer/get/all", SimpleAnswer[].class);
        List<SimpleAnswer> sortedSimpleAnswers = Arrays.asList(receivedArray.getBody());
        Collections.sort(sortedSimpleAnswers, new SimpleAnswerComparator());
        Assert.assertEquals(expectedList, sortedSimpleAnswers);
    }

    @Test
    public void testGetAllNoSimpleAnswersExist() throws IOException {
        ResponseEntity<SimpleAnswer[]> receivedArray = testRestTemplate.getForEntity("/simpleAnswer/get/all", SimpleAnswer[].class);
        Assert.assertEquals(HttpStatus.NO_CONTENT, receivedArray.getStatusCode().NO_CONTENT);
    }

    @Test
    public void testGetById() {
        SimpleAnswer simpleAnswer = createSimpleAnswer();
        HttpEntity<SimpleAnswer> simpleAnswerHttpEntity = new HttpEntity<>(simpleAnswer);
        simpleAnswer = testRestTemplate.postForObject("/simpleAnswer/insert", simpleAnswerHttpEntity, SimpleAnswer.class);

        SimpleAnswer receivedSimpleAnswer = testRestTemplate.getForObject("/simpleAnswer/get/" + simpleAnswer.getId(), SimpleAnswer.class);
        Assert.assertEquals(simpleAnswer, receivedSimpleAnswer);
    }

    @Test
    public void testGetByIdSimpleAnswerDoesNotExist() {
        ResponseEntity<Client> receivedClient = testRestTemplate.getForEntity("/simpleAnswer/get/1", Client.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, receivedClient.getStatusCode().NOT_FOUND);
    }

    @Test
    public void testInsert() throws IOException {
        SimpleAnswer simpleAnswer = createSimpleAnswer();
        HttpEntity<SimpleAnswer> simpleAnswerHttpEntity = new HttpEntity<>(simpleAnswer);
        simpleAnswer = testRestTemplate.postForObject("/simpleAnswer/insert", simpleAnswerHttpEntity, SimpleAnswer.class);

        SimpleAnswer receivedSimpleAnswer = testRestTemplate.getForObject("/simpleAnswer/get/" + simpleAnswer.getId(), SimpleAnswer.class);
        Assert.assertEquals(simpleAnswer, receivedSimpleAnswer);
    }

    @Test
    public void testDeleteAll() throws IOException {
        SimpleAnswer simpleAnswer = createSimpleAnswer();
        HttpEntity<SimpleAnswer> simpleAnswerHttpEntity = new HttpEntity<>(simpleAnswer);
        simpleAnswer = testRestTemplate.postForObject("/simpleAnswer/insert", simpleAnswerHttpEntity, SimpleAnswer.class);

        SimpleAnswer receivedSimpleAnswer = testRestTemplate.getForObject("/simpleAnswer/get/" + simpleAnswer.getId(), SimpleAnswer.class);
        Assert.assertEquals(simpleAnswer, receivedSimpleAnswer);

        testRestTemplate.delete("/simpleAnswer/delete/all", simpleAnswerHttpEntity);
        Client deletedSimpleAnswer = testRestTemplate.getForObject("/simpleAnswer/get/" + simpleAnswer.getId(), Client.class);
        Assert.assertNull(deletedSimpleAnswer);
    }
}
