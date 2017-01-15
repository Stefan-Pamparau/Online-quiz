package com.iquest.webapp.dto;

import com.iquest.webapp.dto.frommodel.SimpleQuestionDto;

public class ClientSimpleAnswerDto {

    private String answerText;
    private SimpleQuestionDto simpleQuestionDto;

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public SimpleQuestionDto getSimpleQuestionDto() {
        return simpleQuestionDto;
    }

    public void setSimpleQuestionDto(SimpleQuestionDto simpleQuestionDto) {
        this.simpleQuestionDto = simpleQuestionDto;
    }
}
