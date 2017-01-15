package com.iquest.webapp.dto;

import com.iquest.webapp.dto.frommodel.ClientDto;
import com.iquest.webapp.dto.frommodel.ExamQuizDto;
import com.iquest.webapp.dto.frommodel.GamefiedQuizDto;

import java.util.List;

public class ClientWithQuizzesDto {

    private ClientDto clientDto;
    private List<ExamQuizDto> examQuizDtos;
    private List<GamefiedQuizDto> gamefiedQuizDtos;

    public ClientDto getClientDto() {
        return clientDto;
    }

    public void setClientDto(ClientDto clientDto) {
        this.clientDto = clientDto;
    }

    public List<ExamQuizDto> getExamQuizDtos() {
        return examQuizDtos;
    }

    public void setExamQuizDtos(List<ExamQuizDto> examQuizDtos) {
        this.examQuizDtos = examQuizDtos;
    }

    public List<GamefiedQuizDto> getGamefiedQuizDtos() {
        return gamefiedQuizDtos;
    }

    public void setGamefiedQuizDtos(List<GamefiedQuizDto> gamefiedQuizDtos) {
        this.gamefiedQuizDtos = gamefiedQuizDtos;
    }
}
