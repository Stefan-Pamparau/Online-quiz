package com.iquest.webapp.dto.report;

import com.iquest.webapp.dto.frommodel.ClientDto;

import java.util.List;

public class ClientScoreReportDto {
    private ClientDto clientDto;
    private List<Integer> scoresPerMonth;

    public ClientDto getClientDto() {
        return clientDto;
    }

    public void setClientDto(ClientDto clientDto) {
        this.clientDto = clientDto;
    }

    public List<Integer> getScoresPerMonth() {
        return scoresPerMonth;
    }

    public void setScoresPerMonth(List<Integer> scoresPerMonth) {
        this.scoresPerMonth = scoresPerMonth;
    }
}
