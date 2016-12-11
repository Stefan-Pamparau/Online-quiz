package com.iquest.webapp;

import com.iquest.model.quiz.ExamQuiz;
import com.iquest.model.quiz.QuizType;
import com.iquest.model.user.Client;
import com.iquest.webapp.comparator.ExamQuizComparator;
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
public class ExamQuizControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static ExamQuiz createExamQuiz() {
        ExamQuiz examQuiz = new ExamQuiz();
        examQuiz.setQuizType(QuizType.EXAM_QUIZ);
        return examQuiz;
    }

    @After
    public void tearDown() {
        testRestTemplate.delete("/examQuiz/delete/all");
    }

    @Test
    public void testGetAll() throws IOException {
        ExamQuiz examQuiz1 = createExamQuiz();
        ExamQuiz examQuiz2 = createExamQuiz();
        HttpEntity<ExamQuiz> examQuizHttpEntity1 = new HttpEntity<>(examQuiz1);
        HttpEntity<ExamQuiz> examQuizHttpEntity2 = new HttpEntity<>(examQuiz2);
        examQuiz1 = testRestTemplate.postForObject("/examQuiz/insert", examQuizHttpEntity1, ExamQuiz.class);
        examQuiz2 = testRestTemplate.postForObject("/examQuiz/insert", examQuizHttpEntity2, ExamQuiz.class);
        List<ExamQuiz> expectedList = new ArrayList<>();
        expectedList.add(examQuiz1);
        expectedList.add(examQuiz2);

        ResponseEntity<ExamQuiz[]> receivedArray = testRestTemplate.getForEntity("/examQuiz/get/all", ExamQuiz[].class);
        List<ExamQuiz> sortedExamQuizs = Arrays.asList(receivedArray.getBody());
        Collections.sort(sortedExamQuizs, new ExamQuizComparator());
        Assert.assertEquals(expectedList, sortedExamQuizs);
    }

    @Test
    public void testGetAllNoExamQuizsExist() throws IOException {
        ResponseEntity<ExamQuiz[]> receivedArray = testRestTemplate.getForEntity("/examQuiz/get/all", ExamQuiz[].class);
        Assert.assertEquals(HttpStatus.NO_CONTENT, receivedArray.getStatusCode().NO_CONTENT);
    }

    @Test
    public void testGetById() {
        ExamQuiz examQuiz = createExamQuiz();
        HttpEntity<ExamQuiz> examQuizHttpEntity = new HttpEntity<>(examQuiz);
        examQuiz = testRestTemplate.postForObject("/examQuiz/insert", examQuizHttpEntity, ExamQuiz.class);

        ExamQuiz receivedExamQuiz = testRestTemplate.getForObject("/examQuiz/get/" + examQuiz.getId(), ExamQuiz.class);
        Assert.assertEquals(examQuiz, receivedExamQuiz);
    }

    @Test
    public void testGetByIdExamQuizDoesNotExist() {
        ResponseEntity<Client> receivedClient = testRestTemplate.getForEntity("/examQuiz/get/1", Client.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, receivedClient.getStatusCode().NOT_FOUND);
    }

    @Test
    public void testInsert() throws IOException {
        ExamQuiz examQuiz = createExamQuiz();
        HttpEntity<ExamQuiz> examQuizHttpEntity = new HttpEntity<>(examQuiz);
        examQuiz = testRestTemplate.postForObject("/examQuiz/insert", examQuizHttpEntity, ExamQuiz.class);

        ExamQuiz receivedExamQuiz = testRestTemplate.getForObject("/examQuiz/get/" + examQuiz.getId(), ExamQuiz.class);
        Assert.assertEquals(examQuiz, receivedExamQuiz);
    }

    @Test
    public void testDeleteAll() throws IOException {
        ExamQuiz examQuiz = createExamQuiz();
        HttpEntity<ExamQuiz> examQuizHttpEntity = new HttpEntity<>(examQuiz);
        examQuiz = testRestTemplate.postForObject("/examQuiz/insert", examQuizHttpEntity, ExamQuiz.class);

        ExamQuiz receivedExamQuiz = testRestTemplate.getForObject("/examQuiz/get/" + examQuiz.getId(), ExamQuiz.class);
        Assert.assertEquals(examQuiz, receivedExamQuiz);

        testRestTemplate.delete("/examQuiz/delete/all", examQuizHttpEntity);
        Client deletedExamQuiz = testRestTemplate.getForObject("/examQuiz/get/" + examQuiz.getId(), Client.class);
        Assert.assertNull(deletedExamQuiz);
    }
}
