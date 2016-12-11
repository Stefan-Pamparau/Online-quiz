package com.iquest.webapp;

import com.iquest.model.quiz.QuizType;
import com.iquest.model.quiz.question.MultipleChoiceQuestion;
import com.iquest.model.quiz.question.QuestionType;
import com.iquest.model.user.Client;
import com.iquest.webapp.comparator.MultipleChoiceQuestionComparator;
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
public class MultipleChoiceQuestionControllerIntegrationTest {

    private static int lastCreatedQuestion = 0;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static MultipleChoiceQuestion createMultipleChoiceQuestion() {
        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion();
        multipleChoiceQuestion.setQuestionType(QuestionType.MULTIPLE_CHOICE_QUESTION);
        multipleChoiceQuestion.setQuestionText("Question text " + lastCreatedQuestion);
        lastCreatedQuestion++;
        return multipleChoiceQuestion;
    }

    @After
    public void tearDown() {
        testRestTemplate.delete("/multipleChoiceQuestion/delete/all");
    }

    @Test
    public void testGetAll() throws IOException {
        MultipleChoiceQuestion multipleChoiceQuestion1 = createMultipleChoiceQuestion();
        MultipleChoiceQuestion multipleChoiceQuestion2 = createMultipleChoiceQuestion();
        HttpEntity<MultipleChoiceQuestion> multipleChoiceQuestionHttpEntity1 = new HttpEntity<>(multipleChoiceQuestion1);
        HttpEntity<MultipleChoiceQuestion> multipleChoiceQuestionHttpEntity2 = new HttpEntity<>(multipleChoiceQuestion2);
        multipleChoiceQuestion1 = testRestTemplate.postForObject("/multipleChoiceQuestion/insert", multipleChoiceQuestionHttpEntity1, MultipleChoiceQuestion.class);
        multipleChoiceQuestion2 = testRestTemplate.postForObject("/multipleChoiceQuestion/insert", multipleChoiceQuestionHttpEntity2, MultipleChoiceQuestion.class);
        List<MultipleChoiceQuestion> expectedList = new ArrayList<>();
        expectedList.add(multipleChoiceQuestion1);
        expectedList.add(multipleChoiceQuestion2);

        ResponseEntity<MultipleChoiceQuestion[]> receivedArray = testRestTemplate.getForEntity("/multipleChoiceQuestion/get/all", MultipleChoiceQuestion[].class);
        List<MultipleChoiceQuestion> sortedMultipleChoiceQuestions = Arrays.asList(receivedArray.getBody());
        Collections.sort(sortedMultipleChoiceQuestions, new MultipleChoiceQuestionComparator());
        Assert.assertEquals(expectedList, sortedMultipleChoiceQuestions);
    }

    @Test
    public void testGetAllNoMultipleChoiceQuestionsExist() throws IOException {
        ResponseEntity<MultipleChoiceQuestion[]> receivedArray = testRestTemplate.getForEntity("/multipleChoiceQuestion/get/all", MultipleChoiceQuestion[].class);
        Assert.assertEquals(HttpStatus.NO_CONTENT, receivedArray.getStatusCode().NO_CONTENT);
    }

    @Test
    public void testGetById() {
        MultipleChoiceQuestion multipleChoiceQuestion = createMultipleChoiceQuestion();
        HttpEntity<MultipleChoiceQuestion> multipleChoiceQuestionHttpEntity = new HttpEntity<>(multipleChoiceQuestion);
        multipleChoiceQuestion = testRestTemplate.postForObject("/multipleChoiceQuestion/insert", multipleChoiceQuestionHttpEntity, MultipleChoiceQuestion.class);

        MultipleChoiceQuestion receivedMultipleChoiceQuestion = testRestTemplate.getForObject("/multipleChoiceQuestion/get/" + multipleChoiceQuestion.getId(), MultipleChoiceQuestion.class);
        Assert.assertEquals(multipleChoiceQuestion, receivedMultipleChoiceQuestion);
    }

    @Test
    public void testGetByIdMultipleChoiceQuestionDoesNotExist() {
        ResponseEntity<Client> receivedClient = testRestTemplate.getForEntity("/multipleChoiceQuestion/get/1", Client.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, receivedClient.getStatusCode().NOT_FOUND);
    }

    @Test
    public void testInsert() throws IOException {
        MultipleChoiceQuestion multipleChoiceQuestion = createMultipleChoiceQuestion();
        HttpEntity<MultipleChoiceQuestion> multipleChoiceQuestionHttpEntity = new HttpEntity<>(multipleChoiceQuestion);
        multipleChoiceQuestion = testRestTemplate.postForObject("/multipleChoiceQuestion/insert", multipleChoiceQuestionHttpEntity, MultipleChoiceQuestion.class);

        MultipleChoiceQuestion receivedMultipleChoiceQuestion = testRestTemplate.getForObject("/multipleChoiceQuestion/get/" + multipleChoiceQuestion.getId(), MultipleChoiceQuestion.class);
        Assert.assertEquals(multipleChoiceQuestion, receivedMultipleChoiceQuestion);
    }

    @Test
    public void testDeleteAll() throws IOException {
        MultipleChoiceQuestion multipleChoiceQuestion = createMultipleChoiceQuestion();
        HttpEntity<MultipleChoiceQuestion> multipleChoiceQuestionHttpEntity = new HttpEntity<>(multipleChoiceQuestion);
        multipleChoiceQuestion = testRestTemplate.postForObject("/multipleChoiceQuestion/insert", multipleChoiceQuestionHttpEntity, MultipleChoiceQuestion.class);

        MultipleChoiceQuestion receivedMultipleChoiceQuestion = testRestTemplate.getForObject("/multipleChoiceQuestion/get/" + multipleChoiceQuestion.getId(), MultipleChoiceQuestion.class);
        Assert.assertEquals(multipleChoiceQuestion, receivedMultipleChoiceQuestion);

        testRestTemplate.delete("/multipleChoiceQuestion/delete/all", multipleChoiceQuestionHttpEntity);
        Client deletedMultipleChoiceQuestion = testRestTemplate.getForObject("/multipleChoiceQuestion/get/" + multipleChoiceQuestion.getId(), Client.class);
        Assert.assertNull(deletedMultipleChoiceQuestion);
    }
}
