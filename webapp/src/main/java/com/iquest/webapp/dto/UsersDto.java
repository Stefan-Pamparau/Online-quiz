package com.iquest.webapp.dto;

import com.iquest.webapp.dto.frommodel.UserDto;

import java.util.List;

public class UsersDto {

    private List<UserDto> userDtos;

    public List<UserDto> getUserDtos() {
        return userDtos;
    }

    public void setUserDtos(List<UserDto> userDtos) {
        this.userDtos = userDtos;
    }
}
