package com.iquest.webapp;

import com.iquest.model.quiz.answer.AnswerType;
import com.iquest.model.quiz.answer.MultipleChoiceAnswer;
import com.iquest.model.quiz.question.QuestionType;
import com.iquest.model.user.Client;
import com.iquest.webapp.comparator.MultipleChoiceAnswerComparator;
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
public class MultipleChoiceAnswerControllerIntegrationTest {

    private static int lastCreatedAnswer = 0;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static MultipleChoiceAnswer createMultipleChoiceAnswer() {
        MultipleChoiceAnswer multipleChoiceAnswer = new MultipleChoiceAnswer();
        multipleChoiceAnswer.setCorrect(true);
        multipleChoiceAnswer.setAnswerText("Answer text " + lastCreatedAnswer);
        multipleChoiceAnswer.setAnswerType(AnswerType.MULTIPLE_CHOICE_ANSWER);
        lastCreatedAnswer++;
        return multipleChoiceAnswer;
    }

    @After
    public void tearDown() {
        testRestTemplate.delete("/multipleChoiceAnswer/delete/all");
    }

    @Test
    public void testGetAll() throws IOException {
        MultipleChoiceAnswer multipleChoiceAnswer1 = createMultipleChoiceAnswer();
        MultipleChoiceAnswer multipleChoiceAnswer2 = createMultipleChoiceAnswer();
        HttpEntity<MultipleChoiceAnswer> multipleChoiceAnswerHttpEntity1 = new HttpEntity<>(multipleChoiceAnswer1);
        HttpEntity<MultipleChoiceAnswer> multipleChoiceAnswerHttpEntity2 = new HttpEntity<>(multipleChoiceAnswer2);
        multipleChoiceAnswer1 = testRestTemplate.postForObject("/multipleChoiceAnswer/insert", multipleChoiceAnswerHttpEntity1, MultipleChoiceAnswer.class);
        multipleChoiceAnswer2 = testRestTemplate.postForObject("/multipleChoiceAnswer/insert", multipleChoiceAnswerHttpEntity2, MultipleChoiceAnswer.class);
        List<MultipleChoiceAnswer> expectedList = new ArrayList<>();
        expectedList.add(multipleChoiceAnswer1);
        expectedList.add(multipleChoiceAnswer2);

        ResponseEntity<MultipleChoiceAnswer[]> receivedArray = testRestTemplate.getForEntity("/multipleChoiceAnswer/get/all", MultipleChoiceAnswer[].class);
        List<MultipleChoiceAnswer> sortedMultipleChoiceAnswers = Arrays.asList(receivedArray.getBody());
        Collections.sort(sortedMultipleChoiceAnswers, new MultipleChoiceAnswerComparator());
        Assert.assertEquals(expectedList, sortedMultipleChoiceAnswers);
    }

    @Test
    public void testGetAllNoMultipleChoiceAnswersExist() throws IOException {
        ResponseEntity<MultipleChoiceAnswer[]> receivedArray = testRestTemplate.getForEntity("/multipleChoiceAnswer/get/all", MultipleChoiceAnswer[].class);
        Assert.assertEquals(HttpStatus.NO_CONTENT, receivedArray.getStatusCode().NO_CONTENT);
    }

    @Test
    public void testGetById() {
        MultipleChoiceAnswer multipleChoiceAnswer = createMultipleChoiceAnswer();
        HttpEntity<MultipleChoiceAnswer> multipleChoiceAnswerHttpEntity = new HttpEntity<>(multipleChoiceAnswer);
        multipleChoiceAnswer = testRestTemplate.postForObject("/multipleChoiceAnswer/insert", multipleChoiceAnswerHttpEntity, MultipleChoiceAnswer.class);

        MultipleChoiceAnswer receivedMultipleChoiceAnswer = testRestTemplate.getForObject("/multipleChoiceAnswer/get/" + multipleChoiceAnswer.getId(), MultipleChoiceAnswer.class);
        Assert.assertEquals(multipleChoiceAnswer, receivedMultipleChoiceAnswer);
    }

    @Test
    public void testGetByIdMultipleChoiceAnswerDoesNotExist() {
        ResponseEntity<Client> receivedClient = testRestTemplate.getForEntity("/multipleChoiceAnswer/get/1", Client.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, receivedClient.getStatusCode().NOT_FOUND);
    }

    @Test
    public void testInsert() throws IOException {
        MultipleChoiceAnswer multipleChoiceAnswer = createMultipleChoiceAnswer();
        HttpEntity<MultipleChoiceAnswer> multipleChoiceAnswerHttpEntity = new HttpEntity<>(multipleChoiceAnswer);
        multipleChoiceAnswer = testRestTemplate.postForObject("/multipleChoiceAnswer/insert", multipleChoiceAnswerHttpEntity, MultipleChoiceAnswer.class);

        MultipleChoiceAnswer receivedMultipleChoiceAnswer = testRestTemplate.getForObject("/multipleChoiceAnswer/get/" + multipleChoiceAnswer.getId(), MultipleChoiceAnswer.class);
        Assert.assertEquals(multipleChoiceAnswer, receivedMultipleChoiceAnswer);
    }

    @Test
    public void testDeleteAll() throws IOException {
        MultipleChoiceAnswer multipleChoiceAnswer = createMultipleChoiceAnswer();
        HttpEntity<MultipleChoiceAnswer> multipleChoiceAnswerHttpEntity = new HttpEntity<>(multipleChoiceAnswer);
        multipleChoiceAnswer = testRestTemplate.postForObject("/multipleChoiceAnswer/insert", multipleChoiceAnswerHttpEntity, MultipleChoiceAnswer.class);

        MultipleChoiceAnswer receivedMultipleChoiceAnswer = testRestTemplate.getForObject("/multipleChoiceAnswer/get/" + multipleChoiceAnswer.getId(), MultipleChoiceAnswer.class);
        Assert.assertEquals(multipleChoiceAnswer, receivedMultipleChoiceAnswer);

        testRestTemplate.delete("/multipleChoiceAnswer/delete/all", multipleChoiceAnswerHttpEntity);
        Client deletedMultipleChoiceAnswer = testRestTemplate.getForObject("/multipleChoiceAnswer/get/" + multipleChoiceAnswer.getId(), Client.class);
        Assert.assertNull(deletedMultipleChoiceAnswer);
    }
}
