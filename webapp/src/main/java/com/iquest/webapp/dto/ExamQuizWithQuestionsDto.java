package com.iquest.webapp.dto;

import com.iquest.webapp.dto.frommodel.ExamQuizDto;
import com.iquest.webapp.dto.frommodel.SimpleQuestionDto;

import java.util.List;

public class ExamQuizWithQuestionsDto {

    private ExamQuizDto examQuizDto;
    private List<SimpleQuestionDto> simpleQuestionDtos;

    public ExamQuizDto getExamQuizDto() {
        return examQuizDto;
    }

    public void setExamQuizDto(ExamQuizDto examQuizDto) {
        this.examQuizDto = examQuizDto;
    }

    public List<SimpleQuestionDto> getSimpleQuestionDtos() {
        return simpleQuestionDtos;
    }

    public void setSimpleQuestionDtos(List<SimpleQuestionDto> simpleQuestionDtos) {
        this.simpleQuestionDtos = simpleQuestionDtos;
    }
}
