package com.iquest.webapp;

import com.iquest.model.quiz.question.SimpleQuestion;
import com.iquest.model.quiz.question.QuestionType;
import com.iquest.model.user.Client;
import com.iquest.webapp.comparator.SimpleQuestionComparator;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimpleQuestionControllerIntegrationTest {

    private static int lastCreatedQuestion = 0;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static SimpleQuestion createSimpleQuestion() {
        SimpleQuestion simpleQuestion = new SimpleQuestion();
        simpleQuestion.setQuestionType(QuestionType.SIMPLE_QUESTION);
        simpleQuestion.setQuestionText("Question text " + lastCreatedQuestion);
        lastCreatedQuestion++;
        return simpleQuestion;
    }

    @After
    public void tearDown() {
        testRestTemplate.delete("/simpleQuestion/delete/all");
    }

    @Test
    public void testGetAll() throws IOException {
        SimpleQuestion simpleQuestion1 = createSimpleQuestion();
        SimpleQuestion simpleQuestion2 = createSimpleQuestion();
        HttpEntity<SimpleQuestion> simpleQuestionHttpEntity1 = new HttpEntity<>(simpleQuestion1);
        HttpEntity<SimpleQuestion> simpleQuestionHttpEntity2 = new HttpEntity<>(simpleQuestion2);
        simpleQuestion1 = testRestTemplate.postForObject("/simpleQuestion/insert", simpleQuestionHttpEntity1, SimpleQuestion.class);
        simpleQuestion2 = testRestTemplate.postForObject("/simpleQuestion/insert", simpleQuestionHttpEntity2, SimpleQuestion.class);
        List<SimpleQuestion> expectedList = new ArrayList<>();
        expectedList.add(simpleQuestion1);
        expectedList.add(simpleQuestion2);

        ResponseEntity<SimpleQuestion[]> receivedArray = testRestTemplate.getForEntity("/simpleQuestion/get/all", SimpleQuestion[].class);
        List<SimpleQuestion> sortedSimpleQuestions = Arrays.asList(receivedArray.getBody());
        Collections.sort(sortedSimpleQuestions, new SimpleQuestionComparator());
        Assert.assertEquals(expectedList, sortedSimpleQuestions);
    }

    @Test
    public void testGetAllNoSimpleQuestionsExist() throws IOException {
        ResponseEntity<SimpleQuestion[]> receivedArray = testRestTemplate.getForEntity("/simpleQuestion/get/all", SimpleQuestion[].class);
        Assert.assertEquals(HttpStatus.NO_CONTENT, receivedArray.getStatusCode().NO_CONTENT);
    }

    @Test
    public void testGetById() {
        SimpleQuestion simpleQuestion = createSimpleQuestion();
        HttpEntity<SimpleQuestion> simpleQuestionHttpEntity = new HttpEntity<>(simpleQuestion);
        simpleQuestion = testRestTemplate.postForObject("/simpleQuestion/insert", simpleQuestionHttpEntity, SimpleQuestion.class);

        SimpleQuestion receivedSimpleQuestion = testRestTemplate.getForObject("/simpleQuestion/get/" + simpleQuestion.getId(), SimpleQuestion.class);
        Assert.assertEquals(simpleQuestion, receivedSimpleQuestion);
    }

    @Test
    public void testGetByIdSimpleQuestionDoesNotExist() {
        ResponseEntity<Client> receivedClient = testRestTemplate.getForEntity("/simpleQuestion/get/1", Client.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, receivedClient.getStatusCode().NOT_FOUND);
    }

    @Test
    public void testInsert() throws IOException {
        SimpleQuestion simpleQuestion = createSimpleQuestion();
        HttpEntity<SimpleQuestion> simpleQuestionHttpEntity = new HttpEntity<>(simpleQuestion);
        simpleQuestion = testRestTemplate.postForObject("/simpleQuestion/insert", simpleQuestionHttpEntity, SimpleQuestion.class);

        SimpleQuestion receivedSimpleQuestion = testRestTemplate.getForObject("/simpleQuestion/get/" + simpleQuestion.getId(), SimpleQuestion.class);
        Assert.assertEquals(simpleQuestion, receivedSimpleQuestion);
    }

    @Test
    public void testDeleteAll() throws IOException {
        SimpleQuestion simpleQuestion = createSimpleQuestion();
        HttpEntity<SimpleQuestion> simpleQuestionHttpEntity = new HttpEntity<>(simpleQuestion);
        simpleQuestion = testRestTemplate.postForObject("/simpleQuestion/insert", simpleQuestionHttpEntity, SimpleQuestion.class);

        SimpleQuestion receivedSimpleQuestion = testRestTemplate.getForObject("/simpleQuestion/get/" + simpleQuestion.getId(), SimpleQuestion.class);
        Assert.assertEquals(simpleQuestion, receivedSimpleQuestion);

        testRestTemplate.delete("/simpleQuestion/delete/all", simpleQuestionHttpEntity);
        Client deletedSimpleQuestion = testRestTemplate.getForObject("/simpleQuestion/get/" + simpleQuestion.getId(), Client.class);
        Assert.assertNull(deletedSimpleQuestion);
    }
}
