package com.iquest.webapp.dto;

import com.iquest.webapp.dto.frommodel.MultipleChoiceAnswerDto;
import com.iquest.webapp.dto.frommodel.MultipleChoiceQuestionDto;

public class MultipleChoiceQuestionAndAnswerDto {

    private MultipleChoiceQuestionDto multipleChoiceQuestionDto;
    private MultipleChoiceAnswerDto multipleChoiceAnswerDto;

    public MultipleChoiceQuestionDto getMultipleChoiceQuestionDto() {
        return multipleChoiceQuestionDto;
    }

    public void setMultipleChoiceQuestionDto(MultipleChoiceQuestionDto multipleChoiceQuestionDto) {
        this.multipleChoiceQuestionDto = multipleChoiceQuestionDto;
    }

    public MultipleChoiceAnswerDto getMultipleChoiceAnswerDto() {
        return multipleChoiceAnswerDto;
    }

    public void setMultipleChoiceAnswerDto(MultipleChoiceAnswerDto multipleChoiceAnswerDto) {
        this.multipleChoiceAnswerDto = multipleChoiceAnswerDto;
    }
}
