package com.iquest.webapp.dto.report;

import com.iquest.webapp.dto.frommodel.UserDto;

import java.util.List;

public class UsersScoreReportDto {

    private List<UserDto> userDtos;
    private List<Integer> scoresPerUser;

    public List<UserDto> getUserDtos() {
        return userDtos;
    }

    public void setUserDtos(List<UserDto> userDtos) {
        this.userDtos = userDtos;
    }

    public List<Integer> getScoresPerUser() {
        return scoresPerUser;
    }

    public void setScoresPerUser(List<Integer> scoresPerUser) {
        this.scoresPerUser = scoresPerUser;
    }
}
