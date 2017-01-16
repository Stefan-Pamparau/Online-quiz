package com.iquest.webapp.dto.report;

import com.iquest.model.user.Client;
import com.iquest.webapp.dto.frommodel.ClientDto;

import java.util.List;

public class ClientActivityReportDto {
    private ClientDto clientDto;
    private List<Integer> quizzesPerMonth;

    public ClientDto getClientDto() {
        return clientDto;
    }

    public void setClientDto(ClientDto clientDto) {
        this.clientDto = clientDto;
    }

    public List<Integer> getQuizzesPerMonth() {
        return quizzesPerMonth;
    }

    public void setQuizzesPerMonth(List<Integer> quizzesPerMonth) {
        this.quizzesPerMonth = quizzesPerMonth;
    }
}
