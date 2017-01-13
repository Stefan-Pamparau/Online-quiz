package com.iquest.webapp.dto;

import com.iquest.webapp.dto.frommodel.SimpleAnswerDto;
import com.iquest.webapp.dto.frommodel.SimpleQuestionDto;

public class SimpleQuestionAndAnswerDto {

    private SimpleQuestionDto simpleQuestionDto;
    private SimpleAnswerDto simpleAnswerDto;

    public SimpleQuestionDto getSimpleQuestionDto() {
        return simpleQuestionDto;
    }

    public void setSimpleQuestionDto(SimpleQuestionDto simpleQuestionDto) {
        this.simpleQuestionDto = simpleQuestionDto;
    }

    public SimpleAnswerDto getSimpleAnswerDto() {
        return simpleAnswerDto;
    }

    public void setSimpleAnswerDto(SimpleAnswerDto simpleAnswerDto) {
        this.simpleAnswerDto = simpleAnswerDto;
    }
}
